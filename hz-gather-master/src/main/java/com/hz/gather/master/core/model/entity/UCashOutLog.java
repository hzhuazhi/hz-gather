package com.hz.gather.master.core.model.entity;

import com.hz.gather.master.core.protocol.page.BasePage;

import java.math.BigDecimal;
import java.util.Date;

public class UCashOutLog extends BasePage {
    /**
     * 自增长
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 收款支付宝账户
     *
     * @mbggenerated
     */
    private String receivaPayId;
    /**
     * 收款支付宝账户
     *
     * @mbggenerated
     */
    private String outTradeNo;
    /**
     * 支付宝姓名
     *
     * @mbggenerated
     */
    private String zfbName;
    /**
     * 提示文案
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 金额
     *
     * @mbggenerated
     */
    private BigDecimal money;

    /**
     * 真实金额
     *
     * @mbggenerated
     */
    private BigDecimal realMoney;

    /**
     * 创建日期：存的日期格式20160530
     *
     * @mbggenerated
     */
    private Integer curday;

    /**
     * 创建所属小时：24小时制
     *
     * @mbggenerated
     */
    private Integer curhour;

    /**
     * 创建所属分钟：60分钟制
     *
     * @mbggenerated
     */
    private Integer curminute;

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
     * 支付状态 1、未打款 2、打款失败 3、打款成功 
     *
     * @mbggenerated
     */
    private Integer paymentType;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;

    /**
     * 运算次数
     *
     * @mbggenerated
     */
    private Integer runNum;
    /**
     * 运算状态
     *
     * @mbggenerated
     */
    private Integer runStatus;

    private Integer num;

    /**
     * 运算次数
     */
    private Integer handleNum;

    /**
     * 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     */
    private Integer handleStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getReceivaPayId() {
        return receivaPayId;
    }

    public void setReceivaPayId(String receivaPayId) {
        this.receivaPayId = receivaPayId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
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

    public Integer getCurhour() {
        return curhour;
    }

    public void setCurhour(Integer curhour) {
        this.curhour = curhour;
    }

    public Integer getCurminute() {
        return curminute;
    }

    public void setCurminute(Integer curminute) {
        this.curminute = curminute;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getZfbName() {
        return zfbName;
    }

    public void setZfbName(String zfbName) {
        this.zfbName = zfbName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getRunNum() {
        return runNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
    }

    public Integer getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Integer runStatus) {
        this.runStatus = runStatus;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getHandleNum() {
        return handleNum;
    }

    public void setHandleNum(Integer handleNum) {
        this.handleNum = handleNum;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }
}