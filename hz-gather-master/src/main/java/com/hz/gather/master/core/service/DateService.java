package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/18 15:56
 * @Version 1.0
 */
public interface DateService<T> extends BaseService<T> {
    int   randomNotice(Integer  count);
}
