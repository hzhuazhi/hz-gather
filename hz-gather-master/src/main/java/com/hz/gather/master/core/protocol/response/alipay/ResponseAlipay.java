package com.hz.gather.master.core.protocol.response.alipay;


import com.hz.gather.master.core.protocol.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/19 20:57
 * @Version 1.0
 */
public class ResponseAlipay extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331101L;

    public String aliOrder;


    public ResponseAlipay(){

    }

    public String getAliOrder() {
        return aliOrder;
    }

    public void setAliOrder(String aliOrder) {
        this.aliOrder = aliOrder;
    }
}
