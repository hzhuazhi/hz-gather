package com.hz.gather.master.core.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UCashOutLog {
    /**
     * 自增长
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 收款支付宝账户
     *
     * @mbggenerated
     */
    private String receivaPayId;

    /**
     * 类型:  1、领取  2、体现
     *
     * @mbggenerated
     */
    private Boolean rewardType;

    /**
     * 金额
     *
     * @mbggenerated
     */
    private BigDecimal money;

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
     * 支付状态 1、未打款 2、打款失败 3、打款成功 
     *
     * @mbggenerated
     */
    private Boolean paymentType;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getReceivaPayId() {
        return receivaPayId;
    }

    public void setReceivaPayId(String receivaPayId) {
        this.receivaPayId = receivaPayId;
    }

    public Boolean getRewardType() {
        return rewardType;
    }

    public void setRewardType(Boolean rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public Boolean getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Boolean paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}