package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 系统通知，系统公告，传播、扩散的通知的Dao层
 * @Author yoko
 * @Date 2020/1/16 20:41
 * @Version 1.0
 */
@Mapper
public interface SpreadNoticeMapper<T> extends BaseDao<T> {
}
