package com.hz.gather.master.core.protocol.request.pay;

import com.hz.gather.master.core.model.user.CommonModel;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/10 10:52
 * @Version 1.0
 */
public class RequestPayCashOut extends CommonModel {
    /**
     * 兑现金额
     */
    private  double   money ;
    /**
     * 支付宝id
     */
    private  String    alPayId ;


    public String getAlPayId() {
        return alPayId;
    }

    public void setAlPayId(String alPayId) {
        this.alPayId = alPayId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
