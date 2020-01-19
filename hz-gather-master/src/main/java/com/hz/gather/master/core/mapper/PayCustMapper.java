package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 已经支付成功的用户纪录的Dao层
 * @Author yoko
 * @Date 2020/1/19 20:41
 * @Version 1.0
 */
@Mapper
public interface PayCustMapper<T> extends BaseDao<T> {
}
