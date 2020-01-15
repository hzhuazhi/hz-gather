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
    private  String    zfbPayId ;

    /**
     * 提现密码
     */
    private  String    payPassword ;

    public String getZfbPayId() {
        return zfbPayId;
    }

    public void setZfbPayId(String zfbPayId) {
        this.zfbPayId = zfbPayId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
