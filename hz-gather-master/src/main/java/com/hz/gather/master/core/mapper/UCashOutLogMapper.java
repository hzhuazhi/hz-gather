package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import com.hz.gather.master.core.model.entity.UMoneyList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UCashOutLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UCashOutLog record);

    UCashOutLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UCashOutLog record);
    /**
     * @Description: TODO
     * @return
     * @author long
     * @date 2020/1/14 17:34
     */
    public int countUCashOutLog(UCashOutLog model);
    /**
     * @Description: TODO
     * @return
     * @author long
     * @date 2020/1/14 17:30
     */
    public List<UCashOutLog> getUCashOutLog(UCashOutLog model);

    public UCashOutLog selectByUserCount(UCashOutLog model);


}