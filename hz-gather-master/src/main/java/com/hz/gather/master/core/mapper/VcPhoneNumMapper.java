package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.model.entity.VcPhoneNum;

public interface VcPhoneNumMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(VcPhoneNum record);

    VcPhoneNum selectByPrimaryKey(VcPhoneNum record);

    int updateByPrimaryKeySelective(VcPhoneNum record);

}