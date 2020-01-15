package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.SysNoticeAsk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysNoticeAskMapper <T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SysNoticeAsk record);

    List<SysNoticeAsk> selectByPrimaryKey();

    int updateByPrimaryKeySelective(SysNoticeAsk record);

}