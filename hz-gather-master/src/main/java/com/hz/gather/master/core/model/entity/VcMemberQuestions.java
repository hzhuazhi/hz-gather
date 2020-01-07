package com.hz.gather.master.core.model.entity;

import java.util.Date;

public class VcMemberQuestions {
    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 问题提示1
     *
     * @mbggenerated
     */
    private String problem1;

    /**
     * 问题答案1
     *
     * @mbggenerated
     */
    private String answer1;

    /**
     * 问题提示2
     *
     * @mbggenerated
     */
    private String problem2;

    /**
     * 问题答案2
     *
     * @mbggenerated
     */
    private String answer2;

    /**
     * 问题提示3
     *
     * @mbggenerated
     */
    private String problem3;

    /**
     * 问题答案3
     *
     * @mbggenerated
     */
    private String answer3;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getProblem1() {
        return problem1;
    }

    public void setProblem1(String problem1) {
        this.problem1 = problem1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getProblem2() {
        return problem2;
    }

    public void setProblem2(String problem2) {
        this.problem2 = problem2;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getProblem3() {
        return problem3;
    }

    public void setProblem3(String problem3) {
        this.problem3 = problem3;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



}