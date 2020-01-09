package com.hz.gather.master.core.protocol.request.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 15:16
 * @Version 1.0
 */
public class RequestEditUser {
    /**
     * 用户token
     */
    private  String  token;
    /***
     * 头像地址
     */
    private String memberAdd;
    /***
     * 昵称
     */
    private String nickname;
    /***
     * 性别 1 男  2 、女
     */
    private String sex;
    /***
     * 生日
     */
    private String birthday;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
