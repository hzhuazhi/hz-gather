package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.SysTypeDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysTypeDictionaryMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SysTypeDictionary record);

    List<SysTypeDictionary> selectByPrimaryKey(SysTypeDictionary record);

    int updateByPrimaryKeySelective(SysTypeDictionary record);

}