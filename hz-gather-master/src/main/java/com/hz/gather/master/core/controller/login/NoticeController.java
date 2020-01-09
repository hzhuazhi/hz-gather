package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.protocol.request.login.SendSmsModel;
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
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 11:51
 * @Version 1.0
 */

@RestController
@RequestMapping("/mg/notice")
public class NoticeController {
    private static Logger log = LoggerFactory.getLogger(NoticeController.class);

    @PostMapping("/register_sms")
    public JsonResult<Object> getRegisterSms(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        SendSmsModel sendSmsModel = new SendSmsModel();
        try{
            data        = StringUtil.decoderBase64(jsonData);
            sendSmsModel  = JSON.parseObject(data, SendSmsModel.class);

            boolean  flag = PublicMethod.checkPhoneIsType(sendSmsModel);
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }



            //还缺一个发送
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(data);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            log.info("----------:register_sms 进来啦!");
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }
}
