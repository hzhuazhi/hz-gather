package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.itembank.ItemBankModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 密码的Dao层
 * @Author yoko
 * @Date 2020/1/15 11:39
 * @Version 1.0
 */
@Mapper
public interface ItemBankMapper<T> extends BaseDao<T> {

    /**
     * @Description: 根据客户ID获取客户自己定义的密保集合
     * @param model
     * @return List
     * @author yoko
     * @date 2020/1/15 15:00
     */
    public List<ItemBankModel> getItemBankByCustomerList(ItemBankModel model);
}
