package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import com.hz.gather.master.core.model.task.TaskAlipayNotifyModel;

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


    /**
     * @Description: 根据条件查询阿里订单同步数据需要跑task的数据：runStatus属于初始化值
     * @param obj - 查询条件
     * @return List
     * @author yoko
     * @date 2019/12/27 22:24
     */
    public List<TaskAlipayNotifyModel> getTaskAlipayNotify(Object obj);

    /**
     * @Description: 更新阿里订单同步数据task的状态
     * @param obj - 更新状态
     * @return int
     * @author yoko
     * @date 2019/12/27 22:26
     */
    public int updateTaskAlipayNotifyStatus(Object obj);

    /**
     * @Description: 添加已支付完成的用户纪录
     * @param obj - 用户信息
     * @return int
     * @author yoko
     * @date 2020/1/19 21:25
    */
    public int addPayCust(Object obj);

}
