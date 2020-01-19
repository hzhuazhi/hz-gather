package com.hz.gather.master.core.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class VcMemberRewardTotal {
    /**
     * 自增主键ID
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 用户ID:对应vc_member表的member_id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 总共产生金额
     *
     * @mbggenerated
     */
    private BigDecimal totalMoney;

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
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;


    /**
     * 是否需要计算 1、不需要 2是需要
     *
     * @mbggenerated
     */
    private Integer isCount;

    /**
     * 未计算金额
     *
     * @mbggenerated
     */
    private BigDecimal notCountMoney;

    /**
     * 领取过的奖励等级
     *
     * @mbggenerated
     */
    private Integer rewardLevel;

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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
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

    public Integer getCurhour() {
        return curhour;
    }

    public void setCurhour(Integer curhour) {
        this.curhour = curhour;
    }

    public Integer getCurminute() {
        return curminute;
    }

    public void setCurminute(Integer curminute) {
        this.curminute = curminute;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }

    public BigDecimal getNotCountMoney() {
        return notCountMoney;
    }

    public void setNotCountMoney(BigDecimal notCountMoney) {
        this.notCountMoney = notCountMoney;
    }

    public Integer getRewardLevel() {
        return rewardLevel;
    }

    public void setRewardLevel(Integer rewardLevel) {
        this.rewardLevel = rewardLevel;
    }
}