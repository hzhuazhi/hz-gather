package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.VcMemberGenerateMapper;
import com.hz.gather.master.core.mapper.VcMemberMapper;
import com.hz.gather.master.core.mapper.VcMemberResourceMapper;
import com.hz.gather.master.core.mapper.VirtualCoinPriceMapper;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import com.hz.gather.master.core.service.LoginService;
import com.hz.gather.master.core.service.UserInfoService;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/7 17:29
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl<T> extends BaseServiceImpl<T> implements UserInfoService<T> {

    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;


    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public VcMember queryMemberInfo(Integer memberId) {
        VcMember  vcMember =PublicMethod.toVcMember(memberId);
        VcMember  rsVcMember=vcMemberMapper.selectByPrimaryKey(vcMember);
        return rsVcMember;
    }

    @Override
    public VcMemberResource queryMemberResourceInfo(Integer memberId) {
        VcMemberResource vcMemberResource   =  PublicMethod.toVcMemberResource(memberId);
        VcMemberResource  vcMemberResource1  =  vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        return vcMemberResource1;
    }
}
