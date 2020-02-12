package com.hz.gather.master.core.model.stream;


import java.io.Serializable;

/**
 * @Description 用户访问流水的实体属性Bean
 * @Author yoko
 * @Date 2019/12/18 14:05
 * @Version 1.0
 */
public class StreamConsumerModel implements Serializable {
    private static final long   serialVersionUID = 1233223301344L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 服务端此次访问的会话ID，cgid表示客户端
     */
    private String sgid;

    /**
     * 客户端会话ID
     */
    private String cgid;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 用户的IP
     */
    private String ip;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * Android版本号
     */
    private String androidVer;

    /**
     * 客户端版本号
     */
    private Integer clientVer;

    /**
     * 用户访问的行为：具体访问哪个接口，后续使用枚举归类
     */
    private Integer actionType;

    /**
     * 用户访问的行为的名称
     */
    private String actionName;

    /**
     * 访问的请求上来的参数
     */
    private String parametJson;

    /**
     * 服务端返回给终端的数据
     */
    private String resultJson;

    /**
     * 错误码：虽然属于冗余字段，但是还是想直观点
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 协议版本号：与终端特殊定义时可能需要用到这个字段
     */
    private Integer agtVer;

    /**
     * 注册时_渠道码:对应tb_ga_channel_m的channel字段
     */
    private String r_channel;

    /**
     * 注册时_渠道号:对应tb_ga_channel_d的channel_num字段
     */
    private String r_channelNum;

    /**
     * 注册时_推广码/推广包ID:对应tb_ga_spread_package的ID或者对应tb_ga_spread_code的code_num
     */
    private String r_spreadValue;

    /**
     * 注册时_关联类型：1自有公司，2第三方公司
     */
    private Integer r_relationType;


    /**
     * 登录时_渠道码:对应tb_ga_channel_m的channel字段
     */
    private String l_channel;

    /**
     * 登录时_渠道号:对应tb_ga_channel_d的channel_num字段
     */
    private String l_channelNum;

    /**
     * 登录时_推广码/推广包ID:对应tb_ga_spread_package的ID或者对应tb_ga_spread_code的code_num
     */
    private String l_spreadValue;

    /**
     * 登录时_关联类型：1自有公司，2第三方公司
     */
    private Integer l_relationType;

    /**
     * 平台类型:平台类型：1Android，2IOS
     */
    private Integer clientType;

    /**
     * 登录的token
     */
    private String token;

    /**
     *运行计算次数
     */
    private int runNum;

    /**
     *运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     */
    private int runStatus;

    /**
     * 创建日期：存的日期格式20160530
     */
    private int curday;

    /**
     * 创建所属小时：24小时制
     */
    private int curhour;

    /**
     * 创建所属分钟：60分钟制
     */
    private int curminute;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     *是否有效：0初始化，1失效/删除
     */
    private int yn;

    /**
     * 表的下表位
     */
    private String suffix;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSgid() {
        return sgid;
    }

    public void setSgid(String sgid) {
        this.sgid = sgid;
    }

    public String getCgid() {
        return cgid;
    }

    public void setCgid(String cgid) {
        this.cgid = cgid;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getAndroidVer() {
        return androidVer;
    }

    public void setAndroidVer(String androidVer) {
        this.androidVer = androidVer;
    }

    public Integer getClientVer() {
        return clientVer;
    }

    public void setClientVer(Integer clientVer) {
        this.clientVer = clientVer;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getParametJson() {
        return parametJson;
    }

    public void setParametJson(String parametJson) {
        this.parametJson = parametJson;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Integer getAgtVer() {
        return agtVer;
    }

    public void setAgtVer(Integer agtVer) {
        this.agtVer = agtVer;
    }

    public int getRunNum() {
        return runNum;
    }

    public void setRunNum(int runNum) {
        this.runNum = runNum;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public int getCurday() {
        return curday;
    }

    public void setCurday(int curday) {
        this.curday = curday;
    }

    public int getCurhour() {
        return curhour;
    }

    public void setCurhour(int curhour) {
        this.curhour = curhour;
    }

    public int getCurminute() {
        return curminute;
    }

    public void setCurminute(int curminute) {
        this.curminute = curminute;
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

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getR_channel() {
        return r_channel;
    }

    public void setR_channel(String r_channel) {
        this.r_channel = r_channel;
    }

    public String getR_channelNum() {
        return r_channelNum;
    }

    public void setR_channelNum(String r_channelNum) {
        this.r_channelNum = r_channelNum;
    }

    public String getR_spreadValue() {
        return r_spreadValue;
    }

    public void setR_spreadValue(String r_spreadValue) {
        this.r_spreadValue = r_spreadValue;
    }

    public Integer getR_relationType() {
        return r_relationType;
    }

    public void setR_relationType(Integer r_relationType) {
        this.r_relationType = r_relationType;
    }

    public String getL_channel() {
        return l_channel;
    }

    public void setL_channel(String l_channel) {
        this.l_channel = l_channel;
    }

    public String getL_channelNum() {
        return l_channelNum;
    }

    public void setL_channelNum(String l_channelNum) {
        this.l_channelNum = l_channelNum;
    }

    public String getL_spreadValue() {
        return l_spreadValue;
    }

    public void setL_spreadValue(String l_spreadValue) {
        this.l_spreadValue = l_spreadValue;
    }

    public Integer getL_relationType() {
        return l_relationType;
    }

    public void setL_relationType(Integer l_relationType) {
        this.l_relationType = l_relationType;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
