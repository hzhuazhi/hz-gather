package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.ULimitedTimeLogMapper;
import com.hz.gather.master.core.mapper.VcMemberMapper;
import com.hz.gather.master.core.model.entity.ULimitedTimeLog;
import com.hz.gather.master.core.service.LimitedTimeService;
import com.hz.gather.master.core.service.LoginService;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/21 12:28
 * @Version 1.0
 */
@Service
public class LimitedTimeServiceImpl<T> extends BaseServiceImpl<T> implements LimitedTimeService<T> {
    @Autowired
    private ULimitedTimeLogMapper uLimitedTimeLogMapper;

    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public ULimitedTimeLog getQueryList(Integer memberId) {
        ULimitedTimeLog  uLimitedTimeLog  = PublicMethod.toULimitedTimeLog(memberId);
        ULimitedTimeLog  queryULimitedTimeLog =   uLimitedTimeLogMapper.selectByMemberId(uLimitedTimeLog);
        return queryULimitedTimeLog;
    }
}
