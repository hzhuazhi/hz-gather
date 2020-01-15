package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UMoneyList;
import com.hz.gather.master.core.model.entity.UMoneyLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UMoneyLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UMoneyLog record);

    UMoneyLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UMoneyLog record);

    /**
     * @Description: TODO
     * @return
     * @author long
     * @date 2020/1/14 17:34
     */
    public int countUMoneyList(UMoneyLog model);
    /**
     * @Description: TODO
     * @return
     * @author long
     * @date 2020/1/14 17:30
     */
    public List<UMoneyList> getUMoneyList(UMoneyLog model);

}