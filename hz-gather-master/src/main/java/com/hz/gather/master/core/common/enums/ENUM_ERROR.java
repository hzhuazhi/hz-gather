package com.hz.gather.master.core.common.enums;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/2 17:32
 * @Version 1.0
 */
public enum ENUM_ERROR {

    SERVER_ERRER("A00001", "手机号码已经注册!", "注册模块、手机号码已经注册!"),
    SERVER_OK("0", "请求正常！", "请求正常!"),
    INVALID_USER("-1", "无效的用户信息，请重新登陆！", "无效的用户信息，请重新登陆!"),

    /***********************************************
     * 登录、注册、忘记密码类的业务
     **********************************************/
    A00001("A00001", "手机号码已经注册!", "注册模块、手机号码已经注册!"),
    A00002("A00002", "无效的邀请码!", "注册模块、无效的邀请码!"),
    A00003("A00003", "发送验证码已达到上线!", "注册模块、发送验证码已达到上线!"),
    A00004("A00004", "发送短信失败!", "注册模块、发送短信失败!"),
    A00005("A00005", "二次密码输入不一致,请仔细核对后再进行提交!", "提交注册信息、二次密码输入不一致!"),
    A00006("A00006", "无效的用户信息!", "提交注册信息、数据异常!"),
    A00007("A00007", "无效的验证码!", "提交注册信息、无效的验证码!"),
    A00008("A00008", "验证码已过期!", "登录模块、发送短信失败!"),
    A00009("A00009", "手机号未注册!", "登录模块、手机号未注册!"),
    A00010("A00010", "手机号或者密码错误!", "手机号或者密码错误!"),
    A00011("A00011", "手机号码不存在!", "忘记密码、手机号码不存在!"),
    A00012("A00012", "用户token已经超时!", "忘记密码、用户token已经超时!"),
    A00013("A00013", "无效的请求信息!", "无效的请求信息!"),
    A00014("A00014", "请求已过期!", "请求已过期!"),
    A00015("A00015", "修改密码失败，请重试!", "修改密码失败，请重试!"),
    A00016("A00016", "用户数据异常，请联系管理员!", "用户数据异常，请联系管理员!"),

    ;
    /**
     * 错误码
     */
    private String eCode;
    /**
     * 给客户端看的错误信息
     */
    private String eDesc;
    /**
     * 插入数据库的错误信息
     */
    private String dbDesc;

    private ENUM_ERROR(String eCode, String eDesc, String dbDesc) {
        this.eCode = eCode;
        this.eDesc = eDesc;
        this.dbDesc =dbDesc;
    }

    public String geteCode() {
        return eCode;
    }

    public void seteCode(String eCode) {
        this.eCode = eCode;
    }

    public String geteDesc() {
        return eDesc;
    }

    public void seteDesc(String eDesc) {
        this.eDesc = eDesc;
    }

    public String getDbDesc() {
        return dbDesc;
    }

    public void setDbDesc(String dbDesc) {
        this.dbDesc = dbDesc;
    }
}
