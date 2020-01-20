package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.entity.VcMemberPay;
import com.hz.gather.master.core.model.user.CommonModel;
import com.hz.gather.master.core.protocol.request.pay.RequestAddZFBPay;
import com.hz.gather.master.core.protocol.request.pay.RequestPayCashOut;
import com.hz.gather.master.core.protocol.request.pay.RequestUpdateZFBPay;
import com.hz.gather.master.core.protocol.response.pay.ResponeseAddZFBPay;
import com.hz.gather.master.core.protocol.response.pay.ResponeseHavaPayInfo;
import com.hz.gather.master.core.protocol.response.pay.ResponesePayCashOut;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 21:49
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/pay")
public class PayController {
    private static Logger log = LoggerFactory.getLogger(PayController.class);
    /**
     * @Description: 是否设置了支付宝
     * @param request
     * @param response
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * local:   http://localhost:8082/mg/pay/havaPay
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad"}
     * @date 2020/1/9 20:49
     */
    @PostMapping("/havaPay")
    public JsonResult<Object> havaPay(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
//    public JsonResult<Object> havaPay(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        log.info("----------:havaPay 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean  flag  =   PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            ResponeseHavaPay responeseHavaPay =  ComponentUtil.payService.getHavaPay(memberId);
            data = PublicMethod.toJson(responeseHavaPay);
            String encryptionData = StringUtil.mergeCodeBase64(data);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * @Description: 支付宝列表查询
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad"}
     * @date 2020/1/9 20:49
     */
    @PostMapping("/havaPayInfo")
    public JsonResult<Object> havaPayinfo(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    //public JsonResult<Object> havaPayinfo(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        log.info("----------:havaPay 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean  flag  =   PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            Integer   memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            List<VcMemberPay> list =  ComponentUtil.payService.queryPayZFBList(memberId);
            ResponeseHavaPayInfo responeseHavaPayInfo =PublicMethod.toQueryHavaPayInfo(list);

            data = PublicMethod.toJson(responeseHavaPayInfo);
            String encryptionData = StringUtil.mergeCodeBase64(data);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 添加支付接口信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad","zfbPayId":"52342162@qq.com","zfbName":"留下"}
     * @date 2020/1/9 22:36
     */
    @PostMapping("/addZFBPay")
    public JsonResult<Object> addZFBPay(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    //public JsonResult<Object> addZFBPay(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        RequestAddZFBPay requestAddZFBPay = new RequestAddZFBPay();
        log.info("----------:addZFBPay 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            requestAddZFBPay  = JSON.parseObject(data, RequestAddZFBPay.class);
            boolean  flag  =   PublicMethod.isCheckPayAdd(requestAddZFBPay);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(requestAddZFBPay.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            //该用户是否超出支付宝最大值
            flag = ComponentUtil.payService.isAddPayZFB(memberId);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.P00001.geteCode(),ENUM_ERROR.P00001.geteDesc());
            }
            Integer  count  = ComponentUtil.payService.addPayZFB(memberId,requestAddZFBPay.getZfbPayId(),requestAddZFBPay.getZfbName());

            ResponeseAddZFBPay responeseAddZFBPay =PublicMethod.updateRs(count);
            data = PublicMethod.toJson(responeseAddZFBPay);
            String encryptionData = StringUtil.mergeCodeBase64(data);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }



    /**
     * @Description: 修改支付密码
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad","zfbPayId":"52342162@qq.com","zfbName":"留下"}
     * @date 2020/1/9 22:36
     */
    @PostMapping("/updateZFBPay")
    public JsonResult<Object> updateZFBPay(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        //public JsonResult<Object> addZFBPay(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        RequestUpdateZFBPay requestUpdateZFBPay = new RequestUpdateZFBPay();
        log.info("----------:addZFBPay 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            requestUpdateZFBPay  = JSON.parseObject(data, RequestUpdateZFBPay.class);
            boolean  flag  =   PublicMethod.isCheckPayUpdate(requestUpdateZFBPay);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(requestUpdateZFBPay.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            //该用户是否超出支付宝最大值
            long  id = ComponentUtil.payService.isOldpayId(requestUpdateZFBPay.getOldZfbPayId(),memberId);
            if(id==0){
                throw  new ServiceException(ENUM_ERROR.P00007.geteCode(),ENUM_ERROR.P00007.geteDesc());
            }
            Integer  count  = ComponentUtil.payService.updatyPayId(id,requestUpdateZFBPay.getZfbPayId(),requestUpdateZFBPay.getZfbName());

            ResponeseAddZFBPay responeseAddZFBPay =PublicMethod.updateRs(count);
            data = PublicMethod.toJson(responeseAddZFBPay);
            String encryptionData = StringUtil.mergeCodeBase64(data);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * @Description: 添加支付接口信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad","zfbPayId":"52342162@qq.com","zfbName":"留下"}
     * @date 2020/1/9 22:36
     */
    @PostMapping("/payCashOut")
    public JsonResult<Object> payCashOut(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    //public JsonResult<Object> payCashOut(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        RequestPayCashOut requestPayCashOut = new RequestPayCashOut();
        log.info("----------:payCashOut 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            requestPayCashOut  = JSON.parseObject(data, RequestPayCashOut.class);
            boolean  flag  =  PublicMethod.isCheckCashOut(requestPayCashOut);
             if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            flag = PublicMethod.cheakMoney(requestPayCashOut.getMoney());
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.P00005.geteCode(),ENUM_ERROR.P00005.geteDesc());
            }




            Integer   memberId = PublicMethod.tokenGetMemberId(requestPayCashOut.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer  caseCount =ComponentUtil.userInfoService.isCountMemberId(memberId);
            if(caseCount>=Constant.USERCASHMAX){
                throw  new ServiceException(ENUM_ERROR.P00011.geteCode(),ENUM_ERROR.P00011.geteDesc());
            }

            flag = ComponentUtil.userInfoService.queryPayPassword(memberId,requestPayCashOut.getPayPassword());
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.P00010.geteCode(),ENUM_ERROR.P00010.geteDesc());
            }

            //该用户是否超出支付宝最大值
//            flag = ComponentUtil.payService.isAddPayZFB(memberId);
//            if(!flag){
//                throw  new ServiceException(ENUM_ERROR.P00001.geteCode(),ENUM_ERROR.P00001.geteDesc());
//            }

            List<VcMemberPay>  list =ComponentUtil.payService.checkMemberIdToAliPayId(memberId,requestPayCashOut.getZfbPayId());
            if(list.size()==0){
                throw  new ServiceException(ENUM_ERROR.P00002.geteCode(),ENUM_ERROR.P00002.geteDesc());
            }

            ComponentUtil.payService.addUCashOutLog(memberId,list.get(0).getZfbPayid(),list.get(0).getZfbName(),sgid,requestPayCashOut.getMoney());

            ResponesePayCashOut  responesePayCashOut = PublicMethod.toResponesePayCashOut(true);

//            Integer  count  = ComponentUtil.payService.addPayZFB(memberId,requestAddZFBPay.getZfbPayId());

//            ResponeseAddZFBPay responeseAddZFBPay =PublicMethod.updateRs(count);
            data = PublicMethod.toJson(responesePayCashOut);
            String encryptionData = StringUtil.mergeCodeBase64(data);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }



}
