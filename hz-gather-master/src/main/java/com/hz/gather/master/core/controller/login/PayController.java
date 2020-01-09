package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.entity.VcMemberPay;
import com.hz.gather.master.core.model.user.CommonModel;
import com.hz.gather.master.core.protocol.request.pay.RequestAddZFBPay;
import com.hz.gather.master.core.protocol.response.pay.ResponeseAddZFBPay;
import com.hz.gather.master.core.protocol.response.pay.ResponeseHavaPayInfo;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @Description: 支付宝是否有效
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad"}
     * @date 2020/1/9 20:49
     */
    @PostMapping("/havaPay")
    public JsonResult<Object> havaPay(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        log.info("----------:havaPay 进来啦!");
        try{
            data        = StringUtil.decoderBase64(jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean  flag  =   PublicMethod.isCommonModel(commonModel);
            if(flag){
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
            return JsonResult.successResult(encryptionData);
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
    public JsonResult<Object> havaPayinfo(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        log.info("----------:havaPay 进来啦!");
        try{
            data        = StringUtil.decoderBase64(jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean  flag  =   PublicMethod.isCommonModel(commonModel);
            if(flag){
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
            return JsonResult.successResult(encryptionData);
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
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad","zfbPayId":"52342162@qq.com"}
     * @date 2020/1/9 22:36
     */
    @PostMapping("/addZFBPay")
    public JsonResult<Object> addZFBPay(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        RequestAddZFBPay requestAddZFBPay = new RequestAddZFBPay();
        log.info("----------:addZFBPay 进来啦!");
        try{

            data        = StringUtil.decoderBase64(jsonData);
            requestAddZFBPay  = JSON.parseObject(data, RequestAddZFBPay.class);
            boolean  flag  =   PublicMethod.isCommonModel(requestAddZFBPay);
            if(flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(requestAddZFBPay.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            //该用户是否超出支付宝最大值
            flag = ComponentUtil.payService.isAddPayZFB(memberId);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00017.geteCode(),ENUM_ERROR.A00017.geteDesc());
            }
            Integer  count  = ComponentUtil.payService.addPayZFB(memberId,requestAddZFBPay.getZfbPayId());

            ResponeseAddZFBPay responeseAddZFBPay =PublicMethod.updateRs(count);
            data = PublicMethod.toJson(responeseAddZFBPay);
            String encryptionData = StringUtil.mergeCodeBase64(data);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            return JsonResult.successResult(encryptionData);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }



}
