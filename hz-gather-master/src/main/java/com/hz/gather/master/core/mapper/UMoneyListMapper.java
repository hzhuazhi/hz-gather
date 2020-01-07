package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UMoneyList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UMoneyListMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UMoneyList record);

    UMoneyList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UMoneyList record);

}