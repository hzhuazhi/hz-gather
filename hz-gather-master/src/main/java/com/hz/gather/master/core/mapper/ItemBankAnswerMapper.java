package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.itembank.ItemBankAnswerModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 密保答案的Dao层
 * @Author yoko
 * @Date 2020/1/15 14:44
 * @Version 1.0
 */
@Mapper
public interface ItemBankAnswerMapper<T> extends BaseDao<T> {

    /**
     * @Description: 用户校验密保
     * @param model - 密保以及答案
     * @return
     * @author yoko
     * @date 2020/1/15 17:55
     */
    public ItemBankAnswerModel checkItemBankAnswer(ItemBankAnswerModel model);
}
