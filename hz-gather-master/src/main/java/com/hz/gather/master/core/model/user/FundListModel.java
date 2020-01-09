package com.hz.gather.master.core.model.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 19:38
 * @Version 1.0
 */
public class FundListModel {
    /**
     * 类型:  1、领取  2、提现
     */
    public   String  reward_type;
    /**
     * 符号类型:1、加 2、减
     */
    public   String  symbol_type;
    /**
     * 金额
     */
    public   String  money;
    /**
     * 创建时间
     */
    public   String  create_time;

    public String getReward_type() {
        return reward_type;
    }

    public void setReward_type(String reward_type) {
        this.reward_type = reward_type;
    }

    public String getSymbol_type() {
        return symbol_type;
    }

    public void setSymbol_type(String symbol_type) {
        this.symbol_type = symbol_type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
