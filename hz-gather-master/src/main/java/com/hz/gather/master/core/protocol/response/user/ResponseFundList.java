package com.hz.gather.master.core.protocol.response.user;

import com.hz.gather.master.core.protocol.base.BaseResponse;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 19:27
 * @Version 1.0
 */
public class ResponseFundList extends BaseResponse {
    /**
     * 小金库余额
     */
    private  String  total_money;
    /**
     *已领取总额
     */
    private  String  surplus_money;
    /**
     *已提现总额
     */
    private  String  cash_money;
    /**
     *列表数量
     */
    private  Integer  rowCount;
    /**
     *领取信息
     */
    private List<Object> list;

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getSurplus_money() {
        return surplus_money;
    }

    public void setSurplus_money(String surplus_money) {
        this.surplus_money = surplus_money;
    }

    public String getCash_money() {
        return cash_money;
    }

    public void setCash_money(String cash_money) {
        this.cash_money = cash_money;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
