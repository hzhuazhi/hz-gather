package com.hz.gather.master.core.model.entity;

import java.math.BigDecimal;

public class VcMember {
    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 所属用户id
     *
     * @mbggenerated
     */
    private Integer ownerMemberId;

    /**
     * 会员头像
     *
     * @mbggenerated
     */
    private String memberAdd;

    /**
     * 会员昵称
     *
     * @mbggenerated
     */
    private String nickname;

    /**
     * 手机号码
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * 性别 1、男 2、女
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 生日
     *
     * @mbggenerated
     */
    private String birthday;

    /**
     * 国家
     *
     * @mbggenerated
     */
    private String country;

    /**
     * 省
     *
     * @mbggenerated
     */
    private String province;

    /**
     * 市
     *
     * @mbggenerated
     */
    private String city;

    /**
     * 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 会员编号
     *
     * @mbggenerated
     */
    private String memberCode;

    /**
     * 会员类型 1、归属 2、邀请制
     *
     * @mbggenerated
     */
    private Integer memberType;

    /**
     * 邀请码
     *
     * @mbggenerated
     */
    private String inviteCode;


    /**
     * 交易所地址
     *
     * @mbggenerated
     */
    private String tradingAddress;
    /**
     * token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * 用户注册时间
     *
     * @mbggenerated
     */
    private Integer createTime;

    /**
     * 用户当前状态：1、正常用户 2、黑名单
     *
     * @mbggenerated
     */
    private Integer isActive;

    /**
     * 用户登录时间
     *
     * @mbggenerated
     */
    private Integer loginTime;

    /**
     * 设备id
     *
     * @mbggenerated
     */
    private String deviceId;

    /**
     * 上级id
     *
     * @mbggenerated
     */
    private Integer superiorId;

    /**
     * 用户支付密码
     *
     * @mbggenerated
     */
    private String payPassword;

    /**
     * 推广人id
     *
     * @mbggenerated
     */
    private String extensionMemberId;

    /**
     * 等级状态  0、普通用户  1、限时VIP 2、永久VIP
     *
     * @mbggenerated
     */
    private Integer gradeType;

    /**
     * 是否开启问答修改密码  0、为开启 1、开启
     *
     * @mbggenerated
     */
    private String isQuestions;
    /**
     * 奖励收益人id
     *
     * @mbggenerated
     */
    private String benefitMemberId;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;

    /**
     * 总共产生金额
     *
     * @mbggenerated
     */
    private BigDecimal totalMoney;


    /**
     * 直推人数(成为了永久vip)
     *
     * @mbggenerated
     */
    private Integer pushPeople;

    /**
     * 下级人数(成为了永久vip)
     *
     * @mbggenerated
     */
    private Integer teamActive;




    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getOwnerMemberId() {
        return ownerMemberId;
    }

    public void setOwnerMemberId(Integer ownerMemberId) {
        this.ownerMemberId = ownerMemberId;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getTradingAddress() {
        return tradingAddress;
    }

    public void setTradingAddress(String tradingAddress) {
        this.tradingAddress = tradingAddress;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getExtensionMemberId() {
        return extensionMemberId;
    }

    public void setExtensionMemberId(String extensionMemberId) {
        this.extensionMemberId = extensionMemberId;
    }

    public Integer getGradeType() {
        return gradeType;
    }

    public void setGradeType(Integer gradeType) {
        this.gradeType = gradeType;
    }

    public String getIsQuestions() {
        return isQuestions;
    }

    public void setIsQuestions(String isQuestions) {
        this.isQuestions = isQuestions;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBenefitMemberId() {
        return benefitMemberId;
    }

    public void setBenefitMemberId(String benefitMemberId) {
        this.benefitMemberId = benefitMemberId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getPushPeople() {
        return pushPeople;
    }

    public void setPushPeople(Integer pushPeople) {
        this.pushPeople = pushPeople;
    }

    public Integer getTeamActive() {
        return teamActive;
    }

    public void setTeamActive(Integer teamActive) {
        this.teamActive = teamActive;
    }
}