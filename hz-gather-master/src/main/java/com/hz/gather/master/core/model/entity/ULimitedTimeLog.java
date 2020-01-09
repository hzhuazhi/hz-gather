package com.hz.gather.master.core.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ULimitedTimeLog {
    /**
     * 自增长
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 批次号,48 小时后生成
     *
     * @mbggenerated
     */
    private String batchNum;

    /**
     * 直推数量
     *
     * @mbggenerated
     */
    private Integer pushNumber;

    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

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
    private Integer curhour;

    /**
     * 创建所属分钟：60分钟制
     *
     * @mbggenerated
     */
    private Integer curminute;

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
     * 失效时间
     *
     * @mbggenerated
     */
    private Date invalidTime;

    /**
     * 是否完成: 1未完成  2、完成
     *
     * @mbggenerated
     */
    private Integer isFinish;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;

    /**
     * 裂变达成奖励
     *
     * @mbggenerated
     */
    private BigDecimal fissionMoney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Integer getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(Integer pushNumber) {
        this.pushNumber = pushNumber;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Integer getCurminute() {
        return curminute;
    }

    public void setCurminute(Integer curminute) {
        this.curminute = curminute;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public BigDecimal getFissionMoney() {
        return fissionMoney;
    }

    public void setFissionMoney(BigDecimal fissionMoney) {
        this.fissionMoney = fissionMoney;
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

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Integer getCurhour() {
        return curhour;
    }

    public void setCurhour(Integer curhour) {
        this.curhour = curhour;
    }
}