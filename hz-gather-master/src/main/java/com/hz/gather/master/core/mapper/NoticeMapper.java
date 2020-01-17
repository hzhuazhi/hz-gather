package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.SysNoticeInfo;
import com.hz.gather.master.core.model.notice.NoticeModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 公告的Dao层
 * @Author yoko
 * @Date 2020/1/14 20:12
 * @Version 1.0
 */
@Mapper
public interface NoticeMapper<T> extends BaseDao<T>{
    int  insertSelective(SysNoticeInfo noticeModel);
}
