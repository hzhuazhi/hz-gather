package com.hz.gather.master.core.protocol.response.spread;

import com.hz.gather.master.core.protocol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/16 21:38
 * @Version 1.0
 */
public class ResponseSpreadNotice extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023311141L;
    public List<SpreadNotice> sdList;
    public SpreadNotice sd;

    public Integer rowCount;
    public ResponseSpreadNotice(){

    }

    public List<SpreadNotice> getSdList() {
        return sdList;
    }

    public void setSdList(List<SpreadNotice> sdList) {
        this.sdList = sdList;
    }

    public SpreadNotice getSd() {
        return sd;
    }

    public void setSd(SpreadNotice sd) {
        this.sd = sd;
    }

    @Override
    public Integer getRowCount() {
        return rowCount;
    }

    @Override
    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
