package com.hz.gather.master.core.protocol.request.spread;

import com.hz.gather.master.core.protocol.base.BaseRequest;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/16 21:37
 * @Version 1.0
 */
public class RequestSpreadNotice extends BaseRequest implements Serializable {
    private static final long   serialVersionUID = 1233283332110L;
    public Long id;
    public RequestSpreadNotice(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
