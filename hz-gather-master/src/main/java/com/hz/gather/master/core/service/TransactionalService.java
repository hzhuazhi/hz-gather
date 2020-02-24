package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.model.notice.NoticeModel;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/3 9:59
 * @Version 1.0
 */
public interface TransactionalService <T> extends BaseService<T> {
    /**
     * 用户注册方法
     * @param vcMember   自己的用户信息
     * @param vcMemberResource  自己的资源信息
     * @param uqVcMemberResource   上级需要修改的
     */
    void   userRegister(VcMember vcMember, VcMemberResource vcMemberResource, VcMemberResource  uqVcMemberResource,VcMemberRewardTotal vcMemberRewardTotal,List<VcMemberResource>  list);

    void   addCashOut(UCashOutLog uCashOutLog , UCashOutProcedLog uCashOutProcedLog, VcMemberResource  uqVcMemberResource);

    void   memberPayment(VcMember vcMember , ULimitedTimeLog uLimitedTimeLog );

    /***
     * vip 的添加
     * @param vcMemberResource
     * @param uMoneyLog
     * @param uMoneyList
     */
    void   addBatchNoVIP(VcMemberResource vcMemberResource,UMoneyLog  uMoneyLog,UMoneyList uMoneyList,VcMemberRewardTotal vcMemberRewardTotal);

    /**
     * 非vip  添加
     * @param vcMemberResource
     * @param updateTimeLog
     * @param uMoneyLog
     */
    void   addBatchNoNoVIP(VcMemberResource vcMemberResource, ULimitedTimeLog updateTimeLog,UMoneyLog uMoneyLog,VcMemberRewardTotal  vcMemberRewardTotal,UMoneyList uMoneyList);

    /**
     * 添加裂变的信息
     * @param uLimitedTimeLog
     * @param uBatchLog
     */
    void  addfissionInfo(ULimitedTimeLog uLimitedTimeLog,UBatchLog uBatchLog,VcMemberRewardTotal vcMemberRewardTotal,VcMemberResource  vcMemberResource);



    void  upgradePermanentVIP(VcMemberResource vcMemberResource, ULimitedTimeLog updatelog, VcMember vcMember, UBatchLog uBatchLog, SysNoticeInfo noticeModel, List<UBatchLog> list,Integer  createMemberId);


    int   updateULimitedTimeLogIsValid(ULimitedTimeLog uqdateULimitedTimeLog,ULimitedTimeLog  insertULimitedTimeLog,VcMemberResource vcMemberResource);

    void  insertSysNoticeInfo(VcMemberRewardTotal vcMemberRewardTotal,SysNoticeInfo sysNoticeInfo);

    void  updateCaseMoneyFail(UMoneyList uMoneyList,VcMemberResource vcMemberResource);

    void  vipSurplusReward(Integer  memberId,VcMemberResource vcMemberResource,UMoneyLog  uMoneyLog,UMoneyList uMoneyList);


}
