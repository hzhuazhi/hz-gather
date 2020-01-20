package com.hz.gather.master.core.mapper;


import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UBatchLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UBatchLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);


    int insertSelective(UBatchLog record);

    UBatchLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UBatchLog record);

    List<UBatchLog> selectByBatchNum(UBatchLog record);

    UBatchLog selectByUserCount(UBatchLog record);
}