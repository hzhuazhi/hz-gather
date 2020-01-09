package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UMoneyLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UMoneyLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UMoneyLog record);

    UMoneyLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UMoneyLog record);

}