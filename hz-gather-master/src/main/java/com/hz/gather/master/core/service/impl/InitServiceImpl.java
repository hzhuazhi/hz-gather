package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.SysNoticeAskMapper;
import com.hz.gather.master.core.mapper.SysTypeDictionaryMapper;
import com.hz.gather.master.core.mapper.VcMemberPayMapper;
import com.hz.gather.master.core.model.entity.SysNoticeAsk;
import com.hz.gather.master.core.model.entity.SysTypeDictionary;
import com.hz.gather.master.core.service.InitService;
import com.hz.gather.master.core.service.PayService;
import com.hz.gather.master.core.singleton.SysNoticeAskSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/14 14:16
 * @Version 1.0
 */
@Service
public class InitServiceImpl<T> extends BaseServiceImpl<T> implements InitService<T> {
    @Autowired
    private SysNoticeAskMapper sysNoticeAskMapper;

    @Autowired
    private SysTypeDictionaryMapper sysTypeDictionaryMapper;

    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public void querySysNoticeAsk() {
        List<SysNoticeAsk> list = sysNoticeAskMapper.selectByPrimaryKey();
        SysNoticeAskSingleton.getInstance().setSysNoticeAskList(list);
    }

    @Override
    public void initBasics() {
        SysTypeDictionary sys= new SysTypeDictionary();
        List<SysTypeDictionary>    list =sysTypeDictionaryMapper.selectByPrimaryKey(sys);
        for(SysTypeDictionary sysTypeDictionary:list){
            if(sysTypeDictionary.getTypeValue().equals("1")){//注册地址
                SysNoticeAskSingleton.getInstance().setRegisterAdd(sysTypeDictionary.getValue());
            }else if(sysTypeDictionary.getTypeValue().equals("2")){//用户推广金额
                SysNoticeAskSingleton.getInstance().setPushPeopleMoney(Double.parseDouble(sysTypeDictionary.getValue()));
            }else if(sysTypeDictionary.getTypeValue().equals("3")){//用户裂变金额
                SysNoticeAskSingleton.getInstance().setEveryPeopleMoney(Double.parseDouble(sysTypeDictionary.getValue()));
            }else if(sysTypeDictionary.getTypeValue().equals("4")){//默认头像地址
                SysNoticeAskSingleton.getInstance().setMemberAdd(sysTypeDictionary.getValue());
            }else if(sysTypeDictionary.getTypeValue().equals("5")){//奖励裂变层级数
                SysNoticeAskSingleton.getInstance().setRewardFissionCount(Integer.parseInt(sysTypeDictionary.getValue()));
            }else if(sysTypeDictionary.getTypeValue().equals("7")){//用户设置最大提现次数
                SysNoticeAskSingleton.getInstance().setUserCashMaxCount(Integer.parseInt(sysTypeDictionary.getValue()));
            }
        }
    }
}
