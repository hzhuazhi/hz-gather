package com.hz.gather.master.core.model.login;

/**
 * @Description 注册信息信息
 * @Author long
 * @Date 2020/1/2 16:14
 * @Version 1.0
 */
public class LoginModel {
    /**
     * 协议版本号
     */
    private  String  version;
    /**
     * 短信验证码
     */
    private  String  smsCode;
    /**
     * 时间戳
     */
    private  String  timeStamp;
    /**
     * 电话号码
     */
    private  String  phone;

    /**
     * 密码啊
     */
    private  String  passWrod;
    /**
     * 邀请码
     */
    private  String  inviteCode;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWrod() {
        return passWrod;
    }

    public void setPassWrod(String passWrod) {
        this.passWrod = passWrod;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
