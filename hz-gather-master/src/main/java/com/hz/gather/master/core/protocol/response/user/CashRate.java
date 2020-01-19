package com.hz.gather.master.core.protocol.response.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/15 20:14
 * @Version 1.0
 */
public class CashRate {
    /**
     * 状态
     */
    private  String   value;
    /**
     * 时间
     */
    private  String   create_time;
    /**
     * 金额
     */
    private  String   money;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
}
