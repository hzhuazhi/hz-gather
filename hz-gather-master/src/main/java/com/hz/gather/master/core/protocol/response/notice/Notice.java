package com.hz.gather.master.core.protocol.response.notice;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/14 21:26
 * @Version 1.0
 */
public class Notice implements Serializable {
    private static final long   serialVersionUID = 1233023331141L;

    public String nickname;
    public Integer dataType;
    public String receiveMoney;

    public Notice(){

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(String receiveMoney) {
        this.receiveMoney = receiveMoney;
    }
}
