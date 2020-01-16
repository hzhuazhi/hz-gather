package com.hz.gather.master.core.protocol.response.user;

import com.hz.gather.master.core.protocol.base.BaseResponse;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/15 20:07
 * @Version 1.0
 */
public class ResponeseCashRate extends BaseResponse {
    List<CashRate> list;

    public List<CashRate> getList() {
        return list;
    }

    public void setList(List<CashRate> list) {
        this.list = list;
    }
}
