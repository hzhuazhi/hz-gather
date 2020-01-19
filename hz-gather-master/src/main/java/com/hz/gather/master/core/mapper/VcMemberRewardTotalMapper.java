package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.model.entity.VcMemberRewardTotal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcMemberRewardTotalMapper {
    int deleteByPrimaryKey(Long id);


    int insertSelective(VcMemberRewardTotal record);

    VcMemberRewardTotal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VcMemberRewardTotal record);

    int updateByCountMoney(VcMemberRewardTotal record);

    List<VcMemberRewardTotal> selectByIsCount();

}