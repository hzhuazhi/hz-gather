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
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.region.RegionModel;
import com.hz.gather.master.core.protocol.request.login.*;
import com.hz.gather.master.core.protocol.response.login.ResponseRegisterVerify;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.HodgepodgeMethod;
import com.hz.gather.master.util.PublicMethod;
import com.hz.gather.master.util.TaskMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
     * local:   http://localhost:8082/mg/login/register_sms
     *
     * 字段格式 { "smsType": 1,"phone":"13606768872","country":"中国","areaCode": "086","version": "1.0.1" }
     * 字段格式 { "smsType": 2,"phone": "13606768872","version": "1.0.1" }
     * 字段格式 { "smsType": 3,"phone":"13606768872","version": "1.0.1" }
     * 字段格式 { "token":"ajsdjasdhashdsad","smsType": 4,"phone":"13606768872","version": "1.0.1" }
     * return
     * {
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJ0aW1lU3RhbXAiOiIxNTc4NzI0MTQzIn0="
     *     }
     * }
     * @date 2020/1/2 21:02
     */
    @PostMapping("/register_sms")
//    public JsonResult<Object> getRegisterSms(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData)throws Exception{
    public JsonResult<Object> getRegisterSms(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        SendSmsModel sendSmsModel = new SendSmsModel();
        String time ="";
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            sendSmsModel  = JSON.parseObject(data, SendSmsModel.class);

            boolean  flag =PublicMethod.checkPhoneIsType(sendSmsModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }
            if(sendSmsModel.getSmsType()==1){
                time  =  ComponentUtil.loginService.sendRegister(sendSmsModel.getPhone(),sendSmsModel.getCountry());
            }else if(sendSmsModel.getSmsType()==2){


                time  =  ComponentUtil.loginService.sendForgetPassword(sendSmsModel.getPhone());
            }else if(sendSmsModel.getSmsType()==3){
                time  =  ComponentUtil.loginService.sendSmsSignIn(sendSmsModel.getPhone());
            }else if(sendSmsModel.getSmsType()==4){
                HodgepodgeMethod.checkIsLogin(sendSmsModel.getToken());
                time  =  ComponentUtil.loginService.sendSmsPayPassword(sendSmsModel.getPhone());
            }

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
     * local:   http://localhost:8082/mg/login/register_info
     * 字段格式: { "phone": "13606768872","smsCode": "中国","timeStamp": "1578053576","passWrod": "111111","inviteCode": "382032","version": "1.0.1" }
     * @author long
     * {
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJ0aW1lU3RhbXAiOiIxNTc4NzI0MTQzIn0="
     *     }
     * }
     * @date 2020/1/2 20:59
     */
    @PostMapping("/register_info")
//    public JsonResult<Object> getRegisterInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData)throws Exception{
    public JsonResult<Object> getRegisterInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        LoginModel loginModel = new LoginModel();
        try{
            log.info("----------:register_info 进来啦!");
            data        = StringUtil.decoderBase64(requestData.jsonData);
            loginModel  = JSON.parseObject(data, LoginModel.class);
            boolean   flag  = PublicMethod.isCheakRegister(loginModel);

            //参数是否正确
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            //用户手机号码是否被注册
            flag = ComponentUtil.loginService.isPhoneExist(loginModel.getPhone());
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00001.geteCode(),ENUM_ERROR.A00001.geteDesc());
            }

            //用户邀请码是否正确
            flag  =  ComponentUtil.loginService.checkInviteCode(loginModel.getInviteCode());
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00002.geteCode(),ENUM_ERROR.A00002.geteDesc());
            }

            //验证码是否正确
            flag = ComponentUtil.loginService.checkVerifCode(loginModel.getTimeStamp(),loginModel.getPhone(),loginModel.getSmsCode(),1);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
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




    @PostMapping("/register_verify")
    public JsonResult<Object> getRegisterVerify(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        LoginModel loginModel = new LoginModel();
        try{
            log.info("----------:register_info 进来啦!");
            data        = StringUtil.decoderBase64(requestData.jsonData);
            loginModel  = JSON.parseObject(data, LoginModel.class);
            boolean   flag  = PublicMethod.isCheakRegisterVerify(loginModel);

            //参数是否正确
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            //用户手机号码是否被注册
            flag = ComponentUtil.loginService.isPhoneExist(loginModel.getPhone());
            if(flag){
                throw  new ServiceException(ENUM_ERROR.A00001.geteCode(),ENUM_ERROR.A00001.geteDesc());
            }

            //用户邀请码是否正确
            flag  =  ComponentUtil.loginService.checkInviteCode(loginModel.getInviteCode());
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00002.geteCode(),ENUM_ERROR.A00002.geteDesc());
            }

            //验证码是否正确
            flag = ComponentUtil.loginService.checkVerifCode(loginModel.getTimeStamp(),loginModel.getPhone(),loginModel.getSmsCode(),1);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
            }




            ResponseRegisterVerify verify = PublicMethod.toResponseRegisterVerify(true);

