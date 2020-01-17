package com.hz.gather.master.core.protocol.response.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/16 19:59
 * @Version 1.0
 */
public class ResponseFirstUqdatePayPw {
    /**
     * token
     */
    private String token;

    /**
     * 第一次密码
     */
    private String  payPw;
    /**
     * 第二次密码
     */
    private String  payPw2;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
