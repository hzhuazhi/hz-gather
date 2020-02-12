package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.StreamConsumerMapper;
import com.hz.gather.master.core.model.stream.StreamConsumerModel;
import com.hz.gather.master.core.service.StreamConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 用户访问、异常的流水的Service层的实现层
 * @Author yoko
 * @Date 2019/12/18 14:20
 * @Version 1.0
 */
@Service
public class StreamConsumerServiceImpl<T> extends BaseServiceImpl<T> implements StreamConsumerService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private StreamConsumerMapper streamConsumerMapper;

    public BaseDao<T> getDao() {
        return streamConsumerMapper;
    }

    @Override
    public int addVisit(StreamConsumerModel model) {
        return streamConsumerMapper.addVisit(model);
    }

    @Override
    public int addError(StreamConsumerModel model) {
        return streamConsumerMapper.addError(model);
    }
}
