package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberResource;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/7 17:29
 * @Version 1.0
 */
public interface UserInfoService<T> extends BaseService<T> {
    /***
     * 根据memberId 查询  memberInfo
     * @param memberId
     * @return
     */
    public VcMember queryMemberInfo(Integer  memberId);

    /***
     * 根据memberId 查询  ResourceInfo
     * @param memberId
     * @return
     */
    public VcMemberResource queryMemberResourceInfo(Integer  memberId);

}
