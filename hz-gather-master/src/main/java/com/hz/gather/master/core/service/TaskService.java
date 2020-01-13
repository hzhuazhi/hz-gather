package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.UCashOutLog;

import java.util.List;

/**
 * @Description 定时任务的Service层
 * @Author yoko
 * @Date 2020/1/11 14:33
 * @Version 1.0
 */
public interface TaskService<T> extends BaseService<T> {

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
