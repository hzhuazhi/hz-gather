package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.mapper.*;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.model.notice.NoticeModel;
import com.hz.gather.master.core.service.TransactionalService;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    @Autowired
    private NoticeMapper sysNoticeInfoMapper;

    @Autowired
    private VcMemberRewardTotalMapper vcMemberRewardTotalMapper;



    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public void userRegister(VcMember vcMember, VcMemberResource vcMemberResource, VcMemberResource  uqVcMemberResource,VcMemberRewardTotal vcMemberRewardTotal,List<VcMemberResource>  list) {
        vcMemberMapper.insertSelective(vcMember);
        vcMemberResourceMapper.insertSelective(vcMemberResource);
        vcMemberResourceMapper.updateUpPeopleAll(uqVcMemberResource);
        vcMemberRewardTotalMapper.insertSelective(vcMemberRewardTotal);
        if(list.size()!=0){
            for(VcMemberResource vcMemberResource1:list){
                vcMemberResourceMapper.updateUpPeopleAll(vcMemberResource1);
            }
        }
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
    public void memberPayment(VcMember vcMember, ULimitedTimeLog uLimitedTimeLog) {
        vcMemberMapper.updateByPrimaryKeySelective(vcMember);
        uLimitedTimeLogMapper.insertSelective(uLimitedTimeLog);
    }

    @Override
    public void addBatchNoVIP(VcMemberResource vcMemberResource, UMoneyLog uMoneyLog, UMoneyList uMoneyList,VcMemberRewardTotal vcMemberRewardTotal) {
        uMoneyListMapper.insertSelective(uMoneyList);
        uMoneyLogMapper.insertSelective(uMoneyLog);
        vcMemberResourceMapper.updateByChargeMoney(vcMemberResource);
        vcMemberRewardTotalMapper.updateByCountMoney(vcMemberRewardTotal);
    }

    @Override
    public void addBatchNoNoVIP(VcMemberResource vcMemberResource, ULimitedTimeLog updateTimeLog, UMoneyLog uMoneyLog,VcMemberRewardTotal  vcMemberRewardTotal,UMoneyList uMoneyList) {
        vcMemberResourceMapper.updateByChargeMoney(vcMemberResource);
        uLimitedTimeLogMapper.updateByPushNumber(updateTimeLog);
        uMoneyLogMapper.insertSelective(uMoneyLog);
        vcMemberRewardTotalMapper.updateByCountMoney(vcMemberRewardTotal);
        uMoneyListMapper.insertSelective(uMoneyList);
//        uBatchLogMapper.insertSelective(uBatchLog);
    }

    @Override
    public void addfissionInfo(ULimitedTimeLog uLimitedTimeLog, UBatchLog uBatchLog,VcMemberRewardTotal  vcMemberRewardTotal,VcMemberResource  vcMemberResource) {
        uLimitedTimeLogMapper.updateByPushNumber(uLimitedTimeLog);
        uBatchLogMapper.insertSelective(uBatchLog);
        vcMemberRewardTotalMapper.updateByCountMoney(vcMemberRewardTotal);
        vcMemberResourceMapper.updateByChargeMoney(vcMemberResource);
    }

    @Override
    public void upgradePermanentVIP(VcMemberResource vcMemberResource, ULimitedTimeLog updatelog, VcMember vcMember, UBatchLog uBatchLog, SysNoticeInfo noticeModel, List<UBatchLog> list,Integer  createMemberId) {
        for(UBatchLog uBatchLog1:list){
            UMoneyList  uMoneyList  = PublicMethod.insertUMoneyList(vcMemberResource.getMemberId(), Constant.REWARD_TYPE1,Constant.SYMBO_TYPE1,uBatchLog1.getReceiveMoney());
            uMoneyListMapper.insertSelective(uMoneyList);
        }
//        uBatchLogMapper.insertSelective(uBatchLog);
        uLimitedTimeLogMapper.updateByPushNumber(updatelog);
        vcMemberResourceMapper.updateByChargeMoney(vcMemberResource);
        vcMemberMapper.updateByPrimaryKeySelective(vcMember);
        sysNoticeInfoMapper.insertSelective(noticeModel);
    }

    @Override
    public int updateULimitedTimeLogIsValid(ULimitedTimeLog uqdateULimitedTimeLog, ULimitedTimeLog insertULimitedTimeLog,VcMemberResource vcMemberResource) {
        uLimitedTimeLogMapper.updateByPrimaryKeySelective(uqdateULimitedTimeLog);
        uLimitedTimeLogMapper.insertSelective(insertULimitedTimeLog);
        vcMemberResourceMapper.updateByCleanFissionPeople(vcMemberResource);
        return 1;
    }

    @Override
    public void insertSysNoticeInfo(VcMemberRewardTotal vcMemberRewardTotal, SysNoticeInfo sysNoticeInfo) {
        vcMemberRewardTotalMapper.updateByCountMoney(vcMemberRewardTotal);
        sysNoticeInfoMapper.insertSelective(sysNoticeInfo);
    }

    @Override
    public void updateCaseMoneyFail(UMoneyList uMoneyList, VcMemberResource vcMemberResource) {
        uMoneyListMapper.insertSelective(uMoneyList);
        vcMemberResourceMapper.updateCaseFail(vcMemberResource);
    }

    @Override
    public void vipSurplusReward(Integer  memberId, VcMemberResource vcMemberResource, UMoneyLog uMoneyLog,UMoneyList uMoneyList) {
        uLimitedTimeLogMapper.updateByisFinish(memberId);
        uMoneyLogMapper.insertSelective(uMoneyLog);
        uMoneyListMapper.insertSelective(uMoneyList);
        vcMemberResourceMapper.updateCaseFail(vcMemberResource);
    }
}
