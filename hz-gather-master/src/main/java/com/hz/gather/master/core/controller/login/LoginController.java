package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.common.utils.constant.ErrorCode;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.dao.VcMember;
import com.hz.gather.master.core.model.login.*;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.apache.commons.lang.StringUtils;
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
 * @Date 2020/1/2 15:47
 * @Version 1.0
 */

@RestController
@RequestMapping("/mg/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * @Description: 注册获取手机信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "smsType": 1: "13606768872","country":"中国","areaCode": "086","version": "1.0.1" }
     * 字段格式 { "smsType": 2: "13606768872","version": "1.0.1" }
     * @date 2020/1/2 21:02
     */
    @PostMapping("/register_sms")
    public JsonResult<Object> getRegisterSms(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData)throws Exception{
        String data = "";
        SendSmsModel sendSmsModel = new SendSmsModel();
        try{
            data        = StringUtil.decoderBase64(jsonData);
            sendSmsModel  = JSON.parseObject(data, SendSmsModel.class);

            boolean  flag =PublicMethod.checkPhoneIsType(sendSmsModel);
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }

            flag = ComponentUtil.loginService.isPhoneExist(sendSmsModel.getPhone());
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00001.geteCode(),ENUM_ERROR.A00001.geteDesc());
            }
            String   time  = ComponentUtil.loginService.createTime(sendSmsModel.getPhone());
            //还缺一个发送
            // 数据加密
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



    /**
     * @Description: 用户注册接口，用户提交手机号码，以及详细信息进行提交
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * 字段格式: { "phone": "13606768872","smsCode": "中国","timeStamp": "1578053576","passWrod": "111111","inviteCode": "382032","version": "1.0.1" }
     * @author long
     * @date 2020/1/2 20:59
     */
    @PostMapping("/register_info")
    public JsonResult<Object> getRegisterInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData)throws Exception{
        String data = "";
        LoginModel loginModel = new LoginModel();
        try{
            log.info("----------:register_info 进来啦!");
            data        = StringUtil.decoderBase64(jsonData);
            loginModel  = JSON.parseObject(data, LoginModel.class);
            boolean   flag  = PublicMethod.isCheakRegister(loginModel);

            //参数是否正确
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.SERVER_OK.geteCode(),ENUM_ERROR.SERVER_OK.geteDesc());
            }

            //验证码是否正确
            flag = ComponentUtil.loginService.checkVerifCode(loginModel.getTimeStamp(),loginModel.getPhone(),loginModel.getSmsCode());
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
            }
            //用户邀请码是否正确
            flag  =  ComponentUtil.loginService.checkInviteCode(loginModel.getInviteCode());
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00002.geteCode(),ENUM_ERROR.A00002.geteDesc());
            }



            //用户手机号码是否被注册
            flag = ComponentUtil.loginService.isPhoneExist(loginModel.getPhone());
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00001.geteCode(),ENUM_ERROR.A00001.geteDesc());
            }
            String  token  = ComponentUtil.loginService.addMemberInfo(loginModel);
            data  = PublicMethod.toLoginModelDto(token);

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
     * @Description: 忘记密码接口,修改密码接口
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2020/1/3 21:57
     */
    @PostMapping("/forget_password")
    public JsonResult<Object> getForgetPassword(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData)throws Exception{
        String data = "";
        ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel();
        String   dtoken ="";
        try{
            data        = StringUtil.decoderBase64(jsonData);
            forgetPasswordModel  = JSON.parseObject(data, ForgetPasswordModel.class);

            boolean    flag =  PublicMethod.checkPwToken(forgetPasswordModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenToMemberId(forgetPasswordModel.getPwToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.A00014.geteCode(),ENUM_ERROR.A00014.geteDesc());
            }

            flag = PublicMethod.pwIsOK(forgetPasswordModel.getPassWord(),forgetPasswordModel.getPassWordConfirm());
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00005.geteCode(),ENUM_ERROR.A00005.geteDesc());
            }

            VcMember  vcMember = ComponentUtil.loginService.queryVcMember(memberId);
            if(vcMember==null){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }

            if(!StringUtils.isBlank(vcMember.getToken())){
                dtoken=vcMember.getToken();
            }
            ComponentUtil.loginService.removeToken(dtoken);//删除token
            Integer  count = ComponentUtil.loginService.updatePassWord(memberId,forgetPasswordModel.getPassWord(),"");
//            boolean  flag =  ComponentUtil.loginService.isPhoneExist(forgetPasswordModel.getPhone());
            if(count==0){
                throw  new ServiceException(ENUM_ERROR.A00015.geteCode(),ENUM_ERROR.A00015.geteDesc());
            }

            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 忘记密码接口
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2020/1/3 21:57
     */
    @PostMapping("/forget_phone")
    public JsonResult<Object> getForgetPhone(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData)throws Exception{
        log.info("----------:进来啦!");
        String data = "";
        ForgetPhoneModel forgetPhoneModel = new ForgetPhoneModel();
        try{
            data        = StringUtil.decoderBase64(jsonData);
            forgetPhoneModel  = JSON.parseObject(data, ForgetPhoneModel.class);

            boolean  flag = PublicMethod.checkPhoneVerificaCode(forgetPhoneModel);
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }

            flag = ComponentUtil.loginService.checkVerifCode(forgetPhoneModel.getTimeStamp(),forgetPhoneModel.getPhone(),forgetPhoneModel.getVerificationCode());
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
            }
            Integer  memberId = ComponentUtil.loginService.getMemberId(forgetPhoneModel.getPhone());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }



            String  token = ComponentUtil.generateService.getNonexistentInformation(Constant.PW_TOKEN);
            //
            ComponentUtil.loginService.toPwTokenRedis(token,memberId);
            String  pwToken =PublicMethod.toForgetPhoneDto(token);

            return JsonResult.successResult(pwToken);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    @PostMapping("/sign_in")
    public JsonResult<Object> getSignIn(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData)throws Exception{
        log.info("----------:进来啦!");
        String data = "";
        SignInModel signInModel = new SignInModel();
        try{
            data        = StringUtil.decoderBase64(jsonData);
            signInModel  = JSON.parseObject(data, SignInModel.class);


            boolean  flag = PublicMethod.checkSignIn(signInModel);


            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }






}
