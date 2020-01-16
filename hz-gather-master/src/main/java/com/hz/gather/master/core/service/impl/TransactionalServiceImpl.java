package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.*;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/3 10:00
 * @Version 1.0
 */
@Service
@Transactional
public class TransactionalServiceImpl<T> extends BaseServiceImpl<T> implements TransactionalService<T> {
    @Autowired
    private VcMemberMapper vcMemberMapper;
    @Autowired
    private ULimitedTimeLogMapper uLimitedTimeLogMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;
    @Autowired
    private UCashOutProcedLogMapper uCashOutProcedLogMapper;

    @Autowired
    private UCashOutLogMapper uCashOutLogMapper;

    @Autowired
    private UMoneyLogMapper uMoneyLogMapper;

    @Autowired
    private UMoneyListMapper uMoneyListMapper;

    @Autowired
    private UBatchLogMapper uBatchLogMapper;



    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public void userRegister(VcMember vcMember, VcMemberResource vcMemberResource, VcMemberResource  uqVcMemberResource) {
        vcMemberMapper.insertSelective(vcMember);
        vcMemberResourceMapper.insertSelective(vcMemberResource);
        vcMemberResourceMapper.updateUpPeople(uqVcMemberResource);
    }

    @Override
    public void addCashOut(UCashOutLog uCashOutLog, UCashOutProcedLog uCashOutProcedLog, VcMemberResource uqVcMemberResource) {
        if(uCashOutProcedLog!=null){
            uCashOutProcedLogMapper.insertSelective(uCashOutProcedLog);
        }
        uCashOutLogMapper.insertSelective(uCashOutLog);
        vcMemberResourceMapper.updateByPrimaryKeySelective(uqVcMemberResource);
    }

    @Override
    public void memberPayment(VcMember vcMember, VcMemberResource vcMemberResource, ULimitedTimeLog uLimitedTimeLog) {
        vcMemberResourceMapper.updateUpPeople(vcMemberResource);
        vcMemberMapper.updateByPrimaryKeySelective(vcMember);
        uLimitedTimeLogMapper.insertSelective(uLimitedTimeLog);
    }

    @Override
    public void addBatchNoVIP(VcMemberResource vcMemberResource, UMoneyLog uMoneyLog, UMoneyList uMoneyList) {
        uMoneyListMapper.insertSelective(uMoneyList);
        uMoneyLogMapper.insertSelective(uMoneyLog);
        vcMemberResourceMapper.updateByChargeMoney(vcMemberResource);
    }

    @Override
    public void addBatchNoNoVIP(VcMemberResource vcMemberResource, ULimitedTimeLog updateTimeLog, UMoneyLog uMoneyLog) {
        vcMemberResourceMapper.updateByChargeMoney(vcMemberResource);
        uLimitedTimeLogMapper.updateByPushNumber(updateTimeLog);
        uMoneyLogMapper.insertSelective(uMoneyLog);
//        uBatchLogMapper.insertSelective(uBatchLog);
    }

    @Override
    public void addfissionInfo(ULimitedTimeLog uLimitedTimeLog, UBatchLog uBatchLog) {
        uLimitedTimeLogMapper.updateByPushNumber(uLimitedTimeLog);
        uBatchLogMapper.insertSelective(uBatchLog);
    }
}
