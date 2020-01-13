package com.hz.gather.master.util;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.DateUtil;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.DateModel;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.model.user.CommonModel;
import com.hz.gather.master.core.model.user.FriendModel;
import com.hz.gather.master.core.protocol.request.login.*;
import com.hz.gather.master.core.protocol.request.pay.RequestAddZFBPay;
import com.hz.gather.master.core.protocol.request.pay.RequestPayCashOut;
import com.hz.gather.master.core.protocol.request.user.RequestEditUser;
import com.hz.gather.master.core.protocol.response.login.ForgetPhoneDto;
import com.hz.gather.master.core.protocol.response.login.LoginModelDto;
import com.hz.gather.master.core.protocol.response.login.SendSmsDto;
import com.hz.gather.master.core.protocol.response.login.SignInModelDto;
import com.hz.gather.master.core.protocol.response.pay.ResponeseAddZFBPay;
import com.hz.gather.master.core.protocol.response.pay.ResponeseHavaPayInfo;
import com.hz.gather.master.core.protocol.response.user.ResponseEditUser;
import com.hz.gather.master.core.protocol.response.user.ResponseMyFriend;
import com.hz.gather.master.core.protocol.response.user.ResponseUser;
import com.hz.gather.master.core.protocol.response.user.ResponseUserInfo;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/2 21:17
 * @Version 1.0
 */
public class PublicMethod {
    /**
     * @Description: 查询电话号码是否被注册
     * @param phone
     * @return com.pf.play.rule.core.model.VcPhoneDeploy
     * @author long
     * @date 2019/12/20 10:37
     */
    public  static VcMember  queryPhoneVcMember(String  phone){
        VcMember vcMember = new  VcMember();
        vcMember.setPhone(phone);
        return   vcMember;
    }

    /**
     * @Description: TODO
     * @param timeStamp
     * @return com.hz.gather.master.core.model.login.SendSmsDto
     * @author long
     * @date 2020/1/2 22:32
     */
    public  static String toSendSmsDto(String  timeStamp){
        SendSmsDto sendSmsDto = new SendSmsDto();
        sendSmsDto.setTimeStamp(timeStamp);
        return   JSON.toJSONString(sendSmsDto);
    }


