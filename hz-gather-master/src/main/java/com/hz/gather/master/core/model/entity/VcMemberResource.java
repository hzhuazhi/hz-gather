package com.hz.gather.master.core.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VcMemberResource {
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
     * 已领取金额
     *
     * @mbggenerated
     */
    private BigDecimal alreadyMoney;

    /**
     * 剩余金额
     *
     * @mbggenerated
     */
    private BigDecimal surplusMoney;

    /**
     * 已兑现金额
     *
     * @mbggenerated
     */
    private BigDecimal cashMoney;

    /**
     * 直推人数(成为了永久vip)
     *
     * @mbggenerated
     */
    private Integer pushPeople;

    /**
     * 下级人数(成为了永久vip)
     *
     * @mbggenerated
     */
    private Integer teamPeople;

    /**
     * 直推总人数(所有的人包括所有状态)
     *
     * @mbggenerated
     */
    private Integer pushPeopleAll;
    /**
     * 裂变收益总人数(包括自己的级数)
     *
     * @mbggenerated
     */
    private Integer fissionPeopleNum;

    /**
     * 下级总人数(所有的人包括所有状态)
     *
     * @mbggenerated
     */
    private Integer teamActiveAll;

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
     * 裂变未达成奖励
     *
     * @mbggenerated
     */
    private BigDecimal fissionMoney;


    /**
     * 直推金额
     *
     * @mbggenerated
     */
    private BigDecimal pushMoney;

    /**
     * memberid  集合
     *
     * @mbggenerated
     */
    private List<Integer> idList;

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

    public BigDecimal getAlreadyMoney() {
        return alreadyMoney;
    }

    public void setAlreadyMoney(BigDecimal alreadyMoney) {
        this.alreadyMoney = alreadyMoney;
    }

    public BigDecimal getSurplusMoney() {
        return surplusMoney;
    }

    public void setSurplusMoney(BigDecimal surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    public BigDecimal getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(BigDecimal cashMoney) {
        this.cashMoney = cashMoney;
    }

    public Integer getPushPeople() {
        return pushPeople;
    }

    public void setPushPeople(Integer pushPeople) {
        this.pushPeople = pushPeople;
    }

    public Integer getTeamPeople() {
        return teamPeople;
    }

    public void setTeamPeople(Integer teamPeople) {
        this.teamPeople = teamPeople;
    }

    public Integer getPushPeopleAll() {
        return pushPeopleAll;
    }

    public void setPushPeopleAll(Integer pushPeopleAll) {
        this.pushPeopleAll = pushPeopleAll;
    }

    public Integer getTeamActiveAll() {
        return teamActiveAll;
    }

    public void setTeamActiveAll(Integer teamActiveAll) {
        this.teamActiveAll = teamActiveAll;
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

    public BigDecimal getPushMoney() {
        return pushMoney;
    }

    public void setPushMoney(BigDecimal pushMoney) {
        this.pushMoney = pushMoney;
    }

    public Integer getFissionPeopleNum() {
        return fissionPeopleNum;
    }

    public void setFissionPeopleNum(Integer fissionPeopleNum) {
        this.fissionPeopleNum = fissionPeopleNum;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}