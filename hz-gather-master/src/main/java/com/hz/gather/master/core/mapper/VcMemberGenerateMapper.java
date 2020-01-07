package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.VcMemberGenerateModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 19:20
 * @Version 1.0
 */
@Mapper
public interface VcMemberGenerateMapper<T> extends BaseDao<T> {
    void insertSelective(VcMemberGenerateModel vcMemberGenerateModel);
}
