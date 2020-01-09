package com.hz.gather.master.core.model.entity;

import java.util.Date;

public class VcMemberPay {
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
     * 支付宝Id
     *
     * @mbggenerated
     */
    private String zfbPayid;

    /**
     * 类型:  1、领取  2、体现
     *
     * @mbggenerated
     */
    private Boolean rewardType;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getZfbPayid() {
        return zfbPayid;
    }

    public void setZfbPayid(String zfbPayid) {
        this.zfbPayid = zfbPayid;
    }

    public Boolean getRewardType() {
        return rewardType;
    }

    public void setRewardType(Boolean rewardType) {
        this.rewardType = rewardType;
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