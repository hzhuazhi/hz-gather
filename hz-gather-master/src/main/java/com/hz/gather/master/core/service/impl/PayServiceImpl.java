package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.VcMemberPayMapper;
import com.hz.gather.master.core.model.entity.VcMemberPay;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;
import com.hz.gather.master.core.service.PayService;
import com.hz.gather.master.core.service.UserInfoService;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Integer addPayZFB(Integer memberId, String payId) throws Exception {
        VcMemberPay vcMemberPay =  PublicMethod.toVcMemberPay(memberId,payId);
        Integer  insertCount  =vcMemberPayMapper.insertSelective(vcMemberPay);
        return insertCount;
    }

    @Override
    public List<VcMemberPay> queryPayZFBList(Integer memberId) throws Exception {
        VcMemberPay         vcMemberPay       =  PublicMethod.toVcMemberPay(memberId);
        List<VcMemberPay>   list  = vcMemberPayMapper.selectByPrimaryKey(vcMemberPay);
        return list;
    }
}
