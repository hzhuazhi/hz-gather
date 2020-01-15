package com.hz.gather.master.core.model.entity;

import java.math.BigDecimal;

public class SysNoticeAsk {
    /**
     * 自增长
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 要求累计累计奖励值
     *
     * @mbggenerated
     */
    private BigDecimal receiveMoney;

    /**
     * 是否完成: 1未完成  2、完成
     *
     * @mbggenerated
     */
    private Boolean isFinish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }
}