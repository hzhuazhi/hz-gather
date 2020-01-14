package com.hz.gather.master.core.protocol.response.pay;

import com.hz.gather.master.core.protocol.request.pay.RequestAddZFBPay;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 23:15
 * @Version 1.0
 */
public class ResponeseHavaPayInfo {
    private  List<RequestAddZFBPay> payList;

    public List<RequestAddZFBPay> getPayList() {
        return payList;
    }

    public void setPayList(List<RequestAddZFBPay> payList) {
        this.payList = payList;
    }
}
