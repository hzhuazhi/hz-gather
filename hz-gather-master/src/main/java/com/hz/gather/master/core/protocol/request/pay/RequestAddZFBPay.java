package com.hz.gather.master.core.protocol.request.pay;

import com.hz.gather.master.core.model.user.CommonModel;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 22:38
 * @Version 1.0
 */
public class RequestAddZFBPay extends CommonModel {
    /**
     * 支付宝信息
     */
    private  String   zfbPayId;

    public String getZfbPayId() {
        return zfbPayId;
    }

    public void setZfbPayId(String zfbPayId) {
        this.zfbPayId = zfbPayId;
    }
}
