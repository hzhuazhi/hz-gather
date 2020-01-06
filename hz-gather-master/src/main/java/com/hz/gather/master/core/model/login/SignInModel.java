package com.hz.gather.master.core.model.login;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/6 14:39
 * @Version 1.0
 */
public class SignInModel {
    /**
     * 类型 1、密码登录  2、验证码登录
     */
    private  Integer type;

    /**
     * 手机号码
     */
    private  String phone;
    /**
     * 密码
     */
    private  String password;
    /**
     * 版本号
     */
    private  String version;
    /**
     * 手机验证码
     */
    private  String smsCode;

    /**
     * 时间戳
     */
    private  String timeStamp;
    /**
     * 时间戳
     */
    private  String st;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
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
