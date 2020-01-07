package com.hz.gather.master.util;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.model.alipay.AlipayModel;
import com.hz.gather.master.core.model.region.RegionModel;
import com.hz.gather.master.core.protocol.request.RequestAlipay;
import com.hz.gather.master.core.protocol.response.alipay.ResponseAlipay;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 公共方法类
 * @Author yoko
 * @Date 2020/1/7 20:25
 * @Version 1.0
 */
public class HodgepodgeMethod {
    private static Logger log = LoggerFactory.getLogger(HodgepodgeMethod.class);

    /**
     * @Description: 组装查询地域的查询条件
     * @param ip
     * @return RegionModel
     * @author yoko
     * @date 2019/12/18 18:41
     */
    public static RegionModel assembleRegionModel(String ip){
        RegionModel resBean = new RegionModel();
        resBean.setIp(ip);
        return resBean;
    }

    /**
     * @Description: 组装阿里支付的订单访问数据
     * @param requestAlipay - 阿里支付订单的基本信息
     * @param outTradeNo - 交易订单号
     * @param totalAmount - 订单总金额
     * @return AlipayModel
     * @author yoko
     * @date 2019/12/19 20:36
     */
    public static AlipayModel assembleAlipayData(RequestAlipay requestAlipay, String outTradeNo, String totalAmount){
        AlipayModel resBean = new AlipayModel();
        if (requestAlipay != null){
            if (!StringUtils.isBlank(requestAlipay.body)){
                resBean.body = requestAlipay.body;
            }else {
                resBean.body = "实名认证";
            }
            if (!StringUtils.isBlank(requestAlipay.subject)){
                resBean.subject = requestAlipay.subject;
            }else {
                resBean.subject = "趣红人实名认证";
            }
            if (!StringUtils.isBlank(requestAlipay.outTradeNo)){
                resBean.outTradeNo = requestAlipay.outTradeNo;
            }else {
                resBean.outTradeNo = outTradeNo;
            }
            if (!StringUtils.isBlank(requestAlipay.timeoutExpress)){
                resBean.timeoutExpress = requestAlipay.timeoutExpress;
            }else {
                resBean.timeoutExpress = "30m";
            }
            if (!StringUtils.isBlank(requestAlipay.totalAmount)){
                resBean.totalAmount = requestAlipay.totalAmount;
            }else {
                resBean.totalAmount = totalAmount;
            }
            if (!StringUtils.isBlank(requestAlipay.productCode)){
                resBean.productCode = requestAlipay.productCode;
            }else {
                resBean.productCode = "QHR_SMRZ";
            }
        }
        return resBean;
    }

    /**
     * @Description: 开市时间的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param aliOrder - 调用阿里支付返回的订单码
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleAlipayResult(long stime, String token, String sign, String aliOrder){
        ResponseAlipay dataModel = new ResponseAlipay();
        dataModel.aliOrder = aliOrder;
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }
}
