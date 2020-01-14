package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.entity.UMoneyList;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.user.CommonModel;
import com.hz.gather.master.core.protocol.request.user.RequestEditUser;
import com.hz.gather.master.core.protocol.response.user.*;
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
 * @Date 2020/1/7 16:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * @Description: 主页信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad"}
     * @date 2020/1/9 14:15
     */
    @PostMapping("/queryUserInfo")
    public JsonResult<Object> getRegisterSms(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        String time ="";
        log.info("----------:queryUserInfo 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);

            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            ResponseUserInfo ResponseUserInfo =ComponentUtil.userInfoService.toResponseUserInfo(memberId);
            data=PublicMethod.toJson(ResponseUserInfo);
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
     * @Description: 我的好友
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad"}
     *
     * local:   http://localhost:8082/mg/user/myFriend
     * 字段格式: { "token":"xxxaxxsadasqweqeqweqsad"}
     * @author long
     * {
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJ0aW1lU3RhbXAiOiIxNTc4NzI0MTQzIn0="
     *     }
     * }
     * @date 2020/1/9 14:15
     */
    @PostMapping("/myFriend")
    public JsonResult<Object> getFriend(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        String time ="";
        log.info("----------:getFriend 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.SERVER_OK.geteCode(),ENUM_ERROR.SERVER_OK.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            ResponseMyFriend reponseMyFriend=ComponentUtil.userInfoService.queryMyFriend(memberId);

            data = PublicMethod.toJson(reponseMyFriend);
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
     * @Description: 查询用户基本信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad"}
     * @author long
     * local:   http://localhost:8082/mg/user/queryUser
     * 字段格式: { "token":"xxxaxxsadasqweqeqweqsad"}
     * {
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJ0aW1lU3RhbXAiOiIxNTc4NzI0MTQzIn0="
     *     }
     * }
     * @date 2020/1/9 14:20
     */
    @PostMapping("/queryUser")
    public JsonResult<Object> queryUser(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        String time ="";
        log.info("----------:queryUser 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.SERVER_OK.geteCode(),ENUM_ERROR.SERVER_OK.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            VcMember   vcMember =  ComponentUtil.userInfoService.getMemeberInfo(memberId);

            if(vcMember==null){
                throw  new ServiceException(ENUM_ERROR.A00016.geteCode(),ENUM_ERROR.A00016.geteDesc());
            }

            ResponseUser responseUser=PublicMethod.toResponseUser(vcMember);
            data = PublicMethod.toJson(responseUser);
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
     * @Description: 编辑用户信息接口
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * token 是必须的需要+ 如何一个字段才能进行通过
     * 字段格式
     *
     * local:   http://localhost:8082/mg/user/editUserInfo
     * 字段格式: { "token":"xxxaxxsadasqweqeqweqsad","memberAdd":"http://xx","nickname":"我还是个孩子","sex":"1","birthday":"2012-12-12"}
     * {
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJ0aW1lU3RhbXAiOiIxNTc4NzI0MTQzIn0="
     *      }
     * }
     * @date 2020/1/9 16:11
     */
    @PostMapping("/editUserInfo")
    public JsonResult<Object> editUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        RequestEditUser editUser = new RequestEditUser();
        log.info("----------:editUserInfo 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            editUser  = JSON.parseObject(data, RequestEditUser.class);

            boolean  flag  =   PublicMethod.toResponseUser(editUser);
            if(flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer   memberId = PublicMethod.tokenGetMemberId(editUser.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer  updateCount  =  ComponentUtil.userInfoService.editMemeberInfo(memberId,editUser);
            ResponseEditUser responseEditUser =PublicMethod.rsResponseEditUser(updateCount);
            data = PublicMethod.toJson(responseEditUser);
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
     * @Description: 我的资金详细信息
     * @param request
    * @param response
    * @param jsonData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * local:   http://localhost:8082/mg/user/myFundList
     * 字段格式 { "token":"xxxaxxsadasqweqeqweqsad","pageNumber":1,"pageSize":3}
     * @date 2020/1/9 19:54
     */
    @PostMapping("/myFundList")
    public JsonResult<Object> myFundList(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        RequestEditUser editUser = new RequestEditUser();
        log.info("----------:myFundList 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            editUser  = JSON.parseObject(data, RequestEditUser.class);

            boolean  flag  =   PublicMethod.toResponseUser(editUser);
            if(flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            Integer   memberId = PublicMethod.tokenGetMemberId(editUser.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            //根据分页查询该用户的资金明细信息
            UMoneyList uMoneyList1 = BeanUtils.copy(editUser, UMoneyList.class);
            uMoneyList1.setMemberId(memberId);
            List<UMoneyList> uMoneyList = ComponentUtil.userInfoService.queryByList(uMoneyList1);

//            ResponseEditUser responseEditUser =PublicMethod.rsResponseEditUser(updateCount);
            data = PublicMethod.toJson(uMoneyList);
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
