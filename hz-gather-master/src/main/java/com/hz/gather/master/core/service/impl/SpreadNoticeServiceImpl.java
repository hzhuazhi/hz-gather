package com.hz.gather.master.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.mapper.SpreadNoticeMapper;
import com.hz.gather.master.core.model.spread.SpreadNoticeModel;
import com.hz.gather.master.core.service.SpreadNoticeService;
import com.hz.gather.master.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 系统通知，系统公告，传播、扩散的通知的Service层的实现层
 * @Author yoko
 * @Date 2020/1/16 20:44
 * @Version 1.0
 */
@Service
public class SpreadNoticeServiceImpl <T> extends BaseServiceImpl<T> implements SpreadNoticeService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private SpreadNoticeMapper spreadNoticeMapper;


    public BaseDao<T> getDao() {
        return spreadNoticeMapper;
    }

    @Override
    public List<SpreadNoticeModel> getSpreadNoticeList(SpreadNoticeModel model, int isCache) throws Exception {
        List<SpreadNoticeModel> dataList = null;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.SPREAD_NOTICE);
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                dataList = JSON.parseArray(strCache, SpreadNoticeModel.class);
            } else {
                //查询数据库
                dataList = spreadNoticeMapper.findByCondition(model);
                if (dataList != null && dataList.size() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO) {
                    // 把数据存入缓存 - 这里缓存的时间是距离凌晨0点相差的时间
                    ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataList, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            dataList = spreadNoticeMapper.findByCondition(model);
        }
        return dataList;
    }

    @Override
    public SpreadNoticeModel getSpreadNotice(SpreadNoticeModel model, int isCache) throws Exception {
        SpreadNoticeModel dataModel = null;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.SPREAD_NOTICE_INFO, model.getId());
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                dataModel = JSON.parseObject(strCache, SpreadNoticeModel.class);
            } else {
                //查询数据库
                dataModel = (SpreadNoticeModel) spreadNoticeMapper.findByObject(model);
                if (dataModel != null && dataModel.getId() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO) {
                    // 把数据存入缓存
                    ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            dataModel = (SpreadNoticeModel) spreadNoticeMapper.findByObject(model);
        }
        return dataModel;
    }
}
