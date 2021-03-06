package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcMemberResourceMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(VcMemberResource record);

    VcMemberResource selectByPrimaryKey(VcMemberResource record);

    int updateByPrimaryKeySelective(VcMemberResource record);

    int updateUpPeople(VcMemberResource record);

    int updateByChargeMoney(VcMemberResource record);

    int updateByFissionPeople(VcMemberResource record);

    int updateByCleanFissionPeople(VcMemberResource record);

    int updateUpPeopleAll(VcMemberResource record);

    VcMemberResource selectEightFissionPeople(VcMemberResource record);

    int updateCaseFail(VcMemberResource record);


}