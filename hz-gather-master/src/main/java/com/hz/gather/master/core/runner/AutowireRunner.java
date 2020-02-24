package com.hz.gather.master.core.runner;

import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.hz.gather.master.core.common.redis.RedisIdService;
import com.hz.gather.master.core.common.redis.RedisService;
import com.hz.gather.master.core.common.utils.constant.LoadConstant;
import com.hz.gather.master.core.service.*;
import com.hz.gather.master.core.service.impl.InitServiceImpl;
import com.hz.gather.master.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


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

    @Resource
    private LoadConstant loadConstant;
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

    @Autowired
    private TaskService taskService;
    @Autowired
    private InitService initService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UpgradeService upgradeService;
    @Autowired
    private ItemBankService itemBankService;

    @Autowired
    private ItemBankAnswerService itemBankAnswerService;

    @Autowired
    private SpreadNoticeService spreadNoticeService;

    @Autowired
    private PayCustService payCustService;

    @Autowired
    private TaskHodgepodgeService taskHodgepodgeService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private StreamConsumerService streamConsumerService;
    Thread runThread = null;

    @Autowired
    private DateService  dateService;

    @Autowired
    private LimitedTimeService  limitedTimeService;








    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("AutowireRunner ...");
        ComponentUtil.redisIdService = redisIdService;
        ComponentUtil.redisService = redisService;
        ComponentUtil.loadConstant = loadConstant;
//        ComponentUtil.virtualCoinPriceService = virtualCoinPriceService;
        ComponentUtil.loginService = loginService;
        ComponentUtil.generateService = generateService;
        ComponentUtil.transactionalService = transactionalService;
        ComponentUtil.userInfoService  =   userInfoService;
        ComponentUtil.questionMService = questionMService;
        ComponentUtil.questionDService = questionDService;
        ComponentUtil.alipayService = alipayService;
        ComponentUtil.payService = payService;
        ComponentUtil.taskService = taskService;
        ComponentUtil.initService = initService;
        ComponentUtil.upgradeService = upgradeService;
        ComponentUtil.noticeService = noticeService;
        ComponentUtil.itemBankService = itemBankService;
        ComponentUtil.itemBankAnswerService = itemBankAnswerService;
        ComponentUtil.spreadNoticeService = spreadNoticeService;
        ComponentUtil.payCustService = payCustService;
        ComponentUtil.taskHodgepodgeService = taskHodgepodgeService;
        ComponentUtil.regionService = regionService;
        ComponentUtil.streamConsumerService = streamConsumerService;
        ComponentUtil.dateService = dateService;
        ComponentUtil.limitedTimeService = limitedTimeService;
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
