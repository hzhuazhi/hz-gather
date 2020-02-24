package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.ULimitedTimeLog;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/21 12:25
 * @Version 1.0
 */
public interface LimitedTimeService<T> extends BaseService<T> {
    ULimitedTimeLog getQueryList(Integer memberId);
}
