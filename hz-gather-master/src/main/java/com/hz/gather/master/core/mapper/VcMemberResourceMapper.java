package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.dao.VcMemberResource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcMemberResourceMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(VcMemberResource record);

    VcMemberResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VcMemberResource record);

    int updateUpPeople(VcMemberResource record);

}