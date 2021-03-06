package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.ULimitedTimeLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ULimitedTimeLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ULimitedTimeLog record);

    ULimitedTimeLog selectByPrimaryKey(ULimitedTimeLog record);

    int updateByPrimaryKeySelective(ULimitedTimeLog record);

    int updateByPushNumber(ULimitedTimeLog record);

    ULimitedTimeLog selectByMaxBatchNum(ULimitedTimeLog record);

    List<ULimitedTimeLog> selectByInvalidTime(ULimitedTimeLog record);

    ULimitedTimeLog selectByMemberId(ULimitedTimeLog record);

    int   updateByisFinish(Integer memberId);
}