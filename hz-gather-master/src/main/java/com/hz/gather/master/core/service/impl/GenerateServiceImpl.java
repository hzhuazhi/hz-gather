package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.UUIDUtils;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.mapper.ULimitedTimeLogMapper;
import com.hz.gather.master.core.mapper.VcMemberMapper;
import com.hz.gather.master.core.model.entity.ULimitedTimeLog;
import com.hz.gather.master.core.service.GenerateService;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/3 15:03
 * @Version 1.0
 */
@Service
public class GenerateServiceImpl<T> extends BaseServiceImpl<T> implements GenerateService<T> {
    private int  ONE_YEAR =366;
    @Autowired
    private ULimitedTimeLogMapper uLimitedTimeLogMapper;

    @Override
    public BaseDao<T> getDao() {
        return uLimitedTimeLogMapper;
    }

    @Override
    public String getNonexistentInformation(int type)throws  Exception {
        String    rs ="";
        Object    reStr ="";
        do{
            if (type== Constant.TOKEN){
                rs = UUIDUtils.createUUID();
                String token1 = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, rs);
                reStr = ComponentUtil.redisService.get(token1);
            }else if(type==Constant.INVITE_CODE){
                rs  =   UUIDUtils.createInviteCode();
                String token1 = CachedKeyUtils.getCacheKey(CacheKey.INVITE_INFO, rs);
                reStr= ComponentUtil.redisService.get(token1);
            }else if(type == Constant.TRADING_ADDRESS){
                rs = UUIDUtils.createUUID();
                String token1 = CachedKeyUtils.getCacheKey(CacheKey.TRADING_ADDRESS_INFO, rs);
                reStr= ComponentUtil.redisService.get(token1);
            }else if(type == Constant.MEMBERID){
                AtomicLong atomic_did = new AtomicLong(ComponentUtil.redisIdService.getIncr(CacheKey.MEMBER_ID_INFO, ONE_YEAR));
                long did = atomic_did.get();
                reStr=did+"";
            }else if(type == Constant.PW_TOKEN){
                rs = UUIDUtils.createUUID();
                String token1 = CachedKeyUtils.getCacheKey(CacheKey.PW_TOKEN, rs);
                reStr = ComponentUtil.redisService.get(token1);
            }
        }while(reStr!=null);

        return rs;
    }


    @Override
    public String getBatchNum() {
        String    rs ="";
        Object    reStr ="";
        ULimitedTimeLog uLimitedTime=new ULimitedTimeLog();
        while(true){
            if(uLimitedTime==null){
                break;
            }
            rs = UUIDUtils.createUUID();
            ULimitedTimeLog uLimitedTimeLog=PublicMethod.queryULimitedTimeLog(rs);
            uLimitedTime= uLimitedTimeLogMapper.selectByPrimaryKey(uLimitedTimeLog);
        }
//        do{
//            rs = UUIDUtils.createUUID();
//            ULimitedTimeLog uLimitedTimeLog=PublicMethod.queryULimitedTimeLog(rs);
//            uLimitedTime= uLimitedTimeLogMapper.selectByPrimaryKey(uLimitedTimeLog);
////            reStr = ComponentUtil.redisService.get(token1);
//        }while(uLimitedTime==null);

        return rs;
    }


}
