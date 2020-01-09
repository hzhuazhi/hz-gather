package com.hz.gather.master.core.runner;

import com.hz.gather.master.core.common.redis.RedisIdService;
import com.hz.gather.master.core.common.redis.RedisService;
import com.hz.gather.master.core.service.*;
import com.hz.gather.master.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(0)
public class AutowireRunner implements ApplicationRunner {
    private final static Logger log = LoggerFactory.getLogger(AutowireRunner.class);

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    @Value("${sp.send.url}")
    private String spSendUrl;

    @Autowired
    private RedisIdService redisIdService;
    @Autowired
    private RedisService redisService;
//    @Autowired
//    private VirtualCoinPriceService virtualCoinPriceService;

    @Autowired
    private GenerateService generateService;


    @Autowired
    private LoginService loginService;
    @Autowired
    private TransactionalService transactionalService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private QuestionMService questionMService;

    @Autowired
    private QuestionDService questionDService;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private PayService payService;

    Thread runThread = null;









    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("AutowireRunner ...");
        ComponentUtil.redisIdService = redisIdService;
        ComponentUtil.redisService = redisService;
//        ComponentUtil.virtualCoinPriceService = virtualCoinPriceService;
        ComponentUtil.loginService = loginService;
        ComponentUtil.generateService = generateService;
        ComponentUtil.transactionalService = transactionalService;
        ComponentUtil.userInfoService  =   userInfoService;
        ComponentUtil.questionMService = questionMService;
        ComponentUtil.questionDService = questionDService;
        ComponentUtil.alipayService = alipayService;
        ComponentUtil.payService = payService;
        runThread = new RunThread();
        runThread.start();






    }

    /**
     * @author df
     * @Description: TODO(模拟请求)
     * <p>1.随机获取当日金额的任务</p>
     * <p>2.获取代码信息</p>
     * @create 20:21 2019/1/29
     **/
    class RunThread extends Thread{
        @Override
        public void run() {
            log.info("启动啦............");
        }


    }






}
