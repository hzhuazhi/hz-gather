package com.hz.gather.master.core.protocol.response.login;

/**
 * @Description 修改密码返回数据
 * @Author long
 * @Date 2020/1/3 22:13
 * @Version 1.0
 */
public class ForgetPasswordDto {

    private  String pwToken;

    public String getPwToken() {
        return pwToken;
    }

    public void setPwToken(String pwToken) {
        this.pwToken = pwToken;
    }
}
