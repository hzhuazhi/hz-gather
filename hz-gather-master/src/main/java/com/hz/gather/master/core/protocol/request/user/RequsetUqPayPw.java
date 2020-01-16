package com.hz.gather.master.core.protocol.request.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/16 17:35
 * @Version 1.0
 */
public class RequsetUqPayPw {
    private String  token;
    private String  pwKey;
    private String  payPw;
    private String  payPw2;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPwKey() {
        return pwKey;
    }

    public void setPwKey(String pwKey) {
        this.pwKey = pwKey;
    }

    public String getPayPw() {
        return payPw;
    }

    public void setPayPw(String payPw) {
        this.payPw = payPw;
    }

    public String getPayPw2() {
        return payPw2;
    }

    public void setPayPw2(String payPw2) {
        this.payPw2 = payPw2;
    }
}
