package com.hz.gather.master.core.singleton;

import com.hz.gather.master.core.model.entity.SysNoticeAsk;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/14 14:22
 * @Version 1.0
 */
public class SysNoticeAskSingleton {

    private static SysNoticeAskSingleton sysNoticeAskSingleton;
    private static ReentrantLock lock = new ReentrantLock();


    private SysNoticeAskSingleton() {

    }

    public static SysNoticeAskSingleton getInstance() {
        if (sysNoticeAskSingleton == null) {
            lock.lock();
            if (sysNoticeAskSingleton == null) {
                sysNoticeAskSingleton = new SysNoticeAskSingleton();
            }
            lock.unlock();
        }
        return sysNoticeAskSingleton;
    }

    public List<SysNoticeAsk>   sysNoticeAskList;

    public List<SysNoticeAsk> getSysNoticeAskList() {
        return sysNoticeAskList;
    }
    public  String   registerAdd ;
    public  String   memberAdd;
    public  Double   everyPeopleMoney;
    public  Double   pushPeopleMoney;
    public  Integer  rewardFissionCount;


    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }

    public Double getEveryPeopleMoney() {
        return everyPeopleMoney;
    }

    public void setEveryPeopleMoney(Double everyPeopleMoney) {
        this.everyPeopleMoney = everyPeopleMoney;
    }

    public Double getPushPeopleMoney() {
        return pushPeopleMoney;
    }

    public void setPushPeopleMoney(Double pushPeopleMoney) {
        this.pushPeopleMoney = pushPeopleMoney;
    }

    public Integer getRewardFissionCount() {
        return rewardFissionCount;
    }

    public void setRewardFissionCount(Integer rewardFissionCount) {
        this.rewardFissionCount = rewardFissionCount;
    }

    public void setSysNoticeAskList(List<SysNoticeAsk> sysNoticeAskList) {
        this.sysNoticeAskList = sysNoticeAskList;
    }

    public String getRegisterAdd() {
        return registerAdd;
    }

    public void setRegisterAdd(String registerAdd) {
        this.registerAdd = registerAdd;
    }
}
