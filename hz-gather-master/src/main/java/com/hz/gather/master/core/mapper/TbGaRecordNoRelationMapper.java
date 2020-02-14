package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.TbGaRecordNoRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbGaRecordNoRelationMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TbGaRecordNoRelation record);

    TbGaRecordNoRelation selectByPrimaryKey(TbGaRecordNoRelation record);

    int updateByPrimaryKeySelective(TbGaRecordNoRelation record);

}