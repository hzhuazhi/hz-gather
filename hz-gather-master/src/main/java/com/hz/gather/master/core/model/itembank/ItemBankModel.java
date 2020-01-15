package com.hz.gather.master.core.model.itembank;

import com.hz.gather.master.core.protocol.page.BasePage;

import java.io.Serializable;

/**
 * @Description 密保的实体属性Bean
 * @Author yoko
 * @Date 2020/1/15 11:30
 * @Version 1.0
 */
public class ItemBankModel extends BasePage implements Serializable {
    private static final long   serialVersionUID = 1203223301144L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 名称
     */
    private String bkName;

    /**
     * 问题
     */
    private String question;

    /**
     * 位置（顺序）
     */
    private Integer seat;


    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBkName() {
        return bkName;
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
