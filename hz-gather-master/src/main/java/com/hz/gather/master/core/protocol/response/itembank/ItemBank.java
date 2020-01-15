package com.hz.gather.master.core.protocol.response.itembank;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/1/15 13:55
 * @Version 1.0
 */
public class ItemBank implements Serializable {
    private static final long   serialVersionUID = 1231023331141L;

    public Long id;
    public String question;
    public Integer seat;

    public ItemBank(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }
}
