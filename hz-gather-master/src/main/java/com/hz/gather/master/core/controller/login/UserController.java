package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.UUIDUtils;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.model.region.RegionModel;
import com.hz.gather.master.core.model.stream.ConsumerChannelModel;
import com.hz.gather.master.core.model.stream.StreamConsumerModel;
import com.hz.gather.master.core.model.user.CommonModel;
import com.hz.gather.master.core.protocol.request.user.*;
import com.hz.gather.master.core.protocol.response.user.*;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.HodgepodgeMethod;
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
     * 字段格式 { "token":"e1a5d05a171242698ba0682c80632b72"}
     * @date 2020/1/9 14:15
     */
    @PostMapping("/queryUserInfo")
    public JsonResult<Object> getRegisterSms(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        String time ="";
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        log.info("----------:queryUserInfo 进来啦!");
        String  strData="";
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);

            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            ResponseUserInfo ResponseUserInfo =ComponentUtil.userInfoService.toResponseUserInfo(memberId);
            strData  =   PublicMethod.toJson(ResponseUserInfo);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;


            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.USER_QUERYUSERINFO.getType(),
                    ServerConstant.InterfaceEnum.USER_QUERYUSERINFO.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.USER_QUERYUSERINFO.getType(),
                    ServerConstant.InterfaceEnum.USER_QUERYUSERINFO.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:getFriend 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.SERVER_OK.geteCode(),ENUM_ERROR.SERVER_OK.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            ResponseMyFriend reponseMyFriend=ComponentUtil.userInfoService.queryMyFriend(memberId);

            strData = PublicMethod.toJson(reponseMyFriend);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.USER_MYFRIEND.getType(),
                    ServerConstant.InterfaceEnum.USER_MYFRIEND.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.USER_MYFRIEND.getType(),
                    ServerConstant.InterfaceEnum.USER_MYFRIEND.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
     * 字段格式: { "token":"de7c16c96ccd475c8108935db685584e"}
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
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:queryUser 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);
            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.SERVER_OK.geteCode(),ENUM_ERROR.SERVER_OK.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }
            VcMember   vcMember =  ComponentUtil.userInfoService.getMemeberInfo(memberId);

            if(vcMember==null){
                throw  new ServiceException(ENUM_ERROR.A00016.geteCode(),ENUM_ERROR.A00016.geteDesc());
            }

            ResponseUser responseUser=PublicMethod.toResponseUser(vcMember);
            strData = PublicMethod.toJson(responseUser);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.USER_QUERYUSER.getType(),
                    ServerConstant.InterfaceEnum.USER_QUERYUSER.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.USER_QUERYUSER.getType(),
                    ServerConstant.InterfaceEnum.USER_QUERYUSER.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:editUserInfo 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            editUser  = JSON.parseObject(data, RequestEditUser.class);

            boolean  flag  =   PublicMethod.toResponseUser(editUser);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(editUser.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            Integer  updateCount  =  ComponentUtil.userInfoService.editMemeberInfo(memberId,editUser);
            ResponseEditUser responseEditUser =PublicMethod.rsResponseEditUser(updateCount);
            strData = PublicMethod.toJson(responseEditUser);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, editUser, ServerConstant.InterfaceEnum.USER_EDITUSERINFO.getType(),
                    ServerConstant.InterfaceEnum.USER_EDITUSERINFO.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, editUser, ServerConstant.InterfaceEnum.USER_EDITUSERINFO.getType(),
                    ServerConstant.InterfaceEnum.USER_EDITUSERINFO.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
     * 字段格式 { "token":"de7c16c96ccd475c8108935db685584e","pageNumber":1,"pageSize":3}
     * @date 2020/1/9 19:54
     */
    @PostMapping("/myFundList")
    public JsonResult<Object> myFundList(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        RequestFundList requestFundList = new RequestFundList();
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:myFundList 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            requestFundList  = JSON.parseObject(data, RequestFundList.class);

            boolean  flag  =   PublicMethod.cheakEequestFundList(requestFundList);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(requestFundList.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            //根据分页查询该用户的资金明细信息
            UMoneyList uMoneyList = BeanUtils.copy(requestFundList, UMoneyList.class);
            uMoneyList.setMemberId(memberId);
            List<Object> uMoneyList1 = ComponentUtil.userInfoService.getUMoneyList(uMoneyList);

            VcMemberResource vcMemberResource  =  ComponentUtil.userInfoService.queryMemberResourceInfo(memberId);

            ResponseFundList responseFundList=PublicMethod.toResponseFundList(vcMemberResource,uMoneyList1, uMoneyList.getRowCount());
//            ResponseEditUser responseEditUser =PublicMethod.rsResponseEditUser(updateCount);
            strData = PublicMethod.toJson(responseFundList);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestFundList, ServerConstant.InterfaceEnum.USER_MYFUNDLIST.getType(),
                    ServerConstant.InterfaceEnum.USER_MYFUNDLIST.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestFundList, ServerConstant.InterfaceEnum.USER_MYFUNDLIST.getType(),
                    ServerConstant.InterfaceEnum.USER_MYFUNDLIST.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * 我的支付信息接口
     * @param request
     * @param response
     * @param requestData
     * @return
     * @throws Exception
     */
    @PostMapping("/myCashRate")
    public JsonResult<Object> myCashRate(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        RequestCashRate requestCashRate = new RequestCashRate();

        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:myCashRate 进来啦!");
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            requestCashRate  = JSON.parseObject(data, RequestCashRate.class);

            boolean  flag  =   PublicMethod.cheakRequestCashRate(requestCashRate);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(requestCashRate.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            //根据分页查询该用户的资金明细信息
            UCashOutLog uCashOutLog = BeanUtils.copy(requestCashRate, UCashOutLog.class);
            uCashOutLog.setMemberId(memberId);

            List<CashRate> cashRateList = ComponentUtil.payService.queryCashLog(uCashOutLog);



            ResponeseCashRate responeseCashRate=PublicMethod.toResponeseCashRate(cashRateList);
//            ResponseEditUser responseEditUser =PublicMethod.rsResponseEditUser(updateCount);
            strData = PublicMethod.toJson(responeseCashRate);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestCashRate, ServerConstant.InterfaceEnum.USER_MYCASHRATE.getType(),
                    ServerConstant.InterfaceEnum.USER_MYCASHRATE.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestCashRate, ServerConstant.InterfaceEnum.USER_MYCASHRATE.getType(),
                    ServerConstant.InterfaceEnum.USER_MYCASHRATE.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * 获取修改支付密码的pay
     * @param request
     * @param response
     * @param requestData
     * @return
     * @throws Exception
     */
    @PostMapping("/check_payPassword")
    public JsonResult<Object> checkPayPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        RequsetPayPassword requsetPayPassword = new RequsetPayPassword();
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:checkPayPassword 进来啦!");
        try{
            data        =   StringUtil.decoderBase64(requestData.jsonData);
            requsetPayPassword  = JSON.parseObject(data, RequsetPayPassword.class);

            boolean  flag  =   PublicMethod.checkRequsetPayPassword(requsetPayPassword);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }


            flag = ComponentUtil.loginService.checkVerifCode(requsetPayPassword.getTimeStamp(),requsetPayPassword.getPhone(),requsetPayPassword.getSmsCode(),4);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(requsetPayPassword.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }


            String  token  = UUIDUtils.createUUID();
            ComponentUtil.loginService.getPayPwToken(memberId,token);


            ResponesePayPassword responesePayPassword=PublicMethod.toResponesePayPassword(token);
            strData = PublicMethod.toJson(responesePayPassword);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requsetPayPassword, ServerConstant.InterfaceEnum.USER_CHECKPAYPASSWORD.getType(),
                    ServerConstant.InterfaceEnum.USER_CHECKPAYPASSWORD.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requsetPayPassword, ServerConstant.InterfaceEnum.USER_CHECKPAYPASSWORD.getType(),
                    ServerConstant.InterfaceEnum.USER_CHECKPAYPASSWORD.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);

            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * 修改支付密码接口
     * @param request
     * @param response
     * @param requestData
     * @return
     * @throws Exception
     */
    @PostMapping("/update_payPassword")
    public JsonResult<Object> updatePayPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        RequsetUqPayPw requsetUqPayPw = new RequsetUqPayPw();
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:updatePayPassword 进来啦!");
        try{
            data        =   StringUtil.decoderBase64(requestData.jsonData);
            requsetUqPayPw  = JSON.parseObject(data, RequsetUqPayPw.class);

            boolean  flag  =   PublicMethod.checkRequsetUqPayPw(requsetUqPayPw);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            if(!requsetUqPayPw.getPayPw().equals(requsetUqPayPw.getPayPw2())){
                throw  new ServiceException(ENUM_ERROR.A00005.geteCode(),ENUM_ERROR.A00005.geteDesc());
            }
//            flag = ComponentUtil.loginService.checkVerifCode(requsetPayPassword.getTimeStamp(),requsetPayPassword.getPhone(),requsetPayPassword.getSmsCode(),4);
//            if(!flag){
//                throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
//            }
//
            memberId = PublicMethod.tokenGetMemberId(requsetUqPayPw.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.A00006.geteCode(),ENUM_ERROR.A00006.geteDesc());
            }


            Integer   memberId2 = PublicMethod.tokenGetMemberId(requsetUqPayPw.getToken());
            if(memberId2==0){
                throw  new ServiceException(ENUM_ERROR.P00008.geteCode(),ENUM_ERROR.P00008.geteDesc());
            }

            if(memberId!=memberId2){
                throw  new ServiceException(ENUM_ERROR.P00009.geteCode(),ENUM_ERROR.P00009.geteDesc());
            }

            Integer  count =ComponentUtil.userInfoService.updatePayPassword(memberId,requsetUqPayPw.getPayPw());

//            ComponentUtil.loginService.getPayPwToken(memberId,token);

            ResponseUpdatePayPw  responseUpdatePayPw  =PublicMethod.toResponesePayPassword(count);
            strData = PublicMethod.toJson(responseUpdatePayPw);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requsetUqPayPw, ServerConstant.InterfaceEnum.USER_UPDATEPAYPASSWORD.getType(),
                    ServerConstant.InterfaceEnum.USER_UPDATEPAYPASSWORD.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requsetUqPayPw, ServerConstant.InterfaceEnum.USER_UPDATEPAYPASSWORD.getType(),
                    ServerConstant.InterfaceEnum.USER_UPDATEPAYPASSWORD.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * 第一次修改密码
     * @param request
     * @param response
     * @param requestData
     * @return
     * @throws Exception
     */
    @PostMapping("/first_uqdatePayPw")
    public JsonResult<Object> firstUqdatePayPw(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        ResponseFirstUqdatePayPw requsetUqPayPw = new ResponseFirstUqdatePayPw();
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        long memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        String  strData="";
        log.info("----------:firstUqdatePayPw 进来啦!");
        try{
            data        =   StringUtil.decoderBase64(requestData.jsonData);
            requsetUqPayPw  = JSON.parseObject(data, ResponseFirstUqdatePayPw.class);

            boolean  flag  =   PublicMethod.checkResponseFirstUqdatePayPw(requsetUqPayPw);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }


            if(!requsetUqPayPw.getPayPw().equals(requsetUqPayPw.getPayPw2())){
                throw  new ServiceException(ENUM_ERROR.A00005.geteCode(),ENUM_ERROR.A00005.geteDesc());
            }
            memberId = HodgepodgeMethod.checkIsLogin(requsetUqPayPw.getToken());

            Integer memberId1 = Integer.parseInt(String.valueOf(memberId));

            if(memberId1==0){
                throw  new ServiceException(ENUM_ERROR.A00006.geteCode(),ENUM_ERROR.A00006.geteDesc());
            }

            Integer  count =ComponentUtil.userInfoService.updatePayPassword(memberId1,requsetUqPayPw.getPayPw());

            ResponseUpdatePayPw  responseUpdatePayPw  =PublicMethod.toResponesePayPassword(count);
            strData = PublicMethod.toJson(responseUpdatePayPw);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requsetUqPayPw, ServerConstant.InterfaceEnum.USER_FIRSTUQDATEPAYPW.getType(),
                    ServerConstant.InterfaceEnum.USER_FIRSTUQDATEPAYPW.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requsetUqPayPw, ServerConstant.InterfaceEnum.USER_FIRSTUQDATEPAYPW.getType(),
                    ServerConstant.InterfaceEnum.USER_FIRSTUQDATEPAYPW.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * 外部接口调用，更新数据接口
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/updateBaseInfo")
    public JsonResult<Object> updateBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String data = "";
//        ResponseFirstUqdatePayPw requsetUqPayPw = new ResponseFirstUqdatePayPw();
        log.info("----------:updateBaseInfo 进来啦!");
        try{
//            data        =   StringUtil.decoderBase64(requestData.jsonData);
//            requsetUqPayPw  = JSON.parseObject(data, ResponseFirstUqdatePayPw.class);

            ComponentUtil.initService.initBasics();

//            ResponseUpdatePayPw  responseUpdatePayPw  =PublicMethod.toResponesePayPassword(count);
//            data = PublicMethod.toJson(responseUpdatePayPw);
//            String encryptionData = StringUtil.mergeCodeBase64(data);
//            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
//            resultDataModel.jsonData = encryptionData;
            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

}
