package com.hz.gather.master.util;

import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.DateUtil;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.common.utils.constant.TkErrorCode;
import com.hz.gather.master.core.model.alipay.AlipayData;
import com.hz.gather.master.core.model.alipay.AlipayTransferModel;
import com.hz.gather.master.core.model.alipay.PayeeInfo;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import com.hz.gather.master.core.model.entity.UCashOutProcedLog;
import com.hz.gather.master.core.model.pay.PayCustModel;
import com.hz.gather.master.core.model.task.base.StatusModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Description 定时任务的公共类
 * @Author yoko
 * @Date 2020/1/11 16:20
 * @Version 1.0
 */
public class TaskMethod {
    private static Logger log = LoggerFactory.getLogger(TaskMethod.class);


    /**
     * @Description: 组装查询定时任务阿里支付宝转账的查询条件
     * @param limitNum - 多少条数据
     * @return
     * @author yoko
     * @date 2020/1/11 16:23
    */
    public static StatusModel assembleTaskByAliapyTransferStatusQuery(int limitNum){
        StatusModel resBean = new StatusModel();
        resBean.setRunNum(ServerConstant.PUBLIC_CONSTANT.RUN_NUM_FIVE);
        resBean.setRunStatus(ServerConstant.PUBLIC_CONSTANT.RUN_STATUS_THREE);
        resBean.setLimitNum(limitNum);
        return resBean;
    }

    /**
     * @Description: 组装阿里支付宝转账所需的数据
     * @param uCashOutLogModel - 要转账的数据
     * @return AlipayTransferModel
     * @author yoko
     * @date 2020/1/11 17:37
    */
    public static AlipayTransferModel assembleAlipayTransfer(UCashOutLog uCashOutLogModel) throws Exception{
        AlipayTransferModel resBean = new AlipayTransferModel();
        if (!StringUtils.isBlank(uCashOutLogModel.getOutTradeNo())){
            resBean.out_biz_no = uCashOutLogModel.getOutTradeNo();
        }else {
            throw new ServiceException(TkErrorCode.ENUM_ERROR.T000001.geteCode(), TkErrorCode.ENUM_ERROR.T000001.geteDesc());
        }
        resBean.trans_amount = String.valueOf(uCashOutLogModel.getMoney());
        resBean.product_code = "TRANS_ACCOUNT_NO_PWD";
        resBean.biz_scene = "DIRECT_TRANSFER";
        PayeeInfo payee_info = new PayeeInfo();
        if (!StringUtils.isBlank(uCashOutLogModel.getReceivaPayId())){
            payee_info.identity = uCashOutLogModel.getReceivaPayId();
        }else {
            throw new ServiceException(TkErrorCode.ENUM_ERROR.T000002.geteCode(), TkErrorCode.ENUM_ERROR.T000002.geteDesc());
        }
        payee_info.identity_type = "ALIPAY_LOGON_ID";
        if (!StringUtils.isBlank(uCashOutLogModel.getZfbName())){
            payee_info.name = uCashOutLogModel.getZfbName();
        }else {
            throw new ServiceException(TkErrorCode.ENUM_ERROR.T000003.geteCode(), TkErrorCode.ENUM_ERROR.T000003.geteDesc());
        }
        resBean.payee_info = payee_info;
        if (!StringUtils.isBlank(uCashOutLogModel.getRemarks())){
            resBean.order_title = uCashOutLogModel.getRemarks();
        }
        return resBean;
    }


