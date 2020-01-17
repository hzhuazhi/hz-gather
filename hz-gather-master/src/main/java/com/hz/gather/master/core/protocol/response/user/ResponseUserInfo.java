package com.hz.gather.master.core.protocol.response.user;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/7 20:07
 * @Version 1.0
 */
public class ResponseUserInfo extends ResponseUser{

    /**
     * 手机号
     */
    private String  phone;
    /**
     * 可提现金额
     */
    private String  surplus_money;
    /**
     * 领取现金额
     */
    private String  already_money;
    /**
     * 已提总收益
     */
    private String  total_money;

    /**
     * 已提总收益
     */
    private String  cash_money;
    /**
     * vip 等级 0、普通用户 1、限时用户 2、永久vip
     */
    private Integer vip_type;

    /**
     * 到期时间
     */
    private Long expire_time;

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
    /****
     * 推荐人数
     */
    private String    push_count;

    /****
     * 裂变要求数量数量
     */
    private String    require_fission_count;

    /****
     * 裂变实际数量
     */
    private String    reality_push_count;
    /**
     * 是否设置password  0、未设置 1、设置了
     */
    private Integer    isPw;
    /**
     * 是否设置支付password 0、未设置 1、设置了
     */
    private Integer    isPayPw;
    /**
     * 是否设置密保  0、未设置 1、设置了
     */
    private Integer    isprotection;
    /**
     * 邀请码
     */
    private String     inviteCode;

    /**
     * 二维码信息
     */
    private String     rq_code;

    public String getRq_code() {
        return rq_code;
    }

    public void setRq_code(String rq_code) {
        this.rq_code = rq_code;
    }

    public String getCash_money() {
        return cash_money;
    }

    public void setCash_money(String cash_money) {
        this.cash_money = cash_money;
    }

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

    public Long getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Long expire_time) {
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

    public String getPush_count() {
        return push_count;
    }

    public void setPush_count(String push_count) {
        this.push_count = push_count;
    }

    public String getRequire_fission_count() {
        return require_fission_count;
    }

    public void setRequire_fission_count(String require_fission_count) {
        this.require_fission_count = require_fission_count;
    }

    public String getReality_push_count() {
        return reality_push_count;
    }

    public void setReality_push_count(String reality_push_count) {
        this.reality_push_count = reality_push_count;
    }

    public Integer getIsPw() {
        return isPw;
    }

    public void setIsPw(Integer isPw) {
        this.isPw = isPw;
    }

    public Integer getIsPayPw() {
        return isPayPw;
    }

    public void setIsPayPw(Integer isPayPw) {
        this.isPayPw = isPayPw;
    }

    public Integer getIsprotection() {
        return isprotection;
    }

    public void setIsprotection(Integer isprotection) {
        this.isprotection = isprotection;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
