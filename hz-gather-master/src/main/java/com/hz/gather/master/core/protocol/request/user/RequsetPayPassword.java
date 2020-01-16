package com.hz.gather.master.core.protocol.request.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/16 11:14
 * @Version 1.0
 */
public class RequsetPayPassword {
    /**
     * token
     */
    private String token;
    /**
     * 短信验证码
     */
    private  String  smsCode;
    /**
     * 时间戳
     */
    private  String  timeStamp;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
