package com.hz.gather.master.util;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.DateUtil;
import com.hz.gather.master.core.common.utils.MD5Util;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.DateModel;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.model.notice.NoticeModel;
import com.hz.gather.master.core.model.user.CommonModel;
import com.hz.gather.master.core.model.user.FriendModel;
import com.hz.gather.master.core.protocol.request.login.*;
import com.hz.gather.master.core.protocol.request.pay.RequestAddZFBPay;
import com.hz.gather.master.core.protocol.request.pay.RequestPayCashOut;
import com.hz.gather.master.core.protocol.request.pay.RequestUpdateZFBPay;
import com.hz.gather.master.core.protocol.request.user.*;
import com.hz.gather.master.core.protocol.response.login.*;
import com.hz.gather.master.core.protocol.response.pay.ResponeseAddZFBPay;
import com.hz.gather.master.core.protocol.response.pay.ResponeseHavaPayInfo;
import com.hz.gather.master.core.protocol.response.pay.ResponesePayCashOut;
import com.hz.gather.master.core.protocol.response.user.*;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
     * @Description: 检查注册参数是否有效
     * @param loginModel
     * @return boolean
     * @author long
     * @date 2020/1/2 23:44
     */
    public  static boolean isCheakRegisterVerify(LoginModel loginModel){
        boolean  flag =  false;
        if(StringUtils.isBlank(loginModel.getInviteCode())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getPhone())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getSmsCode())){
            return  flag;
        }else if(StringUtils.isBlank(loginModel.getTimeStamp())){
            return  flag;
        }
        return   true;
    }


    public  static  ResponseRegisterVerify  toResponseRegisterVerify(boolean flag){
        ResponseRegisterVerify  responseRegisterVerify = new ResponseRegisterVerify();
        responseRegisterVerify.setFlag(flag);
        return responseRegisterVerify;
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
    public  static VcMember insertVcMember(Integer memberId, LoginModel loginModel,String [] SecureUUID,Integer superiorId,String extensionMemberId,String phone){
        VcMember  memberModel = new VcMember();
        Integer  createTime  =  Integer.parseInt(DateUtil.timeStamp());
        Integer  loginTime  =  createTime;
        memberModel.setMemberId(memberId);
        memberModel.setPhone(phone);
        String phoneNumber = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        memberModel.setNickname(phoneNumber);
        memberModel.setMemberAdd(Constant.MEMBERADD);
        memberModel.setMemberCode("C"+loginModel.getPhone());
        memberModel.setInviteCode(SecureUUID[0]);
        memberModel.setTradingAddress(SecureUUID[1]);
        memberModel.setCreateTime(createTime);
        memberModel.setLoginTime(loginTime);
        memberModel.setSuperiorId(superiorId);
        memberModel.setPassword(MD5Util.getMD5String(loginModel.getPassWrod()));
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

//        String []   rgs = extensionMemberId.split(",");
//        List<Integer>   idList = new ArrayList<>();
//        for(String str:rgs){
//            if(memberId!=Integer.parseInt(str)){
//                idList.add(Integer.parseInt(str));
//            }
//        }
        vcMemberResourceModel.setMemberId(memberId);
        vcMemberResourceModel.setPushPeopleAll(1);
        vcMemberResourceModel.setTeamActiveAll(1);
        vcMemberResourceModel.setUpdateTime(new Date());
        return   vcMemberResourceModel;
    }


    public  static VcMemberResource updateResourcePeopleAll(Integer memberId,String extensionMemberId){
        VcMemberResource   vcMemberResourceModel  =  new  VcMemberResource();
        String []   rgs = extensionMemberId.split(",");
        List<Integer>   idList = new ArrayList<>();
        for(String str:rgs){
            if(memberId!=Integer.parseInt(str)){
                idList.add(Integer.parseInt(str));
            }
        }
        vcMemberResourceModel.setIdList(idList);
        vcMemberResourceModel.setPeopleAll(1);
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
        }else if(sendSmsModel.getSmsType()==4){
            if (StringUtils.isBlank(sendSmsModel.getToken())){
                return flag;
            }
            if (StringUtils.isBlank(sendSmsModel.getPhone())){
                return flag;
            }
        }else{
            if (StringUtils.isBlank(sendSmsModel.getPhone())){
                return flag;
            }
        }

        return true;
    }


    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((19[0-9])|(13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
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
        if(StringUtils.isBlank(signInModel.getType()+"")){
            return flag;
        }

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

    public  static  VcMember  toVcMembersuperiorId(Integer superiorId){
        VcMember  vcMember =  new  VcMember();
        vcMember.setSuperiorId(superiorId);
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
        vcMember.setPassword(MD5Util.getMD5String(password));
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
//        vcMember.setLoginTime(new Date());
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


    public  static  VcMemberResource  updateVcMemberResource(Integer memberId,Double money){
        VcMemberResource  vcMemberResource =  new  VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setSurplusMoney(new BigDecimal(money));
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

    public  static boolean  cheakEequestFundList(RequestFundList requestFundList){
        boolean  flag  = false ;
        if(StringUtils.isBlank(requestFundList.getToken())){
            return  flag ;
        }
        return true;
    }

    /**
     * token 是否有效
     * @param requestCashRate
     * @return
     */
    public  static boolean  cheakRequestCashRate(RequestCashRate requestCashRate){
        boolean  flag  = false ;
        if(StringUtils.isBlank(requestCashRate.getToken())){
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
     * @Description: 根据memberId 查询现有批次号信息
     * @param memberId
     * @return com.hz.gather.master.core.model.entity.ULimitedTimeLog
     * @author long
     * @date 2020/1/8 17:03
     */
    public  static ULimitedTimeLog toULimitedTimeLog(Integer memberId){
        ULimitedTimeLog  uLimitedTimeLog =  new ULimitedTimeLog();
        uLimitedTimeLog.setMemberId(memberId);
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
     * @Description: 添加批次号直推明细表
     * @param memberId
    * @param batchNum
    * @param type
    * @param money
     * @return com.hz.gather.master.core.model.entity.UBatchLog
     * @author long
     * @date 2020/1/15 10:24
     */
    public  static UBatchLog  insertUBatchLog(Integer memberId,String batchNum,Integer type,Double  money){
        UBatchLog  uBatchLog =  new UBatchLog();
        uBatchLog.setBatchNum(batchNum);
        uBatchLog.setMemberId(memberId);
        uBatchLog.setBatchNum(batchNum);
        uBatchLog.setDataType(type);
        uBatchLog.setReceiveMoney(new BigDecimal(Double.valueOf(money)));
        return  uBatchLog;
    }

    /**
     * @Description: TODO
     * @param
     * @return com.hz.gather.master.core.protocol.response.user.ResponseUserInfo
     * @author long
     * @date 2020/1/8 17:51
     */
    public  static ResponseUserInfo   toResponseUserInfo(VcMember vcMember, VcMemberResource vcMemberResource, ULimitedTimeLog  limitedTimeLog,List<UBatchLog> list ,List<VcMember> listVcMember)throws  Exception{
        ResponseUserInfo responseUserInfo =  new ResponseUserInfo();
        responseUserInfo.setUserCaseMax(Constant.USERCASHMAX);
        responseUserInfo.setMemberAdd(vcMember.getMemberAdd());
        responseUserInfo.setNickname(vcMember.getNickname());
        responseUserInfo.setSex(vcMember.getSex()+"");
        responseUserInfo.setBirthday(vcMember.getBirthday());
        responseUserInfo.setPhone(vcMember.getPhone());
        responseUserInfo.setInviteCode("");
        responseUserInfo.setRq_code("");
        if(vcMember.getGradeType()!=0){
            responseUserInfo.setRq_code(Constant.REGISTERADD+"?inviteCode="+vcMember.getInviteCode());
            responseUserInfo.setInviteCode(vcMember.getInviteCode());
        }

        Long  time1=vcMember.getCreateTime()*1000L;
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time1);
        SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        responseUserInfo.setCreateTime(createTime);
        responseUserInfo.setVip_type(vcMember.getGradeType());
        responseUserInfo.setAlready_money(vcMemberResource.getAlreadyMoney()+"");
        responseUserInfo.setTotal_money(vcMemberResource.getTotalMoney()+"");
        responseUserInfo.setSurplus_money(vcMemberResource.getSurplusMoney()+"");
        responseUserInfo.setCash_money(vcMemberResource.getCashMoney()+"");
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

        List<String>  addList  =   new ArrayList<>();
        List<String>  pushAddList  =   new ArrayList<>();
        if (vcMember.getGradeType()==0){//普通用户信息
            responseUserInfo.setAddList(addList);
        }else if(vcMember.getGradeType()==1){//限时用户信息
            String recommendMoney = "0";

            int pushCount =0;
            int fissionCount =0;

            if(list!=null){

                if(null!=listVcMember){
                    for(VcMember vcMember1:listVcMember){
                        addList.add(vcMember1.getMemberAdd());
                        pushCount++;
                    }
                }


                for(UBatchLog uBatchLog:list){
                    if(uBatchLog.getDataType()==1){
                        recommendMoney = StringUtil.getBigDecimalAdd(recommendMoney,uBatchLog.getReceiveMoney()+"");
//                    recommendMoney=recommendMoney+uBatchLog.getReceiveMoney();
//                        addList.add(uBatchLog.getMemberAdd());
                    }else{
                        fissionCount++;
                    }
                }
            }
//            else if(vcMember.getGradeType()==2){
//
//            }

            responseUserInfo.setPush_count(pushCount+"");
            responseUserInfo.setAddList(addList);
          //  SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String           expireTime      =  DateUtil.format(limitedTimeLog.getInvalidTime(),sdfLongTimePlus);Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime);
            Long expireTimelong = parse.getTime();

            responseUserInfo.setRecommend_money(vcMemberResource.getPushPeople()*Constant.PUSH_PEOPLE_MONEY+"");
            responseUserInfo.setExpire_time(expireTimelong);
            responseUserInfo.setFission_money(limitedTimeLog.getFissionMoney()+"");
            responseUserInfo.setReality_push_count(Constant.FISSION_NUMBER+"");
            responseUserInfo.setRequire_fission_count(pushCount+"");


        }else if(vcMember.getGradeType()==2){
            responseUserInfo.setAddList(addList);
            responseUserInfo.setRecommend_money(vcMemberResource.getPushPeople()*Constant.PUSH_PEOPLE_MONEY+"");
            responseUserInfo.setFission_money(vcMemberResource.getTeamPeople()*Constant.EVERY_PEOPLE_MONEY+"");
            responseUserInfo.setReality_push_count(Constant.FISSION_NUMBER+"");
            responseUserInfo.setRequire_fission_count(vcMemberResource.getTeamPeople()+"");
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


    public  static  VcMember  toVcMemberSuperiorIdList(Integer superiorId){
        VcMember  vcMember =  new  VcMember();
        List<Integer> list = new ArrayList<>();
        list.add(superiorId);
        vcMember.setIdList(list);
        return vcMember;
    }

    public  static  List<Integer>   toVcMember(List<VcMember> list){
        List<Integer>  listrs = new ArrayList<>();
        if(list.size()==0){
            return  listrs;
        }

        for(VcMember vcMember1:list){
            listrs.add(vcMember1.getMemberId());
        }
        return listrs;
    }


    /**
     *
     * @param memberId
     * @param payPassword
     * @return
     */
    public  static  VcMember  toVcMemberPw(Integer memberId,String payPassword){
        VcMember  vcMember =  new  VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setPayPassword(MD5Util.getMD5String(payPassword));
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
    public  static ResponseMyFriend toVcMemberSuperiorId(VcMemberResource vcMemberResource, List<VcMember>   list,VcMember rsvcMember){
        ResponseMyFriend  reponseMyFriend =  new  ResponseMyFriend();
        reponseMyFriend.setPush_people_vip(vcMemberResource.getPushPeople()+"");
        reponseMyFriend.setTeam_active_vip(vcMemberResource.getPushPeople()+vcMemberResource.getTeamPeople()+"");
        reponseMyFriend.setTeam_active_all(vcMemberResource.getTeamActiveAll()-vcMemberResource.getTeamPeople()+"");
        reponseMyFriend.setPush_people_all(vcMemberResource.getPushPeopleAll()-vcMemberResource.getPushPeople()+"");
        List <Object>   rsList = new ArrayList<>();

        for(VcMember vcMember:list){
//            List<Integer>  list1 = PublicMethod.getMember(vcMemberResource.getMemberId(),vcMember.getBenefitMemberId());
//            vcMemberResourceM   .selectEightFissionPeople
            Integer   fissionPeople=0;
            FriendModel  friendModel = new FriendModel();
            friendModel.setNickname(vcMember.getNickname());
            friendModel.setNickadd(vcMember.getMemberAdd());

            if(rsvcMember.getGradeType()==2){
                fissionPeople = ComponentUtil.userInfoService.EightFissionPeople(vcMember.getMemberId());
                if(vcMember.getGradeType()==0){
                    friendModel.setMoney(0+"");
                }else{
                    friendModel.setMoney(Constant.PUSH_PEOPLE_MONEY+fissionPeople*Constant.EVERY_PEOPLE_MONEY+"");
                }
            }else{
                if(vcMember.getGradeType()==0){
                    friendModel.setMoney(0+"");
                }else{
                    friendModel.setMoney(Constant.PUSH_PEOPLE_MONEY+"");
                }
            }

//            friendModel.setMoney(Constant.PUSH_PEOPLE_MONEY+fissionPeople*Constant.EVERY_PEOPLE_MONEY+"");
            friendModel.setVip_type(vcMember.getGradeType()+"");
            friendModel.setCreate_time(vcMember.getCreateTime()+"");
            friendModel.setFission_people(fissionPeople+"");
            rsList.add(friendModel);
        }
        reponseMyFriend.setPush_people_list(rsList);
        return reponseMyFriend;
    }

    /***
     * 获取有效的下级信息
     * @param memberId
     * @param bennfitId
     * @return
     */
    public  static   List<Integer>  getMember(Integer memberId,String  bennfitId){
        List<Integer>  list = new ArrayList<>();
        String   [] str = bennfitId.split(",");
        for(String dd:str){
            if(memberId<Integer.parseInt(dd)){
                list.add(Integer.parseInt(dd));
            }
        }
        return list;
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
        List<RequestAddZFBPay>  payList = new ArrayList<>();
        for(VcMemberPay vcMemberPay:list){
            RequestAddZFBPay requestAddZFBPay =new  RequestAddZFBPay();
            requestAddZFBPay.setZfbName(vcMemberPay.getZfbName());
            requestAddZFBPay.setZfbPayId(vcMemberPay.getZfbPayid());
            payList.add(requestAddZFBPay);
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
        if(requestPayCashOut.getMoney()<1){
            return  flag;
        }
        if(StringUtils.isBlank(requestPayCashOut.getZfbPayId())){
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
     * 是否有效
     * @param requestUpdateZFBPay
     * @return
     */
    public static boolean isCheckPayUpdate(RequestUpdateZFBPay requestUpdateZFBPay){
        boolean  flag = false ;
        if(StringUtils.isBlank(requestUpdateZFBPay.getToken())){
            return  flag;
        }
        if(StringUtils.isBlank(requestUpdateZFBPay.getOldZfbPayId())){
            return  flag;
        }
        if(StringUtils.isBlank(requestUpdateZFBPay.getZfbPayId())){
            return  flag;
        }
        if(StringUtils.isBlank(requestUpdateZFBPay.getZfbName())){
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
    public static  UCashOutLog   toUCashOutLog(Integer  memberId,String aliPayNo,String aliName,String outTradeNo,Double money,Double realMoney){
        UCashOutLog  uCashOutLog  = new  UCashOutLog();
        DateModel dateModel= PublicMethod.getDate();
        BeanUtils.copy(dateModel,uCashOutLog);
        uCashOutLog.setMemberId(memberId);
        uCashOutLog.setZfbName(aliName);
        uCashOutLog.setRemarks(Constant.PAY_REMARKS);
        uCashOutLog.setReceivaPayId(aliPayNo);
        uCashOutLog.setOutTradeNo(outTradeNo);
        uCashOutLog.setRealMoney(new BigDecimal(realMoney));
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

        return  vcMemberResource1;
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
    public static  VcMemberResource   toUqdateVcMemberResourceVIP(Integer  memberId,Double money,Integer  type ){
        VcMemberResource  vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setTotalMoney(new BigDecimal(Double.valueOf(money)));
        vcMemberResource.setSurplusMoney(new BigDecimal(Double.valueOf(money)));
        if(type==1){
            vcMemberResource.setPushPeople(1);
        }else{
            vcMemberResource.setTeamPeople(1);
        }
        return vcMemberResource;
    }

    /**
     * 修改资源表里面的裂变人数
     * @param memberId
     * @return
     */
    public  static  VcMemberResource  updateFissionPeople(Integer memberId){
        VcMemberResource   vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setFissionPeopleNum(1);
        return  vcMemberResource;
    }


    /**
     * @Description: 修改等级状态
     * @param memberId
    * @param type
     * @return com.hz.gather.master.core.model.entity.VcMember
     * @author long
     * @date 2020/1/13 21:20
     */
    public  static  VcMember  updateGradeType(Integer memberId,Integer type){
        VcMember   vcMember = new VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setGradeType(type);
        return  vcMember;
    }


    /**
     * @Description: TODO
     * @param memberId   用户id
    * @param batchNum  批次号
     * @return com.hz.gather.master.core.model.entity.ULimitedTimeLog
     * @author long
     * @date 2020/1/13 21:45
     */
    public  static ULimitedTimeLog  insertULimitedTimeLog(Integer memberId,String batchNum){
        DateModel dateModel= PublicMethod.getDate();
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        BeanUtils.copy(dateModel,uLimitedTimeLog);

        uLimitedTimeLog.setInvalidTime(DateUtil.dateAddDays(dateModel.getCreateTime(),2));
        uLimitedTimeLog.setMemberId(memberId);
        uLimitedTimeLog.setBatchNum(batchNum);
        return uLimitedTimeLog;
    }

    /**
     * @Description: 设置批次号
     * @param batchNum
     * @return com.hz.gather.master.core.model.entity.ULimitedTimeLog
     * @author long
     * @date 2020/1/13 21:47
     */
    public  static ULimitedTimeLog  queryULimitedTimeLog(String batchNum){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setBatchNum(batchNum);
        return uLimitedTimeLog;
    }


    /**
     * 清除裂变人数
     * @param memberId
     * @return
     */
    public  static VcMemberResource  cleanFissinonPeople(Integer  memberId){
        VcMemberResource  vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setFissionPeopleNum(0);
        return vcMemberResource;
    }

    /**
     * @Description: 添加裂变奖励明细表
     * @param memberId
    * @param CreateMemberId
    * @param outTradeNo
    * @param receiveMoney
    * @param type
     * @return com.hz.gather.master.core.model.entity.UMoneyLog
     * @author long
     * @date 2020/1/14 10:35
     */
    public  static UMoneyLog  insertUMoneyLog(Integer memberId,Integer CreateMemberId,String outTradeNo,Double receiveMoney,Integer type){
        DateModel dateModel= PublicMethod.getDate();
        UMoneyLog  uMoneyLog = new UMoneyLog();
        BeanUtils.copy(dateModel,uMoneyLog);
        uMoneyLog.setMemberId(memberId);
        uMoneyLog.setRewardType(type);
        uMoneyLog.setOutTradeNo(outTradeNo);
        uMoneyLog.setReceiveMoney(new BigDecimal(Double.valueOf(receiveMoney)));
        uMoneyLog.setCreateMemberId(CreateMemberId);
        return uMoneyLog;
    }


    /**
     * @Description: 用户资金明细表
     * @param memberId   会员id
    * @param rewardType  类型 1 裂变奖励、2 提现金额
    * @param symbolType
    * @param money
     * @return com.hz.gather.master.core.model.entity.UMoneyList
     * @author long
     * @date 2020/1/14 11:38
     */
    public  static UMoneyList  insertUMoneyList(Integer memberId,Integer rewardType,Integer symbolType,Double money){
        DateModel dateModel= PublicMethod.getDate();
        UMoneyList  uMoneyList = new UMoneyList();
        BeanUtils.copy(dateModel,uMoneyList);
        uMoneyList.setMemberId(memberId);
        uMoneyList.setRewardType(rewardType);
        uMoneyList.setSymbolType(symbolType);
        uMoneyList.setMoney(new BigDecimal(Double.valueOf(money)));
        return uMoneyList;
    }


    public  static UMoneyList  insertUMoneyList(Integer memberId,Integer rewardType,Integer symbolType,Double money,String outTradeNo){
        DateModel dateModel= PublicMethod.getDate();
        UMoneyList  uMoneyList = new UMoneyList();
        BeanUtils.copy(dateModel,uMoneyList);
        uMoneyList.setMemberId(memberId);
        uMoneyList.setRewardType(rewardType);
        uMoneyList.setSymbolType(symbolType);
        uMoneyList.setOutTradeNo(outTradeNo);
        uMoneyList.setMoney(new BigDecimal(Double.valueOf(money)));
        return uMoneyList;
    }


    public  static UMoneyList  quertUMoneyList(String outTradeNo){
        UMoneyList  uMoneyList = new UMoneyList();
        uMoneyList.setOutTradeNo(outTradeNo);
        return uMoneyList;
    }

    public  static UMoneyList  insertUMoneyList(Integer memberId,Integer rewardType,Integer symbolType,BigDecimal money){
        DateModel dateModel= PublicMethod.getDate();
        UMoneyList  uMoneyList = new UMoneyList();
        BeanUtils.copy(dateModel,uMoneyList);
        uMoneyList.setMemberId(memberId);
        uMoneyList.setRewardType(rewardType);
        uMoneyList.setSymbolType(symbolType);
        uMoneyList.setMoney(money);
        return uMoneyList;
    }


    /**
     * 清除裂变人数
     * @param memberList
     * @return
     */
    public  static VcMember toMember(List<Integer>  memberList){
        VcMember  vcMember = new VcMember();
        vcMember.setIdList(memberList);
        return vcMember;
    }


    public  static ResponseFundList toResponseFundList(VcMemberResource vcMemberResource,List<Object> uMoneyList, Integer rowCount){
        ResponseFundList  responseFundList = new ResponseFundList();
        responseFundList.setTotal_money(vcMemberResource.getTotalMoney()+"");
        responseFundList.setSurplus_money(vcMemberResource.getSurplusMoney()+"");
        responseFundList.setCash_money(vcMemberResource.getCashMoney()+"");
        if (rowCount != null){
            responseFundList.setRowCount(rowCount);
        }

        responseFundList.setList(uMoneyList);
        return responseFundList;
    }

    public  static ResponeseCashRate toResponeseCashRate(List<CashRate> list){
        ResponeseCashRate  responeseCashRate = new ResponeseCashRate();
        responeseCashRate.setList(list);
        return responeseCashRate;
    }

    /**
     *
     * @param token
     * @return
     */
    public  static ResponesePayPassword toResponesePayPassword(String token){
        ResponesePayPassword  responesePayPassword = new ResponesePayPassword();
        responesePayPassword.setPwKey(token);
        return responesePayPassword;
    }


    public  static ResponseUpdatePayPw toResponesePayPassword(Integer count){
        ResponseUpdatePayPw  responseUpdatePayPw = new ResponseUpdatePayPw();
        if(count==0){
            responseUpdatePayPw.setFlag(false);
        }else{
            responseUpdatePayPw.setFlag(true);
        }
        return responseUpdatePayPw;
    }


    /**
     * @Description: TODO
     * @param uMoneyList
     * @return com.hz.gather.master.core.protocol.response.user.UMoneyLogResp
     * @author long
     * @date 2020/1/14 19:15
     */
    public  static UMoneyLogResp toUMoneyListResp(UMoneyList uMoneyList){
        UMoneyLogResp uMoneyLogResp = new UMoneyLogResp();
        if(uMoneyList.getRewardType()==1){
            uMoneyLogResp.setReward_type_value("裂变收益");
        }else if(uMoneyList.getRewardType()==2){
            uMoneyLogResp.setReward_type_value("提现成功");
        }else if(uMoneyList.getRewardType()==3){
            uMoneyLogResp.setReward_type_value("提现失败");
        }
        uMoneyLogResp.setSymbol_type_key(uMoneyList.getRewardType());

        if(uMoneyList.getRewardType()==1){
            uMoneyLogResp.setSymbol_type_value("收入");
        }else if(uMoneyList.getRewardType()==3){
            uMoneyLogResp.setSymbol_type_value("已返还");
            uMoneyLogResp.setSymbol_type_key(1);
        }else{
            uMoneyLogResp.setSymbol_type_value("支出");
        }
        uMoneyLogResp.setMoney(uMoneyList.getMoney()+"");

        SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        uMoneyLogResp.setCreate_time(sdfLongTimePlus.format(uMoneyList.getCreateTime()));
        return uMoneyLogResp;
    }


    /**
     * @Description: 不是vip 修改资源表信息
     * @param memberId
    * @param type
     * @return com.hz.gather.master.core.model.entity.VcMemberResource
     * @author long
     * @date 2020/1/14 21:49
     */
    public static  VcMemberResource   toUqdateVcMemberResourceNoVIP(Integer  memberId,Integer  type ,double money ){
        VcMemberResource  vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        if(type==1){
            vcMemberResource.setTotalMoney(new BigDecimal(Double.valueOf(money)));
            vcMemberResource.setSurplusMoney(new BigDecimal(Double.valueOf(money)));
            vcMemberResource.setPushPeople(1);
        }
        return vcMemberResource;
    }


    public static  VcMemberResource   toUqdateVcMemberResourceTeamPeople(Integer  memberId){
        VcMemberResource  vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setTeamPeople(1);
        return vcMemberResource;
    }


    public static  UCashOutLog   queryUCashOutLog(Integer  memberId){
        Date  date  =new Date();
        UCashOutLog  uCashOutLog = new UCashOutLog();
        uCashOutLog.setCurday(DateUtil.getDayNumber(date));
        uCashOutLog.setMemberId(memberId);
        return uCashOutLog;
    }


    /**
     * 直推修改的限时表
     * @param bacthNo
     * @param pushId
     * @param oldpushId
     * @return
     */
    public static  ULimitedTimeLog   uqdatePushTimeLog(String  bacthNo,String pushId,String oldpushId){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setBatchNum(bacthNo);
        uLimitedTimeLog.setPushNumber(1);
        if(StringUtils.isBlank(oldpushId)){
            uLimitedTimeLog.setPushId(pushId);
        }else{
            uLimitedTimeLog.setPushId(oldpushId+","+pushId);
        }
        return uLimitedTimeLog;
    }

    public static  List<Integer>   toMemberList(String pushId){
        List<Integer>   list = new ArrayList<>();
        if (StringUtils.isBlank(pushId)){
            return list;
        }

        String  [] pushIdList =  pushId.split(",");
        for (String pushid:pushIdList){
            list.add(Integer.parseInt(pushid));
        }
        return list;
    }

    public static  ULimitedTimeLog   uqdateULimitedTimeLog(String  bacthNo){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setBatchNum(bacthNo);
        uLimitedTimeLog.setFissionMoney(new BigDecimal(Double.valueOf(Constant.EVERY_PEOPLE_MONEY)));
        return uLimitedTimeLog;
    }

    public static  ULimitedTimeLog   uqdateULimitedTimeLogIsIsValid(Long  id){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setId(id);
        uLimitedTimeLog.setIsValid(2);
        return uLimitedTimeLog;
    }

    public static  ULimitedTimeLog   uqdateULimitedTimeLogAll(String  bacthNo){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setBatchNum(bacthNo);
        uLimitedTimeLog.setPushNumber(1);
        uLimitedTimeLog.setFissionMoney(new BigDecimal(Double.valueOf(Constant.EVERY_PEOPLE_MONEY)));
        return uLimitedTimeLog;
    }

    public  static Map<String,Integer> getPh(List<UBatchLog> list){
        Map<String,Integer>   map =  new HashMap<String,Integer>();
        Integer  pushPeople  =0;
        Integer  teamPeople  =0;
//        for(){
//
//        }
        return   map;
    }

    /**
     * 添加查询条件
     * @param alipay
     * @param memberId
     * @return
     */
    public static VcMemberPay queryVcMemberPay(String alipay,Integer memberId){
        VcMemberPay  vcMemberPay = new VcMemberPay();
        vcMemberPay.setZfbPayid(alipay);
        vcMemberPay.setMemberId(memberId);
        return vcMemberPay;
    }

    /**
     * 添加查询条件
     * @param id
     * @param alipay
     * @param name
     * @return
     */
    public static VcMemberPay updateVcMemberPay(long id,String alipay,String name){
        VcMemberPay  vcMemberPay = new VcMemberPay();
        vcMemberPay.setId(id);
        vcMemberPay.setZfbPayid(alipay);
        vcMemberPay.setZfbName(name);
        return vcMemberPay;
    }

    /**
     * 组装出去
     * @param flag
     * @return
     */
    public static ResponesePayCashOut  toResponesePayCashOut(boolean  flag){
        ResponesePayCashOut responesePayCashOut = new ResponesePayCashOut();
        responesePayCashOut.setFlag(flag);
        return responesePayCashOut;
    }


    /**
     *
     * @param uLimited
     * @return
     */
    public static VcMemberResource     toVcMemberResource(ULimitedTimeLog  uLimited,List<UBatchLog> list){
        VcMemberResource  vcMemberResource  = new VcMemberResource();
        vcMemberResource.setMemberId(uLimited.getMemberId());
        vcMemberResource.setTotalMoney(uLimited.getFissionMoney());
        vcMemberResource.setSurplusMoney(uLimited.getFissionMoney());
//        vcMemberResource.setTeamPeople(list.size());
        return vcMemberResource;
    }

    public static ULimitedTimeLog  updateFissionMoney(String batchNum,Double money){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setBatchNum(batchNum);
        uLimitedTimeLog.setFissionMoney(new BigDecimal(Double.valueOf(money)));
        return uLimitedTimeLog;
    }

    public static ULimitedTimeLog  updateTimeLogFinish(String batchNum){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setBatchNum(batchNum);
        uLimitedTimeLog.setIsFinish(2);
        return uLimitedTimeLog;
    }


    public static VcMember  updateVcMemberGradeType(Integer  memberId){
        VcMember  vcMember = new VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setGradeType(2);
        return vcMember;
    }


    public static boolean checkRequsetPayPassword(RequsetPayPassword forget){
        boolean  flag  = false ;
        if(StringUtils.isBlank(forget.getToken())){
            return false;
        }
        if(StringUtils.isBlank(forget.getSmsCode())){
            return false;
        }
        if(StringUtils.isBlank(forget.getTimeStamp())){
            return false;
        }
        return true;
    }


    public static boolean checkRequsetUqPayPw(RequsetUqPayPw requsetUqPayPw){
        boolean  flag  = false ;
        if(StringUtils.isBlank(requsetUqPayPw.getToken())){
            return false;
        }
        if(StringUtils.isBlank(requsetUqPayPw.getPwKey())){
            return false;
        }

        if(StringUtils.isBlank(requsetUqPayPw.getPayPw())){
            return false;
        }

        if(StringUtils.isBlank(requsetUqPayPw.getPayPw2())){
            return false;
        }
        return true;
    }

    public static boolean checkResponseFirstUqdatePayPw(ResponseFirstUqdatePayPw firstUqdatePayPw){
        boolean  flag  = false ;
        if(StringUtils.isBlank(firstUqdatePayPw.getToken())){
            return flag;
        }
        return true;
    }

    public  static SysNoticeInfo insertNoticeModel(Integer memberId,String nickname,Integer type,BigDecimal money){
        SysNoticeInfo  noticeModel =  new SysNoticeInfo();
        DateModel dateModel= PublicMethod.getDate();
        BeanUtils.copy(dateModel,noticeModel);
        noticeModel.setMemberId(memberId);
        noticeModel.setDataType(type);
        noticeModel.setNickname(nickname);
        noticeModel.setReceiveMoney(money);
        return  noticeModel;
    }


    /**
     * 生产用户领取奖励总额表信息
     * @param memberId
     * @return
     */
    public  static VcMemberRewardTotal  insertVcMemberRewardTotal(Integer memberId){
        DateModel dateModel= PublicMethod.getDate();
        VcMemberRewardTotal  vcMemberRewardTotal = new VcMemberRewardTotal();
        BeanUtils.copy(dateModel,vcMemberRewardTotal);
        vcMemberRewardTotal.setMemberId(memberId);
        return vcMemberRewardTotal;
    }


    /***
     * 根据date 组成查询条件
     * @param date
     * @return
     */
    public  static  ULimitedTimeLog   getULimitedTimeLog(Date date){
        ULimitedTimeLog  uLimitedTimeLog = new ULimitedTimeLog();
        uLimitedTimeLog.setInvalidTime(date);
        return uLimitedTimeLog;
    }


    /**
     * 修改状态
     * @param memberId
     * @param type
     * @param money
     * @return
     */
    public  static VcMemberRewardTotal  uqdateVcMemberRewardTotal(Integer memberId,Integer type,Double  money){
        VcMemberRewardTotal  vcMemberRewardTotal =  new VcMemberRewardTotal();
        vcMemberRewardTotal.setMemberId(memberId);
        vcMemberRewardTotal.setIsCount(type);
        vcMemberRewardTotal.setNotCountMoney(new BigDecimal(money));
        return  vcMemberRewardTotal;
    }

    /**
     * 修改用户信息
     * @param memberId
     * @param type
     * @param totalMoney
     * @return
     */
    public  static VcMemberRewardTotal  uqdateVcMemberRewardTotal(Integer memberId,Integer type,BigDecimal  totalMoney){
        VcMemberRewardTotal  vcMemberRewardTotal =  new VcMemberRewardTotal();
        vcMemberRewardTotal.setTotalMoney(totalMoney);
        vcMemberRewardTotal.setMemberId(memberId);
        vcMemberRewardTotal.setIsCount(type);
//        vcMemberRewardTotal.setNotCountMoney(new BigDecimal("0"));
        return  vcMemberRewardTotal;
    }


    public  static VcMemberRewardTotal  uqdateVcMemberRewardTotal(Integer memberId,Integer type){
        VcMemberRewardTotal  vcMemberRewardTotal =  new VcMemberRewardTotal();
        vcMemberRewardTotal.setMemberId(memberId);
        vcMemberRewardTotal.setIsCount(type);
        return  vcMemberRewardTotal;
    }


    public  static SysNoticeInfo  insertSysNoticeInfo(Integer memberId,Integer type,String nickName,BigDecimal  totalMoney){
        DateModel dateModel= PublicMethod.getDate();
        SysNoticeInfo  sysNoticeInfo  = new SysNoticeInfo();
        BeanUtils.copy(dateModel,sysNoticeInfo);
        sysNoticeInfo.setMemberId(memberId);
        sysNoticeInfo.setDataType(type+1);
        sysNoticeInfo.setNickname(nickName);
        sysNoticeInfo.setReceiveMoney(totalMoney);
        return  sysNoticeInfo;
    }


    /***
     * 修改用户的人数
     * @param memberId   自己不计算id
     * @param extensionMemberId   总的id
     * @param superiorId    上级id
     * @return
     */
    public  static List<VcMemberResource> updatePeopleInfo(Integer memberId,String extensionMemberId,Integer superiorId ){
        List<VcMemberResource>  list =    new ArrayList<>();
        String []   rgs = extensionMemberId.split(",");
        for(String str:rgs){
            if(memberId!=Integer.parseInt(str)){
                VcMemberResource   vcMemberResource =  new VcMemberResource();
                vcMemberResource.setMemberId(Integer.parseInt(str));
                if(Integer.parseInt(str)==superiorId){
                    vcMemberResource.setPushPeopleAll(1);
                }else{
                    vcMemberResource.setTeamActiveAll(1);
                }
                list.add(vcMemberResource);
            }
        }
        return   list;
    }


    /**
     * 查询信息
     * @param channel
     * @param channelNum
     * @param spreadValue
     * @return
     */
    public  static TbGaChannelRelationSpread toTbGaChannelRelationSpread(String channel,String channelNum,String spreadValue ){
        TbGaChannelRelationSpread  tbGaChannelRelationSpread = new TbGaChannelRelationSpread();
        tbGaChannelRelationSpread.setChannel(channel);
        tbGaChannelRelationSpread.setChannelNum(channelNum);
        tbGaChannelRelationSpread.setSpreadValue(spreadValue);
        return   tbGaChannelRelationSpread;
    }


    /**
     * @Description: TODO
     * @param spreadValue
     * @return com.hz.gather.master.core.model.entity.TbGaChannelRelationSpread
     * @author long
     * @date 2020/2/12 10:24
     */
    public  static TbGaChannelRelationSpread toTbGaChannelRelationSpread(String spreadValue ){
        TbGaChannelRelationSpread  tbGaChannelRelationSpread = new TbGaChannelRelationSpread();
        tbGaChannelRelationSpread.setSpreadValue(spreadValue);
        return   tbGaChannelRelationSpread;
    }


    /**
     * @Description: 转换为TbGaRecordNoRelation
     * @param memberId    用户id
    * @param clientType  终端类型 1、ios 2、android
    * @param channel     渠道码
    * @param channelNum  渠道号
    * @param spreadValue 推广码/推广包ID
     * @return com.hz.gather.master.core.model.entity.TbGaRecordNoRelation
     * @author long
     * @date 2020/2/12 14:07
     */
    public  static TbGaRecordNoRelation toTbGaRecordNoRelation(Long memberId,Integer clientType,String channel,String channelNum,String spreadValue ){
        TbGaRecordNoRelation  tbGaRecordNoRelation = new TbGaRecordNoRelation();
        DateModel    dateModel  =  PublicMethod.getDate();
        BeanUtils.copy(dateModel,tbGaRecordNoRelation);
        tbGaRecordNoRelation.setMemberId(memberId);
        tbGaRecordNoRelation.setClientType(clientType);
        tbGaRecordNoRelation.setChannel(channel);
        tbGaRecordNoRelation.setChannelNum(channelNum);
        tbGaRecordNoRelation.setSpreadValue(spreadValue);
        return   tbGaRecordNoRelation;
    }






}
