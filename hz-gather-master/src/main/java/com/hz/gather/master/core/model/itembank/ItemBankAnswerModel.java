package com.hz.gather.master.core.model.itembank;

import com.hz.gather.master.core.protocol.page.BasePage;

import java.io.Serializable;

/**
 * @Description 密保答案的实体属性Bean
 * @Author yoko
 * @Date 2020/1/15 14:33
 * @Version 1.0
 */
public class ItemBankAnswerModel extends BasePage implements Serializable {
    private static final long   serialVersionUID = 1203223301146L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 对应的密保:对应表tb_ga_item_bank的主键ID
     */
    private Long itemBankId;

    /**
     * 答案
     */
    private String answer;


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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
