package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.VcMemberPay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcMemberPayMapper <T> extends BaseDao<T>  {
    int deleteByPrimaryKey(Long id);

    int insertSelective(VcMemberPay record);

    List<VcMemberPay> selectByPrimaryKey(VcMemberPay record);

    int updateByPrimaryKeySelective(VcMemberPay record);

    List<VcMemberPay>  selectByMemberIdPayId(VcMemberPay record);

    VcMemberPay  selectByoldPayId(VcMemberPay record);

}