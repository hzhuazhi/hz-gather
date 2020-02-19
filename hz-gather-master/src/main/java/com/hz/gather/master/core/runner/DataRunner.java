package com.hz.gather.master.core.runner;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.hz.gather.master.core.common.alipay.Alipay;
import com.hz.gather.master.core.common.utils.DateUtil;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/19 16:12
 * @Version 1.0
 */
@Component
@EnableScheduling
public class DataRunner {
    private final static Logger log = LoggerFactory.getLogger(DataRunner.class);

    @Scheduled(fixedDelay = 1000*60*60) // 一个小时
//    @Scheduled(fixedDelay = 5000) // 每秒执行
    public void addNoticeInfo() throws Exception{
        log.info("DataRunner.addNoticeInfo()------------------进来了!");
        Date  date=new Date();
        String  begenDate=DateUtil.getDateLong(new Date());
        String  endDate = DateUtil.sdfLongTimePlus.format(date);
        Boolean  flag =DateUtil.checkBetweenTime(begenDate+" 07:00:00",begenDate+" 23:59:59");
        if(flag){
            ComponentUtil.dateService.randomNotice(1);
        }
        log.info("DataRunner.addNoticeInfo()------------------结束了!");
    }
}
