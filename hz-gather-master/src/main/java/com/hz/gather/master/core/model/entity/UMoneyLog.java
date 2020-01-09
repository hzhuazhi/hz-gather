package com.hz.gather.master.core.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UMoneyLog {
    /**
     * 自增长
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 支付订单号
     *
     * @mbggenerated
     */
    private String outTradeNo;

    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 创建收益的会员ID
     *
     * @mbggenerated
     */
    private Integer createMemberId;

    /**
     * 领取金额
     *
     * @mbggenerated
     */
    private BigDecimal receiveMoney;

    /**
     * 类型:  1、直推  2、裂变
     *
     * @mbggenerated
     */
    private Boolean rewardType;

    /**
     * 创建日期：存的日期格式20160530
     *
     * @mbggenerated
     */
    private Integer curday;

    /**
     * 创建所属小时：24小时制
     *
     * @mbggenerated
     */
    private Boolean curhour;

    /**
     * 创建所属分钟：60分钟制
     *
     * @mbggenerated
     */
    private Boolean curminute;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Boolean isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCreateMemberId() {
        return createMemberId;
    }

    public void setCreateMemberId(Integer createMemberId) {
        this.createMemberId = createMemberId;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Boolean getRewardType() {
        return rewardType;
    }

    public void setRewardType(Boolean rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Boolean getCurhour() {
        return curhour;
    }

    public void setCurhour(Boolean curhour) {
        this.curhour = curhour;
    }

    public Boolean getCurminute() {
        return curminute;
    }

    public void setCurminute(Boolean curminute) {
        this.curminute = curminute;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}