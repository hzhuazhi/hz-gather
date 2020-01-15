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

    public void setSysNoticeAskList(List<SysNoticeAsk> sysNoticeAskList) {
        this.sysNoticeAskList = sysNoticeAskList;
    }
}
