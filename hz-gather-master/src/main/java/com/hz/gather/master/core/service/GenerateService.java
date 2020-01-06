package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/3 15:03
 * @Version 1.0
 */
public interface GenerateService<T> extends BaseService<T> {
    public String getNonexistentInformation(int type)throws  Exception;
}
