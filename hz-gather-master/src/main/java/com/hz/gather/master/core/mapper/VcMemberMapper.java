package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.VcMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcMemberMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Integer memberId);

    int insertSelective(VcMember record);

    VcMember selectByPrimaryKey(VcMember record);

    int updateByPrimaryKeySelective(VcMember record);

    List<VcMember> selectByCodeOrAddress(VcMember record);
    List<VcMember> selectBySuperiorId(VcMember record);
}