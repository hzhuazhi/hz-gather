package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.spread.SpreadNoticeModel;

import java.util.List;

/**
 * @Description 系统通知，系统公告，传播、扩散的通知的Service层
 * @Author yoko
 * @Date 2020/1/16 20:42
 * @Version 1.0
 */
public interface SpreadNoticeService<T> extends BaseService<T> {

    /**
     * @Description: 根据条件查询系统公告数据集合
     * @param model - 查询条件
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return
     * @author yoko
     * @date 2020/01/15 19:26
     */
    public List<SpreadNoticeModel> getSpreadNoticeList(SpreadNoticeModel model, int isCache) throws Exception;

    /**
     * @Description: 根据条件查询系统公告数据-详情
     * @param model - 查询条件
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return
     * @author yoko
     * @date 2020/01/15 19:26
     */
    public SpreadNoticeModel getSpreadNotice(SpreadNoticeModel model, int isCache) throws Exception;
}
