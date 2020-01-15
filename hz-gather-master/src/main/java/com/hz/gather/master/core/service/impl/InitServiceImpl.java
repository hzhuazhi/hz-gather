package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.SysNoticeAskMapper;
import com.hz.gather.master.core.mapper.VcMemberPayMapper;
import com.hz.gather.master.core.model.entity.SysNoticeAsk;
import com.hz.gather.master.core.service.InitService;
import com.hz.gather.master.core.service.PayService;
import com.hz.gather.master.core.singleton.SysNoticeAskSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/14 14:16
 * @Version 1.0
 */
@Service
public class InitServiceImpl<T> extends BaseServiceImpl<T> implements InitService<T> {
    @Autowired
    private SysNoticeAskMapper sysNoticeAskMapper;


    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public void querySysNoticeAsk() {
        List<SysNoticeAsk> list = sysNoticeAskMapper.selectByPrimaryKey();
        SysNoticeAskSingleton.getInstance().setSysNoticeAskList(list);
    }
}
