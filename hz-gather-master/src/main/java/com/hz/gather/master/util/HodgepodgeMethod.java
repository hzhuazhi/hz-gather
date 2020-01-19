package com.hz.gather.master.util;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.DateUtil;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.ErrorCode;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.alipay.AlipayH5Model;
import com.hz.gather.master.core.model.alipay.AlipayModel;
import com.hz.gather.master.core.model.alipay.AlipayNotifyModel;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.itembank.ItemBankAnswerModel;
import com.hz.gather.master.core.model.itembank.ItemBankModel;
import com.hz.gather.master.core.model.notice.NoticeModel;
import com.hz.gather.master.core.model.pay.PayCustModel;
import com.hz.gather.master.core.model.question.QuestionDModel;
import com.hz.gather.master.core.model.question.QuestionMModel;
import com.hz.gather.master.core.model.region.RegionModel;
import com.hz.gather.master.core.model.spread.SpreadNoticeModel;
import com.hz.gather.master.core.model.upgrade.UpgradeModel;
import com.hz.gather.master.core.protocol.request.RequestAlipay;
import com.hz.gather.master.core.protocol.request.itembank.ItemBankAnswer;
import com.hz.gather.master.core.protocol.request.itembank.RequestItemBank;
import com.hz.gather.master.core.protocol.request.spread.RequestSpreadNotice;
import com.hz.gather.master.core.protocol.request.upgrade.RequestUpgrade;
import com.hz.gather.master.core.protocol.response.alipay.ResponseAlipay;
import com.hz.gather.master.core.protocol.response.itembank.ItemBank;
import com.hz.gather.master.core.protocol.response.itembank.ResponseItemBank;
import com.hz.gather.master.core.protocol.response.notice.Notice;
import com.hz.gather.master.core.protocol.response.notice.ResponseNotice;
import com.hz.gather.master.core.protocol.response.question.QuestionD;
import com.hz.gather.master.core.protocol.response.question.QuestionM;
import com.hz.gather.master.core.protocol.response.question.ResponseQuestion;
import com.hz.gather.master.core.protocol.response.spread.ResponseSpreadNotice;
import com.hz.gather.master.core.protocol.response.spread.SpreadNotice;
import com.hz.gather.master.core.protocol.response.upgrade.ResponseUpgrade;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
     * @Description: 发送组装阿里支付宝支付订单时，校验基本数据是否非法
     * @param requestAlipay - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkAlipayData(RequestAlipay requestAlipay) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestAlipay == null ){
            throw new ServiceException(ErrorCode.ENUM_ERROR.A00001.geteCode(), ErrorCode.ENUM_ERROR.A00001.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestAlipay.getToken())){
            throw new ServiceException(ErrorCode.ENUM_ERROR.A00002.geteCode(), ErrorCode.ENUM_ERROR.A00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = HodgepodgeMethod.checkIsLogin(requestAlipay.getToken());
        return memberId;
    }
    
    
    /**
     * @Description: 已经支付成功的用户纪录的查询数据
     * @param memberId - 用户ID
     * @return 
     * @author yoko
     * @date 2020/1/19 21:08 
    */
    public static PayCustModel assemblePayCustQuery(long memberId){
        PayCustModel resBean = new PayCustModel();
        resBean.setMemberId(memberId);
        return resBean;
    }

    /**
     * @Description: check用户是否重复支付
     * @param payCustModel
     * @author yoko
     * @date 2020/1/19 21:22
    */
    public static void checkIsPayCust(PayCustModel payCustModel) throws Exception{
        if (payCustModel != null){
            throw new ServiceException(ErrorCode.ENUM_ERROR.A00004.geteCode(), ErrorCode.ENUM_ERROR.A00004.geteDesc());
        }
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
        resBean.memberId = requestAlipay.memberId;
        if (requestAlipay != null){
            if (!StringUtils.isBlank(requestAlipay.body)){
                resBean.body = requestAlipay.body;
            }else {
                resBean.body = "费用缴纳";
            }
            if (!StringUtils.isBlank(requestAlipay.subject)){
                resBean.subject = requestAlipay.subject;
            }else {
                resBean.subject = "500费用";
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
                resBean.productCode = "500_HY";
            }
        }
        return resBean;
    }

    /**
     * @Description: 组装阿里支付的订单访问数据-H5
     * @param requestAlipay - 阿里支付订单的基本信息
     * @param outTradeNo - 交易订单号
     * @param totalAmount - 订单总金额
     * @return AlipayModel
     * @author yoko
     * @date 2019/12/19 20:36
     */
    public static AlipayH5Model assembleH5AlipayData(RequestAlipay requestAlipay, String outTradeNo, String totalAmount){
        AlipayH5Model resBean = new AlipayH5Model();
        if (requestAlipay != null){
            if (!StringUtils.isBlank(requestAlipay.body)){
                resBean.body = requestAlipay.body;
            }else {
                resBean.body = "费用缴纳";
            }
            if (!StringUtils.isBlank(requestAlipay.subject)){
                resBean.subject = requestAlipay.subject;
            }else {
                resBean.subject = "500费用";
            }
            if (!StringUtils.isBlank(requestAlipay.outTradeNo)){
                resBean.out_trade_no = requestAlipay.outTradeNo;
            }else {
                resBean.out_trade_no = outTradeNo;
            }
            if (!StringUtils.isBlank(requestAlipay.timeoutExpress)){
                resBean.timeout_express = requestAlipay.timeoutExpress;
            }else {
                resBean.timeout_express = "30m";
            }
            if (!StringUtils.isBlank(requestAlipay.totalAmount)){
                resBean.total_amount = requestAlipay.totalAmount;
            }else {
                resBean.total_amount = totalAmount;
            }
            if (!StringUtils.isBlank(requestAlipay.productCode)){
                resBean.product_code = requestAlipay.productCode;
            }else {
                resBean.product_code = "500_HY";
            }
        }
        return resBean;
    }

    /**
     * @Description: 组装阿里支付请求的纪录数据
     * @param alipayModel - 阿里支付的数据
     * @param aliOrder - 阿里支付宝请求之后返回的订单串
     * @return
     * @author yoko
     * @date 2019/12/26 14:42
     */
    public static AlipayModel assembleAlipayModel(AlipayModel alipayModel, String aliOrder){
        AlipayModel resBean = new AlipayModel();
        resBean = alipayModel;
        if (!StringUtils.isBlank(aliOrder)){
            resBean.setAliOrder(aliOrder);
        }
        resBean.dataType = ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE;
        resBean.setCurday(DateUtil.getDayNumber(new Date()));
        resBean.setCurhour(DateUtil.getHour(new Date()));
        resBean.setCurminute(DateUtil.getCurminute(new Date()));
        return resBean;
    }

    /**
     * @Description: 组装阿里支付请求的纪录数据-H5
     * @param alipayH5Model - 阿里支付的数据-H5
     * @param aliOrder - 阿里支付宝请求之后返回的订单串
     * @return
     * @author yoko
     * @date 2019/12/26 14:42
     */
    public static AlipayModel assembleH5AlipayModel(AlipayH5Model alipayH5Model, RequestAlipay requestAlipay, String aliOrder){
        AlipayModel resBean = new AlipayModel();
        resBean.memberId = requestAlipay.memberId;
        resBean.body = alipayH5Model.body;
        resBean.subject = alipayH5Model.subject;
        resBean.outTradeNo = alipayH5Model.out_trade_no;
        resBean.timeoutExpress = alipayH5Model.timeout_express;
        resBean.totalAmount = alipayH5Model.total_amount;
        resBean.productCode = alipayH5Model.product_code;
        resBean.dataType = ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO;
        if (!StringUtils.isBlank(aliOrder)){
            resBean.setAliOrder(aliOrder);
        }
        resBean.setCurday(DateUtil.getDayNumber(new Date()));
        resBean.setCurhour(DateUtil.getHour(new Date()));
        resBean.setCurminute(DateUtil.getCurminute(new Date()));
        return resBean;
    }

    /**
     * @Description: 阿里支付宝订单生成的数据组装返回客户端的方法
     * @param stime - 服务器的时间
//     * @param token - 登录token
     * @param sign - 签名
     * @param aliOrder - 调用阿里支付返回的订单码
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleAlipayResult(long stime, String sign, String aliOrder){
        ResponseAlipay dataModel = new ResponseAlipay();
        dataModel.aliOrder = aliOrder;
        dataModel.setStime(stime);
//        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }

    /**
     * @Description: 组装阿里云支付宝的订单回调结果数据
     * @param map - 阿里云支付宝返回的订单结果数据
     * @return AlipayNotifyModel
     * @author yoko
     * @date 2019/12/27 15:54
     */
    public static AlipayNotifyModel assembleAlipayNotify(Map<String, String> map) throws Exception{
        if (map == null){
            return null;
        }
        AlipayNotifyModel resBean = new AlipayNotifyModel();
        if (map.containsKey("app_id")){
            if (!StringUtils.isBlank(map.get("app_id"))){
                resBean.appId = map.get("app_id");
            }
        }
        if (map.containsKey("auth_app_id")){
            if (!StringUtils.isBlank(map.get("auth_app_id"))){
                resBean.authAppId = map.get("auth_app_id");
            }
        }
        if (map.containsKey("body")){
            if (!StringUtils.isBlank(map.get("body"))){
                resBean.body = map.get("body");
            }
        }
        if (map.containsKey("buyer_id")){
            if (!StringUtils.isBlank(map.get("buyer_id"))){
                resBean.buyerId = map.get("buyer_id");
            }
        }
        if (map.containsKey("buyer_logon_id")){
            if (!StringUtils.isBlank(map.get("buyer_logon_id"))){
                resBean.buyerLogonId = map.get("buyer_logon_id");
            }
        }
        if (map.containsKey("buyer_pay_amount")){
            if (!StringUtils.isBlank(map.get("buyer_pay_amount"))){
                resBean.buyerPayAmount = map.get("buyer_pay_amount");
            }
        }
        if (map.containsKey("charset")){
            if (!StringUtils.isBlank(map.get("charset"))){
                resBean.dataCharset = map.get("charset");
            }
        }
        if (map.containsKey("fund_bill_list")){
            if (!StringUtils.isBlank(map.get("fund_bill_list"))){
                resBean.fundBillList = map.get("fund_bill_list");
            }
        }
        if (map.containsKey("gmt_create")){
            if (!StringUtils.isBlank(map.get("gmt_create"))){
                resBean.gmtCreate = map.get("gmt_create");
            }
        }
        if (map.containsKey("gmt_payment")){
            if (!StringUtils.isBlank(map.get("gmt_payment"))){
                resBean.gmtPayment = map.get("gmt_payment");
            }
        }
        if (map.containsKey("invoice_amount")){
            if (!StringUtils.isBlank(map.get("invoice_amount"))){
                resBean.invoiceAmount = map.get("invoice_amount");
            }
        }
        if (map.containsKey("notify_id")){
            if (!StringUtils.isBlank(map.get("notify_id"))){
                resBean.notifyId = map.get("notify_id");
            }
        }
        if (map.containsKey("notify_time")){
            if (!StringUtils.isBlank(map.get("notify_time"))){
                resBean.notifyTime = map.get("notify_time");
            }
        }
        if (map.containsKey("notify_type")){
            if (!StringUtils.isBlank(map.get("notify_type"))){
                resBean.notifyType = map.get("notify_type");
            }
        }
        if (map.containsKey("out_trade_no")){
            if (!StringUtils.isBlank(map.get("out_trade_no"))){
                resBean.outTradeNo = map.get("out_trade_no");
            }
        }
        if (map.containsKey("point_amount")){
            if (!StringUtils.isBlank(map.get("point_amount"))){
                resBean.pointAmount = map.get("point_amount");
            }
        }
        if (map.containsKey("receipt_amount")){
            if (!StringUtils.isBlank(map.get("receipt_amount"))){
                resBean.receiptAmount = map.get("receipt_amount");
            }
        }
        if (map.containsKey("seller_email")){
            if (!StringUtils.isBlank(map.get("seller_email"))){
                resBean.sellerEmail = map.get("seller_email");
            }
        }
        if (map.containsKey("seller_id")){
            if (!StringUtils.isBlank(map.get("seller_id"))){
                resBean.sellerId = map.get("seller_id");
            }
        }
        if (map.containsKey("subject")){
            if (!StringUtils.isBlank(map.get("subject"))){
                resBean.subject = map.get("subject");
            }
        }
        if (map.containsKey("total_amount")){
            if (!StringUtils.isBlank(map.get("total_amount"))){
                resBean.totalAmount = map.get("total_amount");
            }
        }
        if (map.containsKey("trade_no")){
            if (!StringUtils.isBlank(map.get("trade_no"))){
                resBean.tradeNo = map.get("trade_no");
            }
        }
        if (map.containsKey("trade_status")){
            if (!StringUtils.isBlank(map.get("trade_status"))){
                resBean.tradeStatus = map.get("trade_status");
            }
        }
        if (map.containsKey("version")){
            if (!StringUtils.isBlank(map.get("version"))){
                resBean.dataVersion = map.get("version");
            }
        }
        resBean.setCurday(DateUtil.getDayNumber(new Date()));
        resBean.setCurhour(DateUtil.getHour(new Date()));
        resBean.setCurminute(DateUtil.getCurminute(new Date()));
        return resBean;
    }


    /**
     * @Description: 百问百答类别集合的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param questionMList - 百问百答类别集合
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleQuestionMResult(long stime, String sign, List<QuestionMModel> questionMList, Integer rowCount){
        ResponseQuestion dataModel = new ResponseQuestion();
        if (questionMList != null && questionMList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<QuestionM> dataList = BeanUtils.copyList(questionMList, QuestionM.class);
            dataModel.qMList = dataList;
        }
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }

    /**
     * @Description: 百问百答-详情集合的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param questionDList - 百问百答详情集合
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleQuestionDResult(long stime, String sign, List<QuestionDModel> questionDList, Integer rowCount){
        ResponseQuestion dataModel = new ResponseQuestion();
        if (questionDList != null && questionDList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<QuestionD> dataList = BeanUtils.copyList(questionDList, QuestionD.class);
            dataModel.qDList = dataList;
        }
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 百问百答-详情的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param questionDModel - 百问百答详情
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleQuestionDDataResult(long stime, String sign, QuestionDModel questionDModel, Integer rowCount){
        ResponseQuestion dataModel = new ResponseQuestion();
        if (questionDModel != null){
            QuestionD data = BeanUtils.copy(questionDModel, QuestionD.class);
            dataModel.qD = data;
        }
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 客户端升级的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param upgradeModel - 客户端更新要更新的数据
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleUpgradeDataResult(long stime, String sign, UpgradeModel upgradeModel){
        ResponseUpgrade dataModel = new ResponseUpgrade();
        if (upgradeModel != null){
            dataModel = BeanUtils.copy(upgradeModel, ResponseUpgrade.class);
        }

        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 公告集合的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param noticeList - 公告集合
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleNoticeResult(long stime, String sign, List<NoticeModel> noticeList, Integer rowCount){
        ResponseNotice dataModel = new ResponseNotice();
        if (noticeList != null && noticeList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<Notice> dataList = BeanUtils.copyList(noticeList, Notice.class);
            dataModel.ncList = dataList;
        }
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 密保集合的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param itemBankList - 密保集合
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleItemBankResult(long stime, String sign, List<ItemBankModel> itemBankList, Integer rowCount){
        ResponseItemBank dataModel = new ResponseItemBank();
        if (itemBankList != null && itemBankList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<ItemBank> dataList = BeanUtils.copyList(itemBankList, ItemBank.class);
            dataModel.bkList = dataList;
        }
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 查询获取用户的密保时，校验基本数据是否非法
     * @param requestModel - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkCustomerData(RequestItemBank requestModel) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestModel == null ){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00001.geteCode(), ErrorCode.ENUM_ERROR.I00001.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestModel.getToken())){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00002.geteCode(), ErrorCode.ENUM_ERROR.I00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = HodgepodgeMethod.checkIsLogin(requestModel.getToken());
        return memberId;
    }

    /**
     * @Description: 校验用户是否处于登录状态
     * @param token - 登录token
     * @return Long
     * @author yoko
     * @date 2019/11/21 18:01
     */
    public static long checkIsLogin(String token) throws Exception{
        Long memberId;
//        String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, token);
        String strCache = (String) ComponentUtil.redisService.get(token);
        if (!StringUtils.isBlank(strCache)) {
            // 登录存储在缓存中的用户id
            memberId = Long.parseLong(strCache);
        }else {
            throw new ServiceException(ErrorCode.ENUM_ERROR.C00000.geteCode(), ErrorCode.ENUM_ERROR.C00000.geteDesc());
        }
        return memberId;
    }


    /**
     * @Description: 查询添加用户的密保时，校验基本数据是否非法
     * @param requestModel - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkAddAnswerData(RequestItemBank requestModel) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestModel == null ){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00003.geteCode(), ErrorCode.ENUM_ERROR.I00003.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestModel.getToken())){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00004.geteCode(), ErrorCode.ENUM_ERROR.I00004.geteDesc());
        }

        // 校验密保以及答案
        if (requestModel.answerList == null){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00005.geteCode(), ErrorCode.ENUM_ERROR.I00005.geteDesc());
        }

        // 校验用户是否登录
        memberId = HodgepodgeMethod.checkIsLogin(requestModel.getToken());
        return memberId;
    }

    /**
     * @Description: 组装用户提交的密保以及答案
     * @param itemBankAnswerList - 用户密保以及答案
     * @param memberId - 用户ID
     * @return List
     * @author yoko
     * @date 2020/1/15 16:52
    */
    public static List<ItemBankAnswerModel> assembleItemBankAnswerList(List<ItemBankAnswer> itemBankAnswerList, long memberId){
        List<ItemBankAnswerModel> resList = new ArrayList<>();
        for (ItemBankAnswer dataModel : itemBankAnswerList){
            if (dataModel.itemBankId != null && !StringUtils.isBlank(dataModel.answer)){
                ItemBankAnswerModel data = new ItemBankAnswerModel();
                data.setMemberId(memberId);
                data.setItemBankId(dataModel.itemBankId);
                data.setAnswer(dataModel.answer);
                resList.add(data);
            }
        }
        return resList;
    }

    /**
     * @Description: 校验用户提交的密保以及答案数据
     * @param itemBankAnswerList - 密保、答案
     * @author yoko
     * @date 2020/1/15 16:59
    */
    public static void checkItemBankAnswerData(List<ItemBankAnswerModel> itemBankAnswerList) throws Exception{
        // 校验数据是否为空
        if (itemBankAnswerList == null || itemBankAnswerList.size() == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00003.geteCode(), ErrorCode.ENUM_ERROR.I00003.geteDesc());
        }
    }


    /**
     * @Description: 添加用户密保数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleAddItemBankResult(long stime, String sign){
        ResponseItemBank dataModel = new ResponseItemBank();
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 查询用户校验密保时，校验基本数据是否非法
     * @param requestModel - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkAnswerData(RequestItemBank requestModel) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestModel == null ){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00007.geteCode(), ErrorCode.ENUM_ERROR.I00007.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestModel.getToken())){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00008.geteCode(), ErrorCode.ENUM_ERROR.I00008.geteDesc());
        }

        // 校验密保以及答案
        if (requestModel.answerList == null){
            throw new ServiceException(ErrorCode.ENUM_ERROR.I00009.geteCode(), ErrorCode.ENUM_ERROR.I00009.geteDesc());
        }

        // 校验用户是否登录
        memberId = HodgepodgeMethod.checkIsLogin(requestModel.getToken());
        return memberId;
    }

    /**
     * @Description: 组装更新用户是否开启密保的数据
     * @param memberId - 用户ID
     * @return
     * @author yoko
     * @date 2020/1/16 14:21
    */
    public static VcMember assembleVcMember(Long memberId){
        VcMember resBean = new VcMember();
        resBean.setMemberId(memberId.intValue());
        resBean.setIsQuestions(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        return resBean;
    }


    /**
     * @Description: 系统通知，系统公告，传播、扩散的通知集合的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param spreadNoticeList - 密保集合
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleSpreadNoticeResult(long stime, String sign, List<SpreadNoticeModel> spreadNoticeList, Integer rowCount){
        ResponseSpreadNotice dataModel = new ResponseSpreadNotice();
        if (spreadNoticeList != null && spreadNoticeList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<SpreadNotice> dataList = BeanUtils.copyList(spreadNoticeList, SpreadNotice.class);
            dataModel.sdList = dataList;
        }
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 获取推广通知详情时，校验基本数据是否非法
     * @param requestModel - 基础数据
     * @return void
     * @author yoko
     * @date 2020/01/16 23:20
     */
    public static void checkSpreadNoticeData(RequestSpreadNotice requestModel) throws Exception{
        // 校验所有数据
        if (requestModel == null ){
            throw new ServiceException(ErrorCode.ENUM_ERROR.H00001.geteCode(), ErrorCode.ENUM_ERROR.H00001.geteDesc());
        }

        // 校验ID值
        if (requestModel.id == null ){
            throw new ServiceException(ErrorCode.ENUM_ERROR.H00002.geteCode(), ErrorCode.ENUM_ERROR.H00002.geteDesc());
        }

    }


    /**
     * @Description: 系统通知，系统公告，传播、扩散的通知-详情的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param sign - 签名
     * @param spreadNoticeModel - 系统通知，系统公告，传播、扩散的通知的详情
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleSpreadNoticeDataResult(long stime, String sign, SpreadNoticeModel spreadNoticeModel){
        ResponseSpreadNotice dataModel = new ResponseSpreadNotice();
        if (spreadNoticeModel != null){
            SpreadNotice data = BeanUtils.copy(spreadNoticeModel, SpreadNotice.class);
            dataModel.sd = data;
        }
        dataModel.setStime(stime);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 校验用户是否频繁进行支付操作
     * @param memberId - 用户ID
     * @return Long
     * @author yoko
     * @date 2019/11/21 18:01
     */
    public static void checkAliPayMember(long memberId) throws Exception{
        String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.ALIPAY_MEMBER, memberId);
        String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
        if (!StringUtils.isBlank(strCache)) {
            // 用户属于频繁调起
            throw new ServiceException(ErrorCode.ENUM_ERROR.A00003.geteCode(), ErrorCode.ENUM_ERROR.A00003.geteDesc());
        }
    }


    /**
     * @Description: 存储用户操作阿里支付到缓存中：存10秒
     * <p>以便check用户是否频繁操作</p>
     * @param memberId - 用户ID
     * @return Long
     * @author yoko
     * @date 2019/11/21 18:01
     */
    public static void setAliPayMember(long memberId) throws Exception{
        String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.ALIPAY_MEMBER, memberId);
        ComponentUtil.redisService.set(strKeyCache, String.valueOf(memberId), 10, TimeUnit.SECONDS);
    }

}
