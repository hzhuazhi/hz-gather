package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UCashOutProcedLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UCashOutProcedLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UCashOutProcedLog record);

    UCashOutProcedLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UCashOutProcedLog record);

}