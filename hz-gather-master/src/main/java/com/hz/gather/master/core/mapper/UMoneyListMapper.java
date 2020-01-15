package com.hz.gather.master.core.mapper;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.model.entity.UMoneyList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UMoneyListMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UMoneyList record);

    UMoneyList selectByPrimaryKey(UMoneyList record);

    int updateByPrimaryKeySelective(UMoneyList record);

    /**
     * @Description: TODO
     * @param null
     * @return
     * @author long
     * @date 2020/1/14 17:34
    */
    public int countUMoneyList(UMoneyList model);
    /**
     * @Description: TODO
     * @param null
     * @return
     * @author long
     * @date 2020/1/14 17:30
     */
    public List<UMoneyList> getUMoneyList(UMoneyList model);

}