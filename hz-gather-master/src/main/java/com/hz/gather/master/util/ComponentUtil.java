package com.hz.gather.master.util;


import com.hz.gather.master.core.common.redis.RedisIdService;
import com.hz.gather.master.core.common.redis.RedisService;
import com.hz.gather.master.core.common.utils.constant.LoadConstant;
import com.hz.gather.master.core.service.*;
import com.hz.gather.master.core.service.impl.InitServiceImpl;

/**
 * 工具类
 */
public class ComponentUtil {
    public static RedisIdService redisIdService;
    public static RedisService redisService;
    public static LoadConstant loadConstant;
//    public static VirtualCoinPriceService virtualCoinPriceService;
    public static LoginService loginService;
    public static GenerateService generateService;
    public static TransactionalService transactionalService;
    public static UserInfoService userInfoService;
    public static QuestionMService questionMService;
    public static QuestionDService questionDService;
    public static AlipayService alipayService;
    public static PayService payService;
    public static TaskService taskService;
    public static InitService initService;
    public static UpgradeService upgradeService;
    public static NoticeService noticeService;
    public static ItemBankService itemBankService;
    public static ItemBankAnswerService itemBankAnswerService;
    public static SpreadNoticeService spreadNoticeService;
    public static InitServiceImpl initServiceImpl;
    public static PayCustService payCustService;
    public static TaskHodgepodgeService taskHodgepodgeService;


}
