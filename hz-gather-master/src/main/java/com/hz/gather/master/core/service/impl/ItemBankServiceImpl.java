package com.hz.gather.master.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.DateUtil;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.mapper.ItemBankMapper;
import com.hz.gather.master.core.model.itembank.ItemBankModel;
import com.hz.gather.master.core.service.ItemBankService;
import com.hz.gather.master.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 密保的Service的实现层
 * @Author yoko
 * @Date 2020/1/15 11:38
 * @Version 1.0
 */
@Service
public class ItemBankServiceImpl <T> extends BaseServiceImpl<T> implements ItemBankService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private ItemBankMapper itemBankMapper;


    public BaseDao<T> getDao() {
        return itemBankMapper;
    }

    @Override
    public List<ItemBankModel> getItemBankList(ItemBankModel model, int isCache) throws Exception {
        List<ItemBankModel> dataList = null;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.ITEM_BANK);
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                dataList = JSON.parseArray(strCache, ItemBankModel.class);
            } else {
                //查询数据库
                dataList = itemBankMapper.findByCondition(model);
                if (dataList != null && dataList.size() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO) {
                    // 把数据存入缓存 - 这里缓存的时间是距离凌晨0点相差的时间
                    ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataList, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), DateUtil.getTomorrowMinute(), TimeUnit.MINUTES);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            dataList = itemBankMapper.findByCondition(model);
        }
        return dataList;
    }

    @Override
    public List<ItemBankModel> getItemBankByCustomerList(ItemBankModel model) {
        return itemBankMapper.getItemBankByCustomerList(model);
    }
}