//            String  token  = ComponentUtil.loginService.addMemberInfo(loginModel);
            data  = PublicMethod.toJson(verify);
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
     *  local:   http://localhost:8082/mg/login/forget_password
     * { "pwToken": "sxxxxx","passWord": "1578053576","passWordConfirm": "111111"}
     * @date 2020/1/3 21:57
     */
    @PostMapping("/forget_password")
    public JsonResult<Object> getForgetPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel();
        String   dtoken ="";
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
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
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00005.geteCode(),ENUM_ERROR.A00005.geteDesc());
            }

            VcMember vcMember = ComponentUtil.loginService.queryVcMember(memberId);
            if(vcMember==null){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }

            if(StringUtils.isBlank(vcMember.getPassword())){
                if(vcMember.getPassword().equals(forgetPasswordModel.getPassWord())){
                    throw  new ServiceException(ENUM_ERROR.A00017.geteCode(),ENUM_ERROR.A00017.geteDesc());
                }
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
     * @Description: 忘记密码接口,根据手机号和验证码修改信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * local:   http://localhost:8082/mg/login/forget_phone
     *{ "phone": "1362723292","verificationCode": "827323","timeStamp": "111111"}
     * @date 2020/1/3 21:57
     */
    @PostMapping("/forget_phone")
  public JsonResult<Object> getForgetPhone(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{

        log.info("----------:进来啦!");
        String data = "";
        ForgetPhoneModel forgetPhoneModel = new ForgetPhoneModel();
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            forgetPhoneModel  = JSON.parseObject(data, ForgetPhoneModel.class);

            boolean  flag = PublicMethod.checkPhoneVerificaCode(forgetPhoneModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }

            flag = ComponentUtil.loginService.checkVerifCode(forgetPhoneModel.getTimeStamp(),forgetPhoneModel.getPhone(),forgetPhoneModel.getVerificationCode(),2);
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
            String encryptionData = StringUtil.mergeCodeBase64(pwToken);
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
     * @Description: 登录信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * local:   http://localhost:8082/mg/login/sign_in
     * 字段格式 { "type": 1, "phone":"13606768872","password":"111111"}
     * 字段格式 { "type": 1, "phone":"13606768872","smsCode": "888888" ,"timeStamp": "123124123" }
     * @date 2020/1/7 13:46
     */
    @PostMapping("/sign_in")
    public JsonResult<Object> getSignIn(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        log.info("----------:进来啦!");
        String data = "";
        SignInModel signInModel = new SignInModel();
        String   token ="";
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            signInModel  = JSON.parseObject(data, SignInModel.class);
            boolean  flag = PublicMethod.checkSignIn(signInModel);

            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00013.geteCode(),ENUM_ERROR.A00013.geteDesc());
            }

            if (signInModel.getType()==1){
                token=ComponentUtil.loginService.passwordSignIn(signInModel.getPhone(),signInModel.getPassword());
            }else if(signInModel.getType()==2){
                token=ComponentUtil.loginService.phoneSmsCodeSignIn(signInModel.getPhone(),signInModel.getTimeStamp(),signInModel.getSmsCode());
            }

            if(StringUtils.isBlank(token)){
                throw  new ServiceException(ENUM_ERROR.A00010.geteCode(),ENUM_ERROR.A00010.geteDesc());
            }

            if(token.equals("1")){
                throw  new ServiceException(ENUM_ERROR.A00002.geteCode(),ENUM_ERROR.A00002.geteDesc());
            }else if(token.equals("2")){
                throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
            }

            data  =     PublicMethod.toToken(token);

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
