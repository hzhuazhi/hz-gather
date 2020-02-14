package com.hz.gather.master.core.runner;

import com.hz.gather.master.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/12 11:46
 * @Version 1.0
 */
@Component
@Order(100)
public class InitRunner implements ApplicationRunner {
    private final static Logger log = LoggerFactory.getLogger(InitRunner.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /****************  初始化信息 ************/
        log.debug("初始化信息！");
        ComponentUtil.initService.querySysNoticeAsk();
        log.debug("初始化信息！");
        ComponentUtil.initService.initBasics();

//        new Thread() {
//            public void run() {
//                log.debug("修改结果状态！");
//                ComponentUtil.payService.executeInvalidTimeInfo();
//            }
//        }.start();
//
//
//        new Thread() {
//            public void run() {
//                log.debug("执行公告记录！");
//                ComponentUtil.userInfoService.executeInsertNoticeInfo();
//            }
//        }.start();

    }

}
