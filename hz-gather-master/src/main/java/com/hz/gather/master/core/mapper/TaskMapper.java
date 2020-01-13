package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 定时任务的Dao层
 * @Author yoko
 * @Date 2020/1/11 14:34
 * @Version 1.0
 */
@Mapper
public interface TaskMapper<T> extends BaseDao<T> {

    /**
     * @Description: 查询要转账的数据
     * @param obj
     * @return
     * @author yoko
     * @date 2020/1/11 16:28
     */
    public List<UCashOutLog> getTransferList(Object obj);

    /**
     * @Description: 更新转账成功、失败的结果
     * @param obj
     * @return
     * @author yoko
     * @date 2020/1/11 16:30
     */
    public int updateTransStatus(Object obj);

    /**
     * @Description: 添加转账数据纪录
     * @param obj
     * @return
     * @author yoko
     * @date 2020/1/11 16:32
     */
    public long addTransData(Object obj);
}
