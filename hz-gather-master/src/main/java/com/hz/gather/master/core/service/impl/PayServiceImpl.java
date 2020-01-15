package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.UUIDUtils;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.common.utils.constant.PfCacheKey;
import com.hz.gather.master.core.mapper.*;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;
import com.hz.gather.master.core.service.PayService;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;


    @Autowired
    private UCashOutLogMapper uCashOutLogMapper;
    @Autowired
    private ULimitedTimeLogMapper uLimitedTimeLogMapper;
    @Autowired
    private UBatchLogMapper uBatchLogMapper;
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
        if(vcMemberResource1.getAlreadyMoney().compareTo(b1)<0){
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

    @Override
    public boolean queryPayPassword(Integer memberId, String payPassword) throws Exception {
        VcMember    vcMember=PublicMethod.toPayPassword(memberId,payPassword);
        VcMember    vcMember1 =  vcMemberMapper.selectByPrimaryKey(vcMember);
        if(vcMember1!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean paymentSuccess(Integer memberId, String outTradeNo) throws Exception {
        boolean    flag  =  false ;
        //首先check     outTradeNo   是否真实支付
        VcMember  vcMember = PublicMethod.toVcMember(memberId);
        VcMember  rsVc = vcMemberMapper.selectByPrimaryKey(vcMember);
        if(rsVc==null){ //用户信息异常
            flag = false;
        }
        //更加自己的数据数据
        flag=ComponentUtil.payService.paymentUpdateMyInfo(rsVc);
        if(!flag){
            return true;
        }
        //查看上级是否需要修改信息
        boolean  isUpdate = ComponentUtil.payService.handleSuperiorId(rsVc.getSuperiorId());

        //查询所有的上级状态信息
        List<VcMember>  list = ComponentUtil.payService.queryMemberInfo(rsVc.getBenefitMemberId());

        // 查询该用户下收益人有那些

        //添加明细表信息
        //查看所有用户的锁情况
        // 修改用户详情信息
        //关闭所有用户锁信息

        //需要给当前用户的邀请码 丢到redis 里面
        return false;
    }


    @Override
    public List<VcMember> queryMemberInfo(String benefitMemberId) throws Exception {
        List<VcMember>  list = null;
        String []  memberId  =benefitMemberId.split(",");
        VcMember  vcMember  = PublicMethod.toList(memberId);
        if(vcMember.getIdList().size()>9){
            return list;
        }
        List<VcMember>    listVcMember  =   vcMemberMapper.selectByList(vcMember);
        list = listVcMember;
        return list;
    }

    @Override
    public boolean updateMemberInfo(List<VcMember> list,Integer  superiorId,boolean superiorFlag,String outTradeNo,Integer  createMemberId) throws Exception {
        for(VcMember vcMember:list){
            Integer type=2;
            if(vcMember.getMemberId()==superiorId){
                type=1;
            }
            if(vcMember.getGradeType()==1){
                ComponentUtil.payService.updateTypeNOPermanentVIP(vcMember.getMemberId(),type,Constant.EVERY_PEOPLE_MONEY,outTradeNo,createMemberId);
                if(superiorFlag){//上级id 需要更新

                }
            }else if(vcMember.getGradeType()==2){
                updateTypePermanentVIP(vcMember.getMemberId(),type,Constant.EVERY_PEOPLE_MONEY,outTradeNo,createMemberId);
            }

        }
        return false;
    }

    @Override
    public Integer updateTypePermanentVIP(Integer memberId, Integer type,Double money,String outTradeNo,Integer createMemberId) {
        //更新会员资源表  人数和金额
        VcMemberResource  vcMemberResource = PublicMethod.toUqdateVcMemberResourceVIP(memberId,money,type);
        //直接添加数据裂变表
        UMoneyLog  uMoneyLog = PublicMethod.insertUMoneyLog(memberId,createMemberId,outTradeNo,money,type);
        //直接添加资金明细表
        UMoneyList uMoneyList = PublicMethod.insertUMoneyList(memberId,Constant.REWARD_TYPE1,Constant.SYMBO_TYPE1,money);
//        PublicMethod
        ComponentUtil.transactionalService.addBatchNoVIP(vcMemberResource,uMoneyLog,uMoneyList);
//         VcMember  vcMember  =PublicMethod.updateGradeType(memberId,1);

        //查看资源明细表是否到底了添加记录表信息
//            vcMemberResourceMapper.updateByChargeMoney(vcMemberResource);
        return 1;
    }

    @Override
    public Integer updateTypeNOPermanentVIP(Integer memberId, Integer type,Double money,String outTradeNo,Integer createMemberId) {

        if (type==1){ //直推情况
            VcMemberResource  vcMemberResource = PublicMethod.toUqdateVcMemberResourceNoVIP(memberId,type,money);
            //直接添加数据裂变表
            UMoneyLog  uMoneyLog = PublicMethod.insertUMoneyLog(memberId,createMemberId,outTradeNo,money,type);
            ULimitedTimeLog  uLimitedTimeLog=PublicMethod.toULimitedTimeLog(memberId);
            ULimitedTimeLog  uLimitedTimeLogh = uLimitedTimeLogMapper.selectByMaxBatchNum(uLimitedTimeLog);

            ULimitedTimeLog  updateTimeLog = PublicMethod.uqdateULimitedTimeLog(uLimitedTimeLogh.getBatchNum());
            UBatchLog  insertBatchLog = PublicMethod.insertUBatchLog(memberId,uLimitedTimeLogh.getBatchNum(),type,money);
            ComponentUtil.transactionalService.addBatchNoNoVIP(vcMemberResource,updateTimeLog,uMoneyLog);
        }else{ //裂变情况
            ULimitedTimeLog  uLimitedTimeLog=PublicMethod.toULimitedTimeLog(memberId);
            ULimitedTimeLog  uLimitedTimeLogh = uLimitedTimeLogMapper.selectByMaxBatchNum(uLimitedTimeLog);
            UBatchLog  insertBatchLog = PublicMethod.insertUBatchLog(memberId,uLimitedTimeLogh.getBatchNum(),type,money);
            uBatchLogMapper.insertSelective(insertBatchLog);
        }


        //uLimitedTimeLogMapper.updateByPushNumber(uLimitedTimeLog1);
        //直接添加资金明细表
//        UMoneyList uMoneyList = PublicMethod.insertUMoneyList(memberId,Constant.REWARD_TYPE1,Constant.SYMBO_TYPE1,money);
        return 1;
    }

    @Override
    public boolean paymentUpdateMyInfo(VcMember  vcMember) {
        boolean  flag = false ;
        if (vcMember.getGradeType()!=0){
            return flag;
        }
        //修改2个状态，一个是裂变人数
        VcMemberResource  vcMemberResource = PublicMethod.updateFissionPeople(vcMember.getMemberId());
        //会员表信息状态
        VcMember   updatevcMember = PublicMethod.updateGradeType(vcMember.getMemberId(),vcMember.getMemberId());
        //插入一条用户限时表信息数据
        String  batchNum = ComponentUtil.generateService.getBatchNum();
        ULimitedTimeLog  uLimitedTimeLog = PublicMethod.insertULimitedTimeLog(vcMember.getMemberId(),batchNum);
        ComponentUtil.transactionalService.memberPayment(updatevcMember,vcMemberResource,uLimitedTimeLog);
        return true;
    }


    @Override
    public boolean handleSuperiorId(Integer superiorId) throws Exception {
        boolean  flag = false;
        VcMember  vcMember = PublicMethod.toVcMember(superiorId);
        VcMember  superIdVcMember = vcMemberMapper.selectByPrimaryKey(vcMember);
        if(superIdVcMember.getGradeType()==1){
            ULimitedTimeLog  uLimitedTimeLog=PublicMethod.toULimitedTimeLog(superiorId);
            ULimitedTimeLog  rsLimitedTimeLog=uLimitedTimeLogMapper.selectByMaxBatchNum(uLimitedTimeLog);
            if(rsLimitedTimeLog.getPushNumber()==2){
                flag=true;
            }
        }
        return flag;
    }

    @Override
    public void upgradeVIPUpdateInfo(Integer memberId) {
        ULimitedTimeLog  uLimitedTimeLog=PublicMethod.toULimitedTimeLog(memberId);
        ULimitedTimeLog  uLimited=uLimitedTimeLogMapper.selectByMaxBatchNum(uLimitedTimeLog);
        if(uLimited != null){
            if(uLimited.getPushNumber()>=3){
                UBatchLog uBatchLog = PublicMethod.toUBatchLog(uLimited.getBatchNum());
                List<UBatchLog> list = uBatchLogMapper.selectByBatchNum(uBatchLog);


//                PublicMethod.toUqdateVcMemberResourceVIP();
            }
        }
    }

    @Override
    public long isOldpayId(String oldPayId,Integer memberId) {
        VcMemberPay  vcMemberPay = PublicMethod.queryVcMemberPay(oldPayId,memberId);
        VcMemberPay query= vcMemberPayMapper.selectByoldPayId(vcMemberPay);
        if(query==null){
            return 0;
        }
        return query.getId();
    }

    @Override
    public Integer updatyPayId(long id, String zfbPayId, String zfbName) {
        VcMemberPay  vcMemberPay = PublicMethod.updateVcMemberPay(id,zfbPayId,zfbName);
        return vcMemberPayMapper.updateByPrimaryKeySelective(vcMemberPay);
    }
}
