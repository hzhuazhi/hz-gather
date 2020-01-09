package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UCashOutLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UCashOutLog record);

    UCashOutLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UCashOutLog record);

}