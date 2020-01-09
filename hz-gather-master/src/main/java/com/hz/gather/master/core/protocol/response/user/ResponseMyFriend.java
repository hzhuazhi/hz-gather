package com.hz.gather.master.core.protocol.response.user;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/8 21:13
 * @Version 1.0
 */
public class ResponseMyFriend {
    /**
     * 直推人数(成为了永久vip)
     */
    private String  push_people_vip;
    /**
     * 下级人数(成为了永久vip)
     */
    private String  team_active_vip;
    /**
     * 直推总人数(所有的人包括所有状态)
     */
    private String  push_people_all;
    /**
     * 下级总人数(所有的人包括所有状态)
     */
    private String  team_active_all;
    /**
     * 直推列表信息
     */
    List<Object>   push_people_list;

    public String getPush_people_vip() {
        return push_people_vip;
    }

    public void setPush_people_vip(String push_people_vip) {
        this.push_people_vip = push_people_vip;
    }

    public String getTeam_active_vip() {
        return team_active_vip;
    }

    public void setTeam_active_vip(String team_active_vip) {
        this.team_active_vip = team_active_vip;
    }

    public String getPush_people_all() {
        return push_people_all;
    }

    public void setPush_people_all(String push_people_all) {
        this.push_people_all = push_people_all;
    }

    public String getTeam_active_all() {
        return team_active_all;
    }

    public void setTeam_active_all(String team_active_all) {
        this.team_active_all = team_active_all;
    }

    public List<Object> getPush_people_list() {
        return push_people_list;
    }

    public void setPush_people_list(List<Object> push_people_list) {
        this.push_people_list = push_people_list;
    }
}
