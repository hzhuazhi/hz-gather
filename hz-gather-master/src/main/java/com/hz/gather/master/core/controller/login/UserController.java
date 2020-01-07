package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import com.hz.gather.master.core.model.user.CommonModel;
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
 * @Date 2020/1/7 16:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/queryUserInfo")
    public JsonResult<Object> getRegisterSms(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        String time ="";
        try{
            data        = StringUtil.decoderBase64(jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);

            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(flag){
                throw  new ServiceException(ENUM_ERROR.SERVER_OK.geteCode(),ENUM_ERROR.SERVER_OK.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            VcMember  vcMember =ComponentUtil.userInfoService.queryMemberInfo(memberId);
            VcMemberResource vcMemberResource =ComponentUtil.userInfoService.queryMemberResourceInfo(memberId);

            if(vcMember==null||vcMemberResource==null){
                throw  new ServiceException(ENUM_ERROR.A00016.geteCode(),ENUM_ERROR.A00016.geteDesc());
            }
            String encryptionData = StringUtil.mergeCodeBase64(PublicMethod.toSendSmsDto(time));
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
