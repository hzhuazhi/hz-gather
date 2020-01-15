package com.hz.gather.master.core.protocol.request.itembank;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/15 16:34
 * @Version 1.0
 */
public class ItemBankAnswer implements Serializable {
    private static final long   serialVersionUID = 1231023331241L;

    public Long itemBankId;
    public String answer;

    public ItemBankAnswer(){

    }

    public Long getItemBankId() {
        return itemBankId;
    }

    public void setItemBankId(Long itemBankId) {
        this.itemBankId = itemBankId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
