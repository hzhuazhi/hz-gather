package com.hz.gather.master.core.protocol.request.login;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/5 12:26
 * @Version 1.0
 */
public class ForgetPhoneModel {
    /**
     * 手机号
     */
    private  String  phone;

    /**
     * 验证码
     */
    private  String  verificationCode;

    /**
     * 时间戳
     */
    private  String  timeStamp;

    /**
     * 版本号
     */
    private  String  version;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
