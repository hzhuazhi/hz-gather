package com.hz.gather.master.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.mapper.UMoneyListMapper;
import com.hz.gather.master.core.mapper.VcMemberMapper;
import com.hz.gather.master.core.mapper.VirtualCoinPriceMapper;
import com.hz.gather.master.core.model.dao.VcMember;
import com.hz.gather.master.core.model.price.VirtualCoinPriceModel;
import com.hz.gather.master.core.service.VirtualCoinPriceService;
import com.hz.gather.master.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 虚拟币每天兑换的价格的Service层的实现层
 * @Author yoko
 * @Date 2019/11/21 22:16
 * @Version 1.0
 */
@Service
public class VirtualCoinPriceServiceImpl<T> extends BaseServiceImpl<T> implements VirtualCoinPriceService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private VirtualCoinPriceMapper virtualCoinPriceMapper;


    @Autowired
    private VcMemberMapper vcMemberMapper;
    @Autowired
    private UMoneyListMapper uMoneyListMapper;

    public BaseDao<T> getDao() {
        return virtualCoinPriceMapper;
    }

    @Override
    public List<VirtualCoinPriceModel> getVirtualCoinPriceList(VirtualCoinPriceModel model, int isCache) throws Exception {
        List<VirtualCoinPriceModel> dataList = null;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.PHONE_INFO, model.getCurdayStart(), model.getCurdayEnd());
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                dataList = JSON.parseArray(strCache, VirtualCoinPriceModel.class);
            } else {
                //查询数据库
                dataList = virtualCoinPriceMapper.findByCondition(model);
                if (dataList != null && dataList.size() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO) {
                    // 把数据存入缓存 - 这里缓存的时间是距离凌晨0点相差的时间
                    ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataList, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN, TimeUnit.MINUTES);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            dataList = virtualCoinPriceMapper.findByCondition(model);
        }
        return dataList;
    }

    @Override
    public VirtualCoinPriceModel getVirtualCoinPrice(VirtualCoinPriceModel model, int isCache) throws Exception {
        VirtualCoinPriceModel dataModel = null;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.DEVICE_INFO, model.getCurday());
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                dataModel = JSON.parseObject(strCache, VirtualCoinPriceModel.class);
            } else {
                //查询数据库
                dataModel = (VirtualCoinPriceModel) virtualCoinPriceMapper.findByObject(model);
                if (dataModel != null && dataModel.getId() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO) {
                    // 把数据存入缓存
                    ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN, TimeUnit.MINUTES);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            dataModel = (VirtualCoinPriceModel) virtualCoinPriceMapper.findByObject(model);
        }
        return dataModel;
    }


    @Override
    public void test() {

//        uMoneyListMapper.selectByPrimaryKey(1L);
        VcMember  vcMember = new VcMember();
        vcMemberMapper.selectByPrimaryKey(vcMember);
    }
}