    /**
     * @Description: 组装更改运行状态的数据
     * @param id - 主键ID
     * @param runStatus - 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     * @return StatusModel
     * @author yoko
     * @date 2019/12/10 10:42
     */
    public static StatusModel assembleUpdateStatusModel(long id, int runStatus){
        StatusModel resBean = new StatusModel();
        resBean.setId(id);
        resBean.setRunStatus(runStatus);
        if (runStatus == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO){
            // 表示失败：失败则需要运行次数加一
            resBean.setRunNum(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        }
        return resBean;
    }

    /**
     * @Description: 组装转账请求&响应的阿里数据
     * @param alipayTransferModel - 请求的阿里支付宝数据
     * @param alipayFundTransUniTransferResponse - 阿里支付宝响应的数据
     * @param uCashOutLog - task任务数据
     * @return AlipayData
     * @author yoko
     * @date 2020/1/13 14:07
    */
    public static AlipayData assembleAlipayData(AlipayTransferModel alipayTransferModel, AlipayFundTransUniTransferResponse alipayFundTransUniTransferResponse, UCashOutLog uCashOutLog){
        AlipayData resBean = new AlipayData();
        if (alipayTransferModel != null){
            resBean = BeanUtils.copy(alipayTransferModel, AlipayData.class);
        }
        resBean.memberId = Long.parseLong(String.valueOf(uCashOutLog.getMemberId()));
        resBean.identity = alipayTransferModel.payee_info.identity;
        resBean.identity_type = alipayTransferModel.payee_info.identity_type;
        resBean.name = alipayTransferModel.payee_info.name;
        if (alipayFundTransUniTransferResponse != null){
            if (!StringUtils.isBlank(alipayFundTransUniTransferResponse.getOrderId())){
                resBean.order_id = alipayFundTransUniTransferResponse.getOrderId();
            }
            if (!StringUtils.isBlank(alipayFundTransUniTransferResponse.getPayFundOrderId())){
                resBean.pay_fund_order_id = alipayFundTransUniTransferResponse.getPayFundOrderId();
            }
            if (!StringUtils.isBlank(alipayFundTransUniTransferResponse.getStatus())){
                resBean.status = alipayFundTransUniTransferResponse.getStatus();
            }
            if (!StringUtils.isBlank(alipayFundTransUniTransferResponse.getTransDate())){
                resBean.trans_date = alipayFundTransUniTransferResponse.getTransDate();
            }
            if (!StringUtils.isBlank(alipayFundTransUniTransferResponse.getCode())){
                resBean.code = alipayFundTransUniTransferResponse.getCode();
            }
            if (!StringUtils.isBlank(alipayFundTransUniTransferResponse.getSubMsg())){
                resBean.subMsg = alipayFundTransUniTransferResponse.getSubMsg();
            }
        }
        return resBean;
    }

    /**
     * @Description: 组装查询支付宝订单同步的数据的查询条件
     * @param limitNum
     * @return StatusModel
     * @author yoko
     * @date 2019/12/6 22:48
     */
    public static StatusModel assembleTaskAlipayNotifyStatusQuery(int limitNum){
        StatusModel resBean = new StatusModel();
        resBean.setRunStatus(ServerConstant.PUBLIC_CONSTANT.RUN_STATUS_THREE);
        resBean.setRunNum(ServerConstant.PUBLIC_CONSTANT.RUN_NUM_FIVE);
        resBean.setLimitNum(limitNum);
        return resBean;
    }


    /**
     * @Description: 组装添加已经支付成功的用户纪录的数据
     * @param memberId - 用户ID
     * @return PayCustModel
     * @author yoko
     * @date 2020/1/19 21:30
    */
    public static PayCustModel assemblePayCust(long memberId){
        PayCustModel resBean = new PayCustModel();
        resBean.setMemberId(memberId);
        resBean.setCurday(DateUtil.getDayNumber(new Date()));
        resBean.setCurhour(DateUtil.getHour(new Date()));
        resBean.setCurminute(DateUtil.getCurminute(new Date()));
        return resBean;
    }


    /**
     * @Description: 组装查询定时任务阿里支付宝转账失败、成功的查询条件
     * @param limitNum - 多少条数据
     * @return
     * @author yoko
     * @date 2020/1/11 16:23
     */
    public static StatusModel assembleTaskByCashOutHandleStatusQuery(int limitNum){
        StatusModel resBean = new StatusModel();
        resBean.setHandleNum(ServerConstant.PUBLIC_CONSTANT.RUN_NUM_FIVE);
        resBean.setHandleStatus(ServerConstant.PUBLIC_CONSTANT.RUN_STATUS_THREE);
        resBean.setLimitNum(limitNum);
        return resBean;
    }

    /**
     * @Description: 组装更改运行状态的数据
     * @param id - 主键ID
     * @param runStatus - 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     * @return StatusModel
     * @author yoko
     * @date 2019/12/10 10:42
     */
    public static StatusModel assembleUpdateResultStatusModel(long id, int runStatus){
        StatusModel resBean = new StatusModel();
        resBean.setId(id);
        resBean.setHandleStatus(runStatus);
        if (runStatus == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO){
            // 表示失败：失败则需要运行次数加一
            resBean.setHandleNum(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        }
        return resBean;
    }

    /**
     * @Description: 组装更新用户提现结果的更新数据
     * @param memberId - 用户ID
     * @param outTradeNo - 订单号
     * @param isOk - 用户提现/系统转账是否成功：0初始化，1成功，2失败
     * @return com.hz.gather.master.core.model.entity.UCashOutProcedLog
     * @author yoko
     * @date 2020/1/21 20:56
     */
    public static UCashOutProcedLog assembleUCashOutProcedLog(Integer memberId, String outTradeNo, Integer isOk){
        UCashOutProcedLog resBean = new UCashOutProcedLog();
        resBean.setMemberId(memberId);
        resBean.setOutTradeNo(outTradeNo);
        resBean.setIsOk(isOk);
        return resBean;
    }
}
