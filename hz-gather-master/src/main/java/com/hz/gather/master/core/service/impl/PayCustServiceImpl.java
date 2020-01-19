package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.PayCustMapper;
import com.hz.gather.master.core.service.PayCustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 已经支付成功的用户纪录的实现层
 * @Author yoko
 * @Date 2020/1/19 20:41
 * @Version 1.0
 */
@Service
public class PayCustServiceImpl<T> extends BaseServiceImpl<T> implements PayCustService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private PayCustMapper payCustMapper;


    public BaseDao<T> getDao() {
        return payCustMapper;
    }
}
