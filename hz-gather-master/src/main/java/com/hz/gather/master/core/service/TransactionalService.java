package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberResource;

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
    void   userRegister(VcMember vcMember, VcMemberResource vcMemberResource, VcMemberResource  uqVcMemberResource);
}
