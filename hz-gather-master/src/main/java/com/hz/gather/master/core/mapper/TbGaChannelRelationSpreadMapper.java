package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.TbGaChannelRelationSpread;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TbGaChannelRelationSpreadMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TbGaChannelRelationSpread record);

    List<TbGaChannelRelationSpread> selectByPrimaryKey(TbGaChannelRelationSpread record);

    int updateByPrimaryKeySelective(TbGaChannelRelationSpread record);

}