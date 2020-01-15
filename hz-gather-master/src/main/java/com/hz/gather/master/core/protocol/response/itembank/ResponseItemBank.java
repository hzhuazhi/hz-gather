package com.hz.gather.master.core.protocol.response.itembank;

import com.hz.gather.master.core.protocol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/15 13:42
 * @Version 1.0
 */
public class ResponseItemBank extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023301141L;
    public List<ItemBank> bkList;

    public Integer rowCount;
    public ResponseItemBank(){

    }

    public List<ItemBank> getBkList() {
        return bkList;
    }

    public void setBkList(List<ItemBank> bkList) {
        this.bkList = bkList;
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
