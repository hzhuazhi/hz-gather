package com.hz.gather.master.util;


import com.hz.gather.master.core.common.redis.RedisIdService;
import com.hz.gather.master.core.common.redis.RedisService;
import com.hz.gather.master.core.service.GenerateService;
import com.hz.gather.master.core.service.LoginService;
import com.hz.gather.master.core.service.TransactionalService;
import com.hz.gather.master.core.service.VirtualCoinPriceService;

/**
 * 工具类
 */
public class ComponentUtil {
    public static RedisIdService redisIdService;
    public static RedisService redisService;

    public static VirtualCoinPriceService virtualCoinPriceService;
    public static LoginService loginService;
    public static GenerateService generateService;
    public static TransactionalService transactionalService;


}
