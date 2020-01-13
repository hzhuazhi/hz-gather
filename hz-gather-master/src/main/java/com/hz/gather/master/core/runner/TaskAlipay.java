package com.hz.gather.master.core.runner;

import com.alibaba.fastjson.JSON;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.hz.gather.master.core.common.alipay.Alipay;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.alipay.AlipayData;
import com.hz.gather.master.core.model.alipay.AlipayTransferModel;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import com.hz.gather.master.core.model.task.base.StatusModel;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.TaskMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 阿里支付宝的task类
 * @Author yoko
 * @Date 2020/1/9 19:28
 * @Version 1.0
 */
@Component
@EnableScheduling
public class TaskAlipay {
    private final static Logger log = LoggerFactory.getLogger(TaskAlipay.class);


    @Value("${task.limit.num}")
    private int limitNum;


    /**
     * @Description: 阿里云支付：单笔转账到账户
     * @author yoko
     * @date 2019/12/27 21:30
     */
//    @Scheduled(cron = "1 * * * * ?")
//    @Scheduled(fixedDelay = 1000) // 每秒执行
    public void taskTransferAlipay() throws Exception{
        log.info("TaskAlipay.taskTransferAlipay()------------------进来了!");
        StatusModel statusQuery = TaskMethod.assembleTaskByAliapyTransferStatusQuery(limitNum);
        List<UCashOutLog> synchroList = ComponentUtil.taskService.getTransferList(statusQuery);
        for (UCashOutLog data : synchroList){
            if (data != null){
                try{
                    String lockKey = CachedKeyUtils.getPfCacheKey(CacheKey.LOCK_TRANSFER, data.getId());
                    boolean flagLock = ComponentUtil.redisIdService.lock(lockKey);
                    if (flagLock){
                        // 组装阿里支付宝转账所需的数据
                        AlipayTransferModel alipayTransferModel = TaskMethod.assembleAlipayTransfer(data);
                        String strData = JSON.toJSONString(alipayTransferModel);
                        AlipayFundTransUniTransferResponse alipayFundTransUniTransferResponse = Alipay.transferAlipay(strData);
                        if (alipayFundTransUniTransferResponse != null){
                            if (alipayFundTransUniTransferResponse.isSuccess()){
                                // 更新此次task的状态：更新成成功
                                StatusModel statusModel = TaskMethod.assembleUpdateStatusModel(data.getId(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_THREE);
                                ComponentUtil.taskService.updateTransStatus(statusModel);
                            }else{
                                // 更新此次task的状态：更新成失败
                                StatusModel statusModel = TaskMethod.assembleUpdateStatusModel(data.getId(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
                                ComponentUtil.taskService.updateTransStatus(statusModel);
                            }
                            AlipayData addAlipayData = TaskMethod.assembleAlipayData(alipayTransferModel, alipayFundTransUniTransferResponse, data);
                            ComponentUtil.taskService.addTransData(addAlipayData);
                        }else{
                            // 更新此次task的状态：更新成失败
                            StatusModel statusModel = TaskMethod.assembleUpdateStatusModel(data.getId(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
                            ComponentUtil.taskService.updateTransStatus(statusModel);
                        }
                    }
                    // 解锁
                    ComponentUtil.redisIdService.delLock(lockKey);
                }catch (Exception e){
                    // 更新此次task的状态：更新成失败
                    StatusModel statusModel = TaskMethod.assembleUpdateStatusModel(data.getId(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
                    ComponentUtil.taskService.updateTransStatus(statusModel);
                    log.error(String.format("this TaskAlipay.taskTransferAlipay() is error , the id=%s !", data.getId()));
                    e.printStackTrace();
                }
            }
        }
        log.info("TaskAlipay.taskTransferAlipay()------------------结束了!");
    }

    public static void main(String [] args) throws Exception{
        TaskAlipay t = new TaskAlipay();
        t.taskTransferAlipay();
    }
}
