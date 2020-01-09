package com.hz.gather.master.core.model.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 11:11
 * @Version 1.0
 */
public class FriendModel {
    /**
     * 昵称
     */
    private String  nickname;
    /**
     * 用户头像地址
     */
    private String  nickadd;
    /**
     *  用户登记
     */
    private String  vip_type;
    /**
     * 创建时间
     */
    private String  create_time;
    /***
     * 裂变人数
     */
    private String  fission_people;
    /**
     * 收益总金额
     */
    private String  money;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickadd() {
        return nickadd;
    }

    public void setNickadd(String nickadd) {
        this.nickadd = nickadd;
    }

    public String getVip_type() {
        return vip_type;
    }

    public void setVip_type(String vip_type) {
        this.vip_type = vip_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFission_people() {
        return fission_people;
    }

    public void setFission_people(String fission_people) {
        this.fission_people = fission_people;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
