package com.hz.gather.master.core.protocol.response.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/14 19:05
 * @Version 1.0
 */
public class UMoneyLogResp {
    /**
     * 类型:  1、裂变收益  2、提现成功
     */
    private  String  reward_type_value;
    /**
     * 符号类型:  1、收入  2、支出
     */
    private  String  symbol_type_value;
    /**
     * 符号类型:  1、收入  2、支出
     */
    private  Integer  symbol_type_key;
    /**
     * 时间
     */
    private  String  create_time;
    /**
     * 金额
     */
    private  String  money;
    /**
     * token
     */
    private  String  token;

    public String getReward_type_value() {
        return reward_type_value;
    }

    public void setReward_type_value(String reward_type_value) {
        this.reward_type_value = reward_type_value;
    }

    public String getSymbol_type_value() {
        return symbol_type_value;
    }

    public void setSymbol_type_value(String symbol_type_value) {
        this.symbol_type_value = symbol_type_value;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSymbol_type_key() {
        return symbol_type_key;
    }

    public void setSymbol_type_key(Integer symbol_type_key) {
        this.symbol_type_key = symbol_type_key;
    }
}
