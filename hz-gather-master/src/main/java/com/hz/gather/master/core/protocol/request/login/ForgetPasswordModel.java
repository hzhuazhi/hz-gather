package com.hz.gather.master.core.protocol.request.login;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/3 22:07
 * @Version 1.0
 */
public class ForgetPasswordModel {
    /**
     * 修改密码token
     */
    private  String pwToken;
    /**
     * 第一次密码
     */
    private  String passWord;

    /**
     * 确认密码
     */
    private  String passWordConfirm;

    public String getPwToken() {
        return pwToken;
    }

    public void setPwToken(String pwToken) {
        this.pwToken = pwToken;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWordConfirm() {
        return passWordConfirm;
    }

    public void setPassWordConfirm(String passWordConfirm) {
        this.passWordConfirm = passWordConfirm;
    }
}
