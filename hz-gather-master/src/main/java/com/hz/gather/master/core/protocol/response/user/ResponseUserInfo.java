package com.hz.gather.master.core.protocol.response.user;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/7 20:07
 * @Version 1.0
 */
public class ResponseUserInfo {
    /**
     * 可提现金额
     */
    private String  surplus_money;
    /**
     * 已提现金额
     */
    private String  already_money;
    /**
     * 已提总收益
     */
    private String  total_money;
    /**
     * vip 等级 0、普通用户 1、限时用户 2、永久vip
     */
    private Integer vip_type;

    /**
     * 到期时间
     */
    private String expire_time;

    /****
     * 头像地址集合
     */
    private List<String> addList ;

    /****
     * 引荐金额
     */
    private String    recommend_money;
    /****
     * 裂变金额
     */
    private String    fission_money;

    public String getSurplus_money() {
        return surplus_money;
    }

    public void setSurplus_money(String surplus_money) {
        this.surplus_money = surplus_money;
    }

    public String getAlready_money() {
        return already_money;
    }

    public void setAlready_money(String already_money) {
        this.already_money = already_money;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public Integer getVip_type() {
        return vip_type;
    }

    public void setVip_type(Integer vip_type) {
        this.vip_type = vip_type;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public List<String> getAddList() {
        return addList;
    }

    public void setAddList(List<String> addList) {
        this.addList = addList;
    }

    public String getRecommend_money() {
        return recommend_money;
    }

    public void setRecommend_money(String recommend_money) {
        this.recommend_money = recommend_money;
    }

    public String getFission_money() {
        return fission_money;
    }

    public void setFission_money(String fission_money) {
        this.fission_money = fission_money;
    }
}
