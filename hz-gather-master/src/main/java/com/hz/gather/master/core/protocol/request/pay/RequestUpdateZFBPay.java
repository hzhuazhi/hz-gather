package com.hz.gather.master.core.protocol.request.pay;

import com.hz.gather.master.core.model.user.CommonModel;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/15 14:49
 * @Version 1.0
 */
public class RequestUpdateZFBPay extends CommonModel {
    /**
     * 支付宝信息
     */
    private  String   oldZfbPayId;
    /**
     * 支付宝信息
     */
    private  String   zfbPayId;
    /**
     * 真实姓名
     */
    private  String   zfbName;

    public String getOldZfbPayId() {
        return oldZfbPayId;
    }

    public void setOldZfbPayId(String oldZfbPayId) {
        this.oldZfbPayId = oldZfbPayId;
    }

    public String getZfbPayId() {
        return zfbPayId;
    }

    public void setZfbPayId(String zfbPayId) {
        this.zfbPayId = zfbPayId;
    }

    public String getZfbName() {
        return zfbName;
    }

    public void setZfbName(String zfbName) {
        this.zfbName = zfbName;
    }
}
