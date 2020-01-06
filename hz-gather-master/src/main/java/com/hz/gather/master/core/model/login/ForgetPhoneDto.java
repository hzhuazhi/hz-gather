package com.hz.gather.master.core.model.login;

/**
 * @Description 手机号码绑定token 信息进行修改
 * @Author long
 * @Date 2020/1/5 12:26
 * @Version 1.0
 */
public class ForgetPhoneDto {
    /**
     * 修改密码token
     */
    private  String pwToken;

    public String getPwToken() {
        return pwToken;
    }

    public void setPwToken(String pwToken) {
        this.pwToken = pwToken;
    }
}
