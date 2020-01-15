package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.ItemBankAnswerMapper;
import com.hz.gather.master.core.service.ItemBankAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 密保答案的Service层的实现层
 * @Author yoko
 * @Date 2020/1/15 14:45
 * @Version 1.0
 */
@Service
public class ItemBankAnswerServiceImpl<T> extends BaseServiceImpl<T> implements ItemBankAnswerService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private ItemBankAnswerMapper itemBankAnswerMapper;

    public BaseDao<T> getDao() {
        return itemBankAnswerMapper;
    }
}
