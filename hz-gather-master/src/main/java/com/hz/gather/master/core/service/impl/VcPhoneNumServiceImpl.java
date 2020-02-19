package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.service.DateService;
import com.hz.gather.master.core.service.VcPhoneNumService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/19 15:45
 * @Version 1.0
 */
@Service
public class VcPhoneNumServiceImpl<T> extends BaseServiceImpl<T> implements VcPhoneNumService<T> {
    @Override
    public BaseDao<T> getDao() {
        return null;
    }
}
