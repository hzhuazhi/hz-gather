package com.hz.gather.master.core.model.entity;

import java.util.Date;

public class TbGaChannelRelationSpread {
    /**
     * 自增主键ID
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String relationName;

    /**
     * 渠道码:对应tb_ga_channel_m的channel字段
     *
     * @mbggenerated
     */
    private String channel;

    /**
     * 渠道号:对应tb_ga_channel_d的channel_num字段
     *
     * @mbggenerated
     */
    private String channelNum;

    /**
     * 推广码/推广包ID:对应tb_ga_spread_package的ID或者对应tb_ga_spread_code的code_num
     *
     * @mbggenerated
     */
    private String spreadValue;

    /**
     * 关联的平台类型:平台类型：1Android，2IOS
     *
     * @mbggenerated
     */
    private Integer clientType;

    /**
     * 关联类型：1自有公司，2第三方公司
     *
     * @mbggenerated
     */
    private Integer relationType;

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
     * TS时间
     *
     * @mbggenerated
     */
    private Date tsTime;

    /**
     * 是否有效：0有效，1无效/删除
     *
     * @mbggenerated
     */
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getSpreadValue() {
        return spreadValue;
    }

    public void setSpreadValue(String spreadValue) {
        this.spreadValue = spreadValue;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
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

    public Date getTsTime() {
        return tsTime;
    }

    public void setTsTime(Date tsTime) {
        this.tsTime = tsTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}