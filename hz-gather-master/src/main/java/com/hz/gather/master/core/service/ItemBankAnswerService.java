package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.itembank.ItemBankAnswerModel;

/**
 * @Description 密保答案的Service层
 * @Author yoko
 * @Date 2020/1/15 14:44
 * @Version 1.0
 */
public interface ItemBankAnswerService<T> extends BaseService<T> {

    /**
     * @Description: 用户校验密保
     * @param model - 密保以及答案
     * @return 
     * @author yoko
     * @date 2020/1/15 17:55
    */
    public ItemBankAnswerModel checkItemBankAnswer(ItemBankAnswerModel model);
}
