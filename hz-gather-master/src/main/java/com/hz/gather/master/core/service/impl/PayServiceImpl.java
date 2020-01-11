package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.common.utils.constant.PfCacheKey;
import com.hz.gather.master.core.mapper.UCashOutLogMapper;
import com.hz.gather.master.core.mapper.VcMemberPayMapper;
import com.hz.gather.master.core.mapper.VcMemberResourceMapper;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import com.hz.gather.master.core.model.entity.UCashOutProcedLog;
import com.hz.gather.master.core.model.entity.VcMemberPay;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;
import com.hz.gather.master.core.service.PayService;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 21:50
 * @Version 1.0
 */
@Service
public class PayServiceImpl<T> extends BaseServiceImpl<T> implements PayService<T> {
    @Autowired
    private VcMemberPayMapper vcMemberPayMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;


    @Autowired
    private UCashOutLogMapper uCashOutLogMapper;
    @Override
    public BaseDao<T> getDao() {
        return null;
    }


    @Override
    public ResponeseHavaPay getHavaPay(Integer memberId) throws Exception {
        ResponeseHavaPay    responeseHavaPay  =  new  ResponeseHavaPay();
        VcMemberPay vcMemberPay       =  PublicMethod.toVcMemberPay(memberId);
        List<VcMemberPay> list  = vcMemberPayMapper.selectByPrimaryKey(vcMemberPay);
        if(list.size()>0){
            responeseHavaPay.setFlag(true);
        }else{
            responeseHavaPay.setFlag(false);
        }
        return responeseHavaPay;
    }

    @Override
    public boolean isAddPayZFB(Integer memberId) throws Exception {
        boolean  flag  = true ;
        VcMemberPay         vcMemberPay       =  PublicMethod.toVcMemberPay(memberId);
        List<VcMemberPay>   list  = vcMemberPayMapper.selectByPrimaryKey(vcMemberPay);
        if(list.size()>=2){
            flag = false;
        }
        return flag;
    }


    @Override
    public Integer addPayZFB(Integer memberId, String payId,String  payName) throws Exception {
        VcMemberPay vcMemberPay =  PublicMethod.toVcMemberPay(memberId,payId,payName);
        Integer  insertCount  =vcMemberPayMapper.insertSelective(vcMemberPay);
        return insertCount;
    }

    @Override
    public List<VcMemberPay> queryPayZFBList(Integer memberId) throws Exception {
        VcMemberPay         vcMemberPay       =  PublicMethod.toVcMemberPay(memberId);
        List<VcMemberPay>   list  = vcMemberPayMapper.selectByPrimaryKey(vcMemberPay);
        return list;
    }

    @Override
    public List<VcMemberPay>   checkMemberIdToAliPayId(Integer memberId, String aliPayId) throws Exception {
        VcMemberPay vcMemberPay = PublicMethod.queryVcMemberPay(memberId,aliPayId);
        List<VcMemberPay>  list = vcMemberPayMapper.selectByMemberIdPayId(vcMemberPay);
        return list;
    }

    @Override
    public boolean checkMemberIdMoney(Integer memberId, Double money) throws Exception {
        VcMemberResource vcMemberResource =PublicMethod.toVcMemberResource(memberId);
        VcMemberResource  vcMemberResource1=vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        BigDecimal b1 = new BigDecimal(Double.toString(money));
        if(vcMemberResource1.getAlreadyMoney().compareTo(b1)>=0){
            return  true;
        }
        return false;
    }

    @Override
    public void addUCashOutLog(Integer memberId, String aliPayNo,String alname,String outTradeNo, Double money) throws Exception {
        VcMemberResource vcMemberResource =PublicMethod.toVcMemberResource(memberId);
        VcMemberResource  vcMemberResource1=vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        BigDecimal b1 = new BigDecimal(Double.toString(money));
        if(vcMemberResource1.getAlreadyMoney().compareTo(b1)>=0){
            throw  new ServiceException(ENUM_ERROR.P00003.geteCode(),ENUM_ERROR.P00003.geteDesc());
        }

        UCashOutLog        uCashOutLog       =  null;
        UCashOutProcedLog uCashOutProcedLog  =  null;
        Double   moneyCashOut = 0D;
        Double   moneyProced  = 0D;
        if(money == Constant.PAY_MAX_MOMNEY){
            moneyCashOut = Constant.PAY_MAX_MOMNEY;
        }else{
            moneyCashOut = (1-Constant.SERVICE_MOMNEY)*money;
            moneyProced  = Constant.SERVICE_MOMNEY * money;
        }

        uCashOutLog  =  PublicMethod.toUCashOutLog(memberId,aliPayNo,alname,outTradeNo,moneyCashOut);
        if(moneyProced==0D){
            uCashOutProcedLog = PublicMethod.toUCashOutProcedLog(memberId,aliPayNo,outTradeNo,moneyCashOut);
        }
        VcMemberResource   vcMemberResourceFlag   = PublicMethod.updateVcMemberResource(money,vcMemberResource1);

        String      lockKey   = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, outTradeNo);
        boolean     flagLock  = ComponentUtil.redisIdService.lock(lockKey);

        String lockKey_consumer = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, memberId);
        if (flagLock){
            ComponentUtil.transactionalService.addCashOut(uCashOutLog,uCashOutProcedLog,vcMemberResourceFlag);
        }else {
            throw  new ServiceException(ENUM_ERROR.P00006.geteCode(),ENUM_ERROR.P00006.geteDesc());
        }
        ComponentUtil.redisIdService.delLock(lockKey);
        ComponentUtil.redisIdService.delLock(lockKey_consumer);
    }
}
