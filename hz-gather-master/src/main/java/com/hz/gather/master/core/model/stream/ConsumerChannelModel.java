package com.hz.gather.master.core.model.stream;

import java.io.Serializable;

/**
 * @Description 用户的渠道码、渠道号/推广码信息
 * @Author yoko
 * @Date 2020/2/11 18:50
 * @Version 1.0
 */
public class ConsumerChannelModel implements Serializable {
    private static final long   serialVersionUID = 1233223901344L;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 注册时_渠道码:对应tb_ga_channel_m的channel字段
     */
    private String r_channel;

    /**
     * 注册时_渠道号:对应tb_ga_channel_d的channel_num字段
     */
    private String r_channelNum;

    /**
     * 注册时_推广码/推广包ID:对应tb_ga_spread_package的ID或者对应tb_ga_spread_code的code_num
     */
    private String r_spreadValue;

    /**
     * 注册时_关联类型：1自有公司，2第三方公司
     */
    private Integer r_relationType;


    /**
     * 登录时_渠道码:对应tb_ga_channel_m的channel字段
     */
    private String l_channel;

    /**
     * 登录时_渠道号:对应tb_ga_channel_d的channel_num字段
     */
    private String l_channelNum;

    /**
     * 登录时_推广码/推广包ID:对应tb_ga_spread_package的ID或者对应tb_ga_spread_code的code_num
     */
    private String l_spreadValue;

    /**
     * 登录时_关联类型：1自有公司，2第三方公司
     */
    private Integer l_relationType;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getR_channel() {
        return r_channel;
    }

    public void setR_channel(String r_channel) {
        this.r_channel = r_channel;
    }

    public String getR_channelNum() {
        return r_channelNum;
    }

    public void setR_channelNum(String r_channelNum) {
        this.r_channelNum = r_channelNum;
    }

    public String getR_spreadValue() {
        return r_spreadValue;
    }

    public void setR_spreadValue(String r_spreadValue) {
        this.r_spreadValue = r_spreadValue;
    }

    public Integer getR_relationType() {
        return r_relationType;
    }

    public void setR_relationType(Integer r_relationType) {
        this.r_relationType = r_relationType;
    }

    public String getL_channel() {
        return l_channel;
    }

    public void setL_channel(String l_channel) {
        this.l_channel = l_channel;
    }

    public String getL_channelNum() {
        return l_channelNum;
    }

    public void setL_channelNum(String l_channelNum) {
        this.l_channelNum = l_channelNum;
    }

    public String getL_spreadValue() {
        return l_spreadValue;
    }

    public void setL_spreadValue(String l_spreadValue) {
        this.l_spreadValue = l_spreadValue;
    }

    public Integer getL_relationType() {
        return l_relationType;
    }

    public void setL_relationType(Integer l_relationType) {
        this.l_relationType = l_relationType;
    }
}
