package com.hz.gather.master.util;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.utils.DateUtil;
import com.hz.gather.master.core.model.dao.VcMember;
import com.hz.gather.master.core.model.dao.VcMemberResource;
import com.hz.gather.master.core.model.login.*;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

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
        SendSmsDto sendSmsDto = new  SendSmsDto();
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
        VcMemberResource   vcMemberResourceModel  =  new  VcMemberResource();
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
        ForgetPhoneDto  forgetPhoneDto= new ForgetPhoneDto();
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
}
