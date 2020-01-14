package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.protocol.request.login.LoginModel;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/2 20:45
 * @Version 1.0
 */


public interface LoginService<T> extends BaseService<T>  {
    /**
     * 该电话号码是否有存在
     * @param phone
     * @return
     */
    public boolean     isPhoneExist(String phone);

    /**
     * 该电话号码是否有存在
     * @param phone
     */
    String      createTime(String phone,Integer type)throws  Exception;


    /**
     * check 验证码是否有效
     * @param time
     * @param phone
     * @return
     */
    public boolean    checkVerifCode(String time,String phone,String verifCode,Integer type)throws ServiceException;

    /**
     * 注册用户添加信息
     * @param loginModel
     * @return
     */
    public String    addMemberInfo(LoginModel loginModel)throws Exception;

    /**
     * 注册用户检查邀请码是否有效
     * @param inviteCode
     * @return
     */
    boolean    checkInviteCode(String   inviteCode)throws ServiceException;

    /**
     * 获取用户id
     * @return
     */
    public Integer getMemberId()throws  Exception;

    /**
     * 申请邀请码
     * @return
     * @throws Exception
     */
    public String createInviteCode()throws  Exception;

    /**
     * 添加操作数据
     * @return
     * @throws Exception
     */
    public void addUserInfo(LoginModel loginModel, Integer memberId, String[]   InviteAdd, VcMember vcMember,String phone)throws  Exception;

    /**
     * 给用户信息添加到缓存里面
     * @param loginModel
     * @param InviteAdd
     * @param memberId
     * @throws Exception
     */
    public void addRedis(LoginModel loginModel,String [] InviteAdd,Integer memberId)throws  Exception;

    /**
     * 根据电话号码查询用户id
     * @param phone
     * @return
     * @throws Exception
     */
    public Integer   getMemberId(String phone)throws  Exception;

    /**
     * 给token 和 memberId 绑定
     * @param token
     * @param memberId
     * @throws Exception
     */
    public  void    toPwTokenRedis(String token,Integer memberId)throws  Exception;

    /**
     * 修改密码信息
     * @param memberId
     * @param passWord
     * @throws Exception
     */
    public  Integer    updatePassWord(Integer  memberId,String passWord,String  token)throws  Exception;


    /**
     * 根据memberId 查询 token 信息
     * @param memberId
     * @return
     * @throws Exception
     */
    public VcMember queryVcMember(Integer   memberId)throws  Exception;

    /**
     * 删除token
     * @param delToken
     * @throws Exception
     */
    public  void   removeToken(String  delToken)throws  Exception;

    /**
     * 注册类发短信
     * @param phone
     * @throws Exception
     */
    public  String   sendRegister(String  phone,String areaCode)throws  Exception;

    /**
     * 忘记密码
     * @param phone
     * @throws Exception
     */
    public  String   sendForgetPassword(String  phone)throws  Exception;

    /**
     * 短信登陆发送短信
     * @param phone
     * @throws Exception
     */
    public  String   sendSmsSignIn(String  phone)throws  Exception;


    /**
     * 手机号 + 密码 登录
     * @param phone
     * @param password
     * @return
     * @throws Exception
     */
    public  String   passwordSignIn(String phone,String  password)throws  Exception;


    /**
     * 手机号 + 验证码 登录
     * @param phone
     * @param timeStamp
     * @return
     * @throws Exception
     */
    public  String   phoneSmsCodeSignIn(String phone,String  timeStamp,String smsCode)throws  Exception;



}
