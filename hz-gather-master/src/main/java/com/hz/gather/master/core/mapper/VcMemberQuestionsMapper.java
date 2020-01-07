package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.VcMemberQuestions;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcMemberQuestionsMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Integer memberId);

    int insert(VcMemberQuestions record);

    int insertSelective(VcMemberQuestions record);

    VcMemberQuestions selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(VcMemberQuestions record);

    int updateByPrimaryKey(VcMemberQuestions record);
}