package com.hz.gather.master.core.protocol.request.login;

import com.hz.gather.master.core.protocol.base.BaseRequest;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/2 22:04
 * @Version 1.0
 */
public class SendSmsModel extends BaseRequest {
    /**
     * 类型  1、是注册 2、忘记密码 3、用户登录
     */
    private Integer smsType;

    /**
     * 电话号码
     */
    private String  phone;

    /**
     * 国家
     */
    private String  country;

    /**
     * 区号
     */
    private String  areaCode;

    /**
     * 协议版本
     */
    private String  version;

    /**
     * token
     */
    private String  token;




    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
