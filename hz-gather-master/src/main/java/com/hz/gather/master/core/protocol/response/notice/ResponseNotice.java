package com.hz.gather.master.core.protocol.response.notice;

import com.hz.gather.master.core.protocol.base.BaseResponse;
import com.hz.gather.master.core.protocol.response.question.QuestionD;
import com.hz.gather.master.core.protocol.response.question.QuestionM;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/14 21:26
 * @Version 1.0
 */
public class ResponseNotice extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331141L;
    public List<Notice> ncList;
    public Integer rowCount;

    public ResponseNotice(){

    }

    public List<Notice> getNcList() {
        return ncList;
    }

    public void setNcList(List<Notice> ncList) {
        this.ncList = ncList;
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
