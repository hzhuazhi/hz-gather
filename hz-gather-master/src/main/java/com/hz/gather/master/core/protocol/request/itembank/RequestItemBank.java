package com.hz.gather.master.core.protocol.request.itembank;

import com.hz.gather.master.core.protocol.base.BaseRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/15 13:40
 * @Version 1.0
 */
public class RequestItemBank extends BaseRequest implements Serializable {
    private static final long   serialVersionUID = 1133223332140L;

    public List<ItemBankAnswer> answerList;
    public RequestItemBank(){

    }

    public List<ItemBankAnswer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<ItemBankAnswer> answerList) {
        this.answerList = answerList;
    }
}
