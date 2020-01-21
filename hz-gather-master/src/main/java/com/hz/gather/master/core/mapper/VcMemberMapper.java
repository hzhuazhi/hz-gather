package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.VcMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcMemberMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Integer memberId);

    int insertSelective(VcMember record);

    VcMember selectByPrimaryKey(VcMember record);

    int updateByPrimaryKeySelective(VcMember record);

    List<VcMember> selectByCodeOrAddress(VcMember record);
    List<VcMember> selectBySuperiorId(VcMember record);

    List<VcMember> selectByList(VcMember record);

    /**
     * @Description: 修改用户是否开启问答修改密码  0、未开启 1、开启
     * @param model - memberId、is_questions
     * @return int
     * @author yoko
     * @date 2020/1/16 14:07
     */
    public int updateMemberIsQuestions(VcMember model);

    List<VcMember> selectBySuperiorIdMember(VcMember model);

    List<VcMember> selectSuperiorIdList(VcMember model);
}