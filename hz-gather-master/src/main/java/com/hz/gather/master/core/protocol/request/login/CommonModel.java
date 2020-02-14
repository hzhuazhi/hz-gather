package com.hz.gather.master.core.protocol.request.login;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/12 21:17
 * @Version 1.0
 */
public class CommonModel {
    /**
     * 渠道码
     */
    private  String   channel;
    /**
     * 渠道号
     */
    private  String   channelNum;
    /**
     * 推广码/推广包ID
     */
    private  String   spreadValue;

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
}
