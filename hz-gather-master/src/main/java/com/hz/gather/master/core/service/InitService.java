package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.SysNoticeAsk;

import java.util.List;

/**
 * @Description 初始化加载
 * @Author long
 * @Date 2020/1/14 14:14
 * @Version 1.0
 */
public interface InitService <T> extends BaseService<T> {
    void querySysNoticeAsk();
}