    /**
     * @Description: 检查注册参数是否有效
     * @param loginModel
     * @return boolean
     * @author long
     * @date 2020/1/2 23:44
     */
    public  static boolean isCheakRegister(LoginModel loginModel){
        boolean  flag =  false;
        if(StringUtils.isBlank(loginModel.getInviteCode())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getPassWrod())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getPhone())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getSmsCode())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getTimeStamp())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getVersion())){
            return  flag;
        }
        return   true;
    }

    /**
     * @Description: 查询电话号码是否被注册
     * @param inviteCode
     * @return com.pf.play.rule.core.model.VcPhoneDeploy
     * @author long
     * @date 2019/12/20 10:37
     */
    public  static VcMember  queryInviteCode(String  inviteCode){
        VcMember vcMember = new  VcMember();
        vcMember.setInviteCode(inviteCode);
        return   vcMember;
    }


    /**
     * @Description: TODO
     * @param memberId
    * @param registerReq
    * @param SecureUUID
    * @param superiorId
    * @param extensionMemberId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/11/24 16:14
     */
    public  static VcMember insertVcMember(Integer memberId, LoginModel loginModel,String [] SecureUUID,Integer superiorId,String extensionMemberId){
        VcMember  memberModel = new VcMember();
        Integer  createTime  =  Integer.parseInt(DateUtil.timeStamp());
        Integer  loginTime  =  createTime;
        memberModel.setMemberId(memberId);
        memberModel.setMemberCode("C"+loginModel.getPhone());
        memberModel.setInviteCode(SecureUUID[0]);
        memberModel.setTradingAddress(SecureUUID[1]);
        memberModel.setCreateTime(createTime);
        memberModel.setLoginTime(loginTime);
        memberModel.setSuperiorId(superiorId);
        memberModel.setPassword(loginModel.getPassWrod());
        String    benefitMemberId   =  PublicMethod.getBenefitMemberId(extensionMemberId,Constant.REWARD_FISSION_COUNT);
        memberModel.setBenefitMemberId(benefitMemberId);
        memberModel.setExtensionMemberId(extensionMemberId+","+memberId);
        return   memberModel;
    }

    /**
     * @Description: 注册用户会员资源初始化
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/11/25 10:27
     */
    public  static VcMemberResource insertVcMemberResource(Integer memberId){
        Date createdate =  DateUtil.currentTimestamp();
        VcMemberResource vcMemberResourceModel  =  new  VcMemberResource();
        vcMemberResourceModel.setMemberId(memberId);
        vcMemberResourceModel.setCreateTime(createdate);
        vcMemberResourceModel.setUpdateTime(new Date());
        return   vcMemberResourceModel;
    }

    /**
     * @Description: 更新资源表里面的用户信息
     * @param memberId
     * @return com.hz.gather.master.core.model.dao.VcMemberResource
     * @author long
     * @date 2020/1/3 16:17
     */
    public  static VcMemberResource updateResourcePeople(Integer memberId){
        VcMemberResource   vcMemberResourceModel  =  new  VcMemberResource();
        vcMemberResourceModel.setMemberId(memberId);
        vcMemberResourceModel.setPushPeopleAll(1);
        vcMemberResourceModel.setTeamActiveAll(1);
        vcMemberResourceModel.setUpdateTime(new Date());
        return   vcMemberResourceModel;
    }

    /**
     * @Description: 给token 返回给用户
     * @param token
     * @return java.lang.String
     * @author long
     * @date 2020/1/3 16:57
     */
    public  static String    toLoginModelDto(String  token){
        LoginModelDto  loginModelDto = new LoginModelDto();
        loginModelDto.setToken(token);
        return   JSON.toJSONString(loginModelDto);
    }


    /**
     * @Description: 用户查询手机号信息
     * @param sendSmsModel
     * @return boolean
     * @author long
     * @date 2020/1/5 14:25
     */
    public  static  boolean  checkPhoneIsType(SendSmsModel sendSmsModel){
        boolean  flag =  false ;
        if(null==sendSmsModel.getSmsType()){
            return flag;
        }
        if (sendSmsModel.getSmsType()==1){
            if(StringUtils.isBlank(sendSmsModel.getPhone())){
                return flag;
            }else if(StringUtils.isBlank(sendSmsModel.getAreaCode())){
                return flag;
            }
        }else{
            if (StringUtils.isBlank(sendSmsModel.getPhone())){
                return flag;
            }
        }
        return true;
    }


    /**
     * @Description: 检查手机号码以及验证码是否为空
     * @param forgetPhoneModel
     * @return boolean
     * @author long
     * @date 2020/1/5 21:37
     */
    public  static  boolean  checkPhoneVerificaCode(ForgetPhoneModel forgetPhoneModel){
        boolean  flag =  false ;
        if(StringUtils.isBlank(forgetPhoneModel.getPhone())){
            return flag;
        }else if (StringUtils.isBlank(forgetPhoneModel.getVerificationCode())){
            return flag;
        }
        return true;
    }

    /**
     * 登录信息是否完成
     * @param signInModel
     * @return
     */
    public  static  boolean  checkSignIn(SignInModel signInModel){
        boolean  flag =  false ;
        if(signInModel.getType()==1){
            if(StringUtils.isBlank(signInModel.getPhone())){
                return flag;
            }else if (StringUtils.isBlank(signInModel.getPassword())){
                return flag;
            }
        }else if(signInModel.getType()==2){
            if(StringUtils.isBlank(signInModel.getPhone())){
                return flag;
            }else if (StringUtils.isBlank(signInModel.getSmsCode())){
                return flag;
            }else if (StringUtils.isBlank(signInModel.getTimeStamp())){
                return flag;
            }
        }else{
            return  flag;
        }

        return true;
    }


    /**
     * @Description: 给pw token 返回给用户
     * @param token
     * @return java.lang.String
     * @author long
     * @date 2020/1/3 16:57
     */
    public  static String    toForgetPhoneDto(String  token){
        ForgetPhoneDto forgetPhoneDto= new ForgetPhoneDto();
        forgetPhoneDto.setPwToken(token);
        return   JSON.toJSONString(forgetPhoneDto);
    }

    /**
     * @Description: 检查手机号码以及验证码是否为空
     * @param forgetPasswordModel
     * @return boolean
     * @author long
     * @date 2020/1/5 21:37
     */
    public  static  boolean  checkPwToken(ForgetPasswordModel forgetPasswordModel){
        boolean  flag =  false ;
        if(StringUtils.isBlank(forgetPasswordModel.getPwToken())){
            return flag;
        }else if(StringUtils.isBlank(forgetPasswordModel.getPassWord())){
            return flag;
        }else if(StringUtils.isBlank(forgetPasswordModel.getPassWordConfirm())){
            return flag;
        }
        return true;
    }

    /**
     * @Description: 转换成VcMember 信息
     * @param phone
     * @return com.hz.gather.master.core.model.dao.VcMember
     * @author long
     * @date 2020/1/6 10:39
     */
    public  static  VcMember  toVcMember(String phone){
        VcMember  vcMember =  new  VcMember();
        vcMember.setPhone(phone);
        return vcMember;
    }


    /**
     * @Description: TODO
     * @param token
     * @return java.lang.Integer
     * @author long
     * @date 2020/1/6 11:30
     */
    public  static  Integer  tokenToMemberId(String token){
        Integer   memberId  =0;
        String memberIdStr = (String) ComponentUtil.redisService.get(token);
        if(!StringUtils.isBlank(memberIdStr)){
            memberId =Integer.parseInt(memberIdStr);
        }
        return memberId;
    }

    /**
     * 二次密码是否一致
     * @param passWord
     * @param passwordConfirm
     * @return
     */
    public  static  boolean  pwIsOK(String passWord,String passwordConfirm){
       boolean   flag =  false ;
       if (!passWord.equals(passwordConfirm)){
            return flag;
       }
        return true;
    }

    /***
     * memberId 设置VcMember
     * @param memberId
     * @return
     */
    public  static  VcMember  toVcMember(Integer memberId){
        VcMember  vcMember =  new  VcMember();
        vcMember.setMemberId(memberId);
        return vcMember;
    }


    /**
     * @Description: TODO
     * @param phone
    * @param password
     * @return com.hz.gather.master.core.model.dao.VcMember
     * @author long
     * @date 2020/1/6 20:10
     */
    public  static  VcMember  toVcMember(String phone,String password){
        VcMember  vcMember =  new  VcMember();
        vcMember.setPhone(phone);
        vcMember.setPassword(password);
        return vcMember;
    }

    /**
     * 设置memberId + token
     * @param memberId
     * @param token
     * @return
     */
    public  static  VcMember  toVcMember(Integer memberId,String token){
        VcMember  vcMember =  new  VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setToken(token);
        return vcMember;
    }

    /**
     * @Description: TODO
     * @param memberId
     * @return com.hz.gather.master.core.model.dao.VcMemberResource
     * @author long
     * @date 2020/1/7 11:03
     */
    public  static  VcMemberResource  toVcMemberResource(Integer memberId){
        VcMemberResource  vcMemberResource =  new  VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        return vcMemberResource;
    }


    /**
     * @Description: 给 toToken
     * @param token
     * @return java.lang.String
     * @author long
     * @date 2020/1/3 16:57
     */
    public  static String  toToken(String  token){
        SignInModelDto  signInModelDto= new SignInModelDto();
        signInModelDto.setToken(token);
        return   JSON.toJSONString(signInModelDto);
    }

    /**
     * @Description: 根据token  查询用户信息
     * @param token
     * @return java.lang.Integer
     * @author long
     * @date 2020/1/7 16:53
     */
    public  static Integer  tokenGetMemberId(String  token){
        Integer   memberId = 0;
        String  rsMemberId =(String)ComponentUtil.redisService.get(token);
        if(!StringUtils.isBlank(rsMemberId)){
            memberId  = Integer.parseInt(rsMemberId);
        }
        return memberId;
    }


    /**
     * @Description: isCommonModel 是否成功的
     * @param commonModel
     * @return boolean
     * @author long
     * @date 2020/1/7 17:13
     */
    public  static boolean  isCommonModel(CommonModel commonModel){
        boolean  flag  = false ;
        if(StringUtils.isBlank(commonModel.getToken())){
            return  flag ;
        }
        return true;
    }

    /**
     * @Description: 根据 batchNum  设置 ULimitedTimeLog 条件
     * @param batchNum
     * @return com.hz.gather.master.core.model.entity.ULimitedTimeLog
     * @author long
     * @date 2020/1/8 17:03
     */
    public  static ULimitedTimeLog toULimitedTimeLog(String batchNum){
        ULimitedTimeLog  uLimitedTimeLog =  new ULimitedTimeLog();
        uLimitedTimeLog.setBatchNum(batchNum);
        return  uLimitedTimeLog;
    }


    /**
     * 根据批次号查询这个批次号的用户
     * @param batchNum
     * @return
     */
    public  static UBatchLog toUBatchLog(String batchNum){
        UBatchLog  uBatchLog =  new UBatchLog();
        uBatchLog.setBatchNum(batchNum);
        return  uBatchLog;
    }

    /**
     * @Description: TODO
     * @param
     * @return com.hz.gather.master.core.protocol.response.user.ResponseUserInfo
     * @author long
     * @date 2020/1/8 17:51
     */
    public  static ResponseUserInfo   toResponseUserInfo(VcMember vcMember, VcMemberResource vcMemberResource, ULimitedTimeLog  limitedTimeLog, List<UBatchLog> list)throws  Exception{
        ResponseUserInfo responseUserInfo =  new ResponseUserInfo();
        responseUserInfo.setVip_type(vcMember.getGradeType());
        responseUserInfo.setAlready_money(vcMemberResource.getAlreadyMoney()+"");
        responseUserInfo.setTotal_money(vcMemberResource.getTotalMoney()+"");
        responseUserInfo.setSurplus_money(vcMemberResource.getSurplusMoney()+"");
        if(!StringUtils.isBlank(vcMember.getPassword())){
            responseUserInfo.setIsPw(1);
        }else{
            responseUserInfo.setIsPw(0);
        }

        if(!StringUtils.isBlank(vcMember.getPayPassword())){
            responseUserInfo.setIsPayPw(1);
        }else{
            responseUserInfo.setIsPayPw(0);
        }
        responseUserInfo.setIsprotection(vcMember.getIsQuestions());


        if (vcMember.getGradeType()==0){//普通用户信息

        }else if(vcMember.getGradeType()==1){//限时用户信息
            String recommendMoney = "";
            List<String>  addList  =   new ArrayList<>();
            int pushCount =0;
            int fissionCount =0;
            for(UBatchLog uBatchLog:list){
                if(uBatchLog.getDataType()==1){
                    recommendMoney = StringUtil.getBigDecimalAdd(recommendMoney,uBatchLog.getReceiveMoney()+"");
//                    recommendMoney=recommendMoney+uBatchLog.getReceiveMoney();
                    addList.add(uBatchLog.getMemberAdd());
                    pushCount++;
                }else{
                    fissionCount++;
                }
            }
            responseUserInfo.setPush_count(pushCount+"");
            responseUserInfo.setAddList(addList);
            SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String           expireTime      =  DateUtil.format(limitedTimeLog.getInvalidTime(),sdfLongTimePlus);
            responseUserInfo.setExpire_time(expireTime);
            responseUserInfo.setFission_money(limitedTimeLog.getFissionMoney()+"");
            responseUserInfo.setReality_push_count(Constant.FISSION_NUMBER+"");
            responseUserInfo.setRequire_fission_count(fissionCount+"");
        }else if(vcMember.getGradeType()==2){
            responseUserInfo.setRecommend_money(vcMemberResource.getPushMoney()+"");
            responseUserInfo.setFission_money(vcMemberResource.getFissionMoney()+"");
            responseUserInfo.setReality_push_count(Constant.FISSION_NUMBER+"");
            responseUserInfo.setRequire_fission_count(vcMemberResource.getTeamActive()+"");
        }

        return responseUserInfo;
    }

    /**
     * @Description: 对象赚json
     * @param o
     * @return java.lang.String
     * @author long
     * @date 2020/1/9 10:17
     */
    public  static  String  toJson(Object o){
        return  JSON.toJSONString(o);
    }



    /**
     * @Description: 转换成VcMember 信息
     * @param superiorId
     * @return com.hz.gather.master.core.model.dao.VcMember
     * @author long
     * @date 2020/1/6 10:39
     */
    public  static  VcMember  toVcMemberSuperiorId(Integer superiorId){
        VcMember  vcMember =  new  VcMember();
        vcMember.setSuperiorId(superiorId);
        return vcMember;
    }

    /**
     * @Description: 我的朋友信息
     * @param vcMemberResource
    * @param list
     * @return com.hz.gather.master.core.protocol.response.user.ReponseMyFriend
     * @author long
     * @date 2020/1/9 11:39
     */
    public  static ResponseMyFriend toVcMemberSuperiorId(VcMemberResource vcMemberResource, List<VcMember>   list){
        ResponseMyFriend  reponseMyFriend =  new  ResponseMyFriend();
        reponseMyFriend.setPush_people_vip(vcMemberResource.getPushPeople()+"");
        reponseMyFriend.setTeam_active_vip(vcMemberResource.getTeamActive()+"");
        reponseMyFriend.setTeam_active_all(vcMemberResource.getTeamActiveAll()+"");
        reponseMyFriend.setPush_people_all(vcMemberResource.getPushPeopleAll()+"");
        List <Object>   rsList = new ArrayList<>();
        for(VcMember vcMember:list){
            FriendModel  friendModel = new FriendModel();
            friendModel.setNickname(vcMember.getNickname());
            friendModel.setNickadd(vcMember.getMemberAdd());
            friendModel.setMoney(vcMember.getTotalMoney()+"");
            friendModel.setVip_type(vcMember.getGradeType()+"");
            friendModel.setCreate_time(vcMember.getCreateTime()+"");
            friendModel.setFission_people(vcMember.getPushPeople()+"");
            rsList.add(friendModel);
        }
        reponseMyFriend.setPush_people_list(rsList);
        return reponseMyFriend;
    }

    /**
     * @Description: TODO
     * @param vcMember
     * @return com.hz.gather.master.core.protocol.response.user.ResponseUser
     * @author long
     * @date 2020/1/9 14:07
     */
    public  static ResponseUser   toResponseUser(VcMember vcMember){
        ResponseUser  responseUser  =  new ResponseUser();
        responseUser.setNickname(vcMember.getNickname());
        responseUser.setMemberAdd(vcMember.getMemberAdd());
        responseUser.setCreateTime(vcMember.getCreateTime()+"");
        responseUser.setSex(vcMember.getSex()+"");
        responseUser.setBirthday(vcMember.getBirthday());
        return responseUser;
    }


    /**
     * @Description: 验证修改用户信息是否通过
     * @param editUser
     * @return boolean
     * @author long
     * @date 2020/1/9 15:24
     */
    public  static  boolean   toResponseUser(RequestEditUser editUser){
        boolean  flag  =  false;
        if(StringUtils.isBlank(editUser.getToken())){
            return flag;
        }
        if(!StringUtils.isBlank(editUser.getNickname())){
            return true;
        }else if(!StringUtils.isBlank(editUser.getMemberAdd())){
            return true;
        }else if(!StringUtils.isBlank(editUser.getBirthday())){
            return true;
        }else if(!StringUtils.isBlank(editUser.getSex())){
            return true;
        }
        return false;
    }

    /**
     * @Description: 拼装成用户修改信息
     * @param memberId
    * @param editUser
     * @return com.hz.gather.master.core.model.entity.VcMember
     * @author long
     * @date 2020/1/9 15:43
     */
    public static  VcMember   toVcMember(Integer  memberId,RequestEditUser editUser){
        VcMember  vcMember  =  new  VcMember();
        vcMember.setMemberId(memberId);
        if(!StringUtils.isBlank(editUser.getNickname())){
            vcMember.setNickname(editUser.getNickname());
        }
        if(!StringUtils.isBlank(editUser.getSex())){
            vcMember.setSex(Integer.parseInt(editUser.getSex()));
        }
        if(!StringUtils.isBlank(editUser.getMemberAdd())){
            vcMember.setMemberAdd(editUser.getMemberAdd());
        }
        if(!StringUtils.isBlank(editUser.getBirthday())){
            vcMember.setBirthday(editUser.getBirthday());
        }
        return   vcMember;
    }

    /**
     * @Description: 返回出去给用户
     * @param updateCount
     * @return com.hz.gather.master.core.protocol.response.user.ResponseEditUser
     * @author long
     * @date 2020/1/9 16:08
     */
     public static ResponseEditUser rsResponseEditUser(Integer  updateCount){
        ResponseEditUser  responseEditUser = new ResponseEditUser();
        if(updateCount==0){
            responseEditUser.setFlag(false);
        }else{
            responseEditUser.setFlag(true);
        }
        return responseEditUser;
    }

    /**
     * @Description: 设置查询用户支付信息条件
     * @param memberId
     * @return com.hz.gather.master.core.model.entity.VcMemberPay
     * @author long
     * @date 2020/1/9 21:32
     */
    public static VcMemberPay toVcMemberPay(Integer  memberId){
        VcMemberPay  vcMemberPay = new VcMemberPay();
        vcMemberPay.setMemberId(memberId);
        return vcMemberPay;
    }

    /**
     * @Description: TODO
     * @param memberId
    * @param payId
     * @return com.hz.gather.master.core.model.entity.VcMemberPay
     * @author long
     * @date 2020/1/9 22:57
     */
    public static VcMemberPay toVcMemberPay(Integer  memberId,String payId,String payName){
        VcMemberPay  vcMemberPay = new VcMemberPay();
        DateModel dateModel= PublicMethod.getDate();
        BeanUtils.copy(dateModel,vcMemberPay);
        vcMemberPay.setMemberId(memberId);
        vcMemberPay.setZfbPayid(payId);
        vcMemberPay.setZfbName(payName);
        return vcMemberPay;
    }

    /**
     * @Description: 获取当前系统时间
     * @param
     * @return com.pf.play.rule.core.model.DateModel
     * @author long
     * @date 2019/11/20 22:15
     */
    public  static DateModel getDate(){
        Date   date  = new Date();
        DateModel dateModel = new DateModel();
        dateModel.setCurday(DateUtil.getDayNumber(date));
        dateModel.setCurhour(DateUtil.getHour(date));
        dateModel.setCurminute(DateUtil.getCurminute(date));
        dateModel.setCreateTime(date);
        dateModel.setUpdateTime(date);
        return dateModel;
    }

    public static ResponeseAddZFBPay updateRs(Integer  updateCount){
        ResponeseAddZFBPay  responeseAddZFBPay = new ResponeseAddZFBPay();
        if(updateCount==0){
            responeseAddZFBPay.setFlag(false);
        }else{
            responeseAddZFBPay.setFlag(true);
        }
        return responeseAddZFBPay;
    }

    /**
     * @Description: 支付宝列表信息转换
     * @param list
     * @return com.hz.gather.master.core.protocol.response.pay.ResponeseHavaPayInfo
     * @author long
     * @date 2020/1/9 23:19
     */
    public static ResponeseHavaPayInfo toQueryHavaPayInfo(List<VcMemberPay>  list){
        ResponeseHavaPayInfo  responeseHavaPayInfo = new ResponeseHavaPayInfo();
        List<String>  payList = new ArrayList<>();
        for(VcMemberPay vcMemberPay:list){
            payList.add(vcMemberPay.getZfbPayid());
        }
        responeseHavaPayInfo.setPayList(payList);
        return responeseHavaPayInfo;
    }

    /**
     * @Description: check 是否成功
     * @param requestPayCashOut
     * @return java.lang.Integer
     * @author long
     * @date 2020/1/10 11:11
     */
    public static boolean isCheckCashOut(RequestPayCashOut requestPayCashOut){
        boolean  flag = false ;
        if(StringUtils.isBlank(requestPayCashOut.getToken())){
            return  flag;
        }
        if(requestPayCashOut.getMoney()>0){
            return  flag;
        }
        if(StringUtils.isBlank(requestPayCashOut.getAlPayId())){
            return  flag;
        }

        if(StringUtils.isBlank(requestPayCashOut.getPayPassword())){
            return  flag;
        }
        return true;
    }


    /**
     * 支付宝添加，必要信息是否为空
     * @param requestAddZFBPay
     * @return
     */
    public static boolean isCheckPayAdd(RequestAddZFBPay requestAddZFBPay){
        boolean  flag = false ;
        if(StringUtils.isBlank(requestAddZFBPay.getToken())){
            return  flag;
        }
        if(StringUtils.isBlank(requestAddZFBPay.getZfbPayId())){
            return  flag;
        }
        if(StringUtils.isBlank(requestAddZFBPay.getZfbName())){
            return  flag;
        }
        return true;
    }

    /**
     * @Description: 设置查询用户支付信息条件
     * @param memberId
     * @return com.hz.gather.master.core.model.entity.VcMemberPay
     * @author long
     * @date 2020/1/9 21:32
     */
    public static VcMemberPay queryVcMemberPay(Integer  memberId,String alipay){
        VcMemberPay  vcMemberPay = new VcMemberPay();
        vcMemberPay.setMemberId(memberId);
        vcMemberPay.setZfbPayid(alipay);
        return vcMemberPay;
    }


    /**
     * @Description: 用户提现表信息
     * @param memberId
    * @param aliPayNo
    * @param outTradeNo
    * @param money
     * @return com.hz.gather.master.core.model.entity.UCashOutLog
     * @author long
     * @date 2020/1/10 14:39
     */
    public static  UCashOutLog   toUCashOutLog(Integer  memberId,String aliPayNo,String aliName,String outTradeNo,Double money){
        UCashOutLog  uCashOutLog  = new  UCashOutLog();
        DateModel dateModel= PublicMethod.getDate();
        BeanUtils.copy(dateModel,uCashOutLog);
        uCashOutLog.setMemberId(memberId);
        uCashOutLog.setZfbName(aliName);
        uCashOutLog.setRemarks(Constant.PAY_REMARKS);
        uCashOutLog.setReceivaPayId(aliPayNo);
        uCashOutLog.setOutTradeNo(outTradeNo);
        uCashOutLog.setMoney(new BigDecimal(Double.toString(money)));
        return uCashOutLog;
    }


    /**
     * @Description: 用户提现表信息
     * @param memberId
    * @param aliPayNo
    * @param outTradeNo
    * @param money
     * @return com.hz.gather.master.core.model.entity.UCashOutLog
     * @author long
     * @date 2020/1/10 14:39
     */
    public static  UCashOutProcedLog   toUCashOutProcedLog(Integer  memberId,String aliPayNo,String outTradeNo,Double money){
        UCashOutProcedLog  uCashOutProcedLog  = new  UCashOutProcedLog();
        DateModel dateModel= PublicMethod.getDate();
        BeanUtils.copy(dateModel,uCashOutProcedLog);
        uCashOutProcedLog.setMemberId(memberId);
        uCashOutProcedLog.setReceivaPayId(aliPayNo);
        uCashOutProcedLog.setOutTradeNo(outTradeNo);
        uCashOutProcedLog.setProcedMoney(new BigDecimal(Double.toString(money)));
        return uCashOutProcedLog;
    }

    /**
     * @Description: 提现金额是否正常访问
     * @param money
     * @return boolean
     * @author long
     * @date 2020/1/10 16:26
     */
    public static  boolean cheakMoney(Double money){
        boolean  flag   = false;
        if(Constant.PAY_MIN_MOMNEY<=money&& money<=Constant.PAY_MAX_MOMNEY){
            flag=  true;
        }
        return flag;
    }


    /**
     * @Description: TODO
     * @param money
    * @param vcMemberResource
     * @return com.hz.gather.master.core.model.entity.VcMemberResource
     * @author long
     * @date 2020/1/10 17:37
     */
    public   static   VcMemberResource  updateVcMemberResource(double  money,VcMemberResource  vcMemberResource){
        VcMemberResource   vcMemberResource1 =  new  VcMemberResource();

        BigDecimal   moneyDec  =  new BigDecimal(Double.valueOf(money));
        BigDecimal   surplusMoney     =  null;//剩余金额 减去
        BigDecimal   cashMoney     =  null; //已兑现金额 增加

        surplusMoney = StringUtil.getBigDecimalSubtract(vcMemberResource.getSurplusMoney(),moneyDec);
        cashMoney  =StringUtil.getBigDecimalAdd(vcMemberResource.getCashMoney(),moneyDec);
        vcMemberResource1.setMemberId(vcMemberResource.getMemberId());
        vcMemberResource1.setSurplusMoney(surplusMoney);
        vcMemberResource1.setCashMoney(cashMoney);
        vcMemberResource1.setUpdateTime(new Date());

        return  vcMemberResource;
    }

    /**
     * @Description: 能领取裂变奖励id 计算方式
     * @param benefitMemberId  层级关系
     * @param fissionCount  裂变有效数
     * @return java.lang.String
     * @author long
     * @date 2020/1/11 13:40
     */
    public   static   String   getBenefitMemberId(String  benefitMemberId,Integer fissionCount){
        String   rsStr ="";
        String   []  str =  benefitMemberId.split(",");
        if(str.length<fissionCount){
            rsStr = benefitMemberId;
        }else{
            for(int  i=1;i<str.length+1;i++){
                if(i>fissionCount){
                    break;
                }
                if(i==1){
                    rsStr=str[str.length-i];
                }else{
                    rsStr=rsStr+","+str[str.length-i];
                }
            }
            String  []  orderStr =rsStr.split(",");
            rsStr="";
            for(int i=1;i<orderStr.length+1;i++){
                if(i==1){
                    rsStr=orderStr[orderStr.length-i];
                }else{
                    rsStr=rsStr+","+orderStr[orderStr.length-i];
                }
            }
        }
        return rsStr;
    }


    public  static  void   main(String [] args){
        String   dd ="3,4,5,6,7,8,9,10,11,1,2";
        System.out.println(PublicMethod.getBenefitMemberId(dd,9));
    }


    /***
     * memberId 设置VcMember
     * @param memberId
     * @return
     */
    public  static  VcMember  toPayPassword(Integer memberId,String  payPassword){
        VcMember  vcMember =  new  VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setPayPassword(payPassword);
        return vcMember;
    }



    /***
     * memberId 设置VcMember
     * @param memberId
     * @return
     */
    public  static  VcMember  toList(String  [] memberId){
        VcMember  vcMember =  new  VcMember();
        List<Integer>  list  = new ArrayList<>();
        for (int  i =0; i<memberId.length; i++){
            list.add(Integer.parseInt(memberId[i]));
        }
        vcMember.setIdList(list);
        return vcMember;
    }

    /**
     * @Description: 下级裂变人购买需要更新信息
     * @param memberId
    * @param money
    * @param type
     * @return com.hz.gather.master.core.model.entity.VcMemberResource
     * @author long
     * @date 2020/1/13 13:56
     */
    public static  VcMemberResource   toUqdateVcMemberResource(Integer  memberId,Double money,Integer  type ){
        VcMemberResource  vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setSurplusMoney(new BigDecimal(Double.valueOf(money)));
        if(type==1){
            vcMemberResource.setPushPeople(1);
        }else{
            vcMemberResource.setTeamActive(1);
        }
        return vcMemberResource;
    }





}
