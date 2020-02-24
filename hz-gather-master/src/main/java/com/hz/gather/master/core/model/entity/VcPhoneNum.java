package com.hz.gather.master.core.model.entity;

public class VcPhoneNum {
    /**
     * 自增主键ID
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 手机号码
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;

    /**
     * 创建日期：存的日期格式20160530
     *
     * @mbggenerated
     */
    private Integer curday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }
}