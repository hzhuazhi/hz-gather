package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.itembank.ItemBankModel;

import java.util.List;

/**
 * @Description 密保的Service层
 * @Author yoko
 * @Date 2020/1/15 11:36
 * @Version 1.0
 */
public interface ItemBankService<T> extends BaseService<T> {

    /**
     * @Description: 根据条件查询密保数据集合
     * @param model - 查询条件
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return
     * @author yoko
     * @date 2020/01/15 19:26
     */
    public List<ItemBankModel> getItemBankList(ItemBankModel model, int isCache) throws Exception;

    /**
     * @Description: 根据客户ID获取客户自己定义的密保集合
     * @param model
     * @return List
     * @author yoko
     * @date 2020/1/15 15:00
    */
    public List<ItemBankModel> getItemBankByCustomerList(ItemBankModel model);
}
