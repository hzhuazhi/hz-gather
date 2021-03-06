package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.*;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.common.utils.constant.ErrorCode;
import com.hz.gather.master.core.mapper.VcMemberGenerateMapper;
import com.hz.gather.master.core.mapper.VcMemberMapper;
import com.hz.gather.master.core.mapper.VcMemberResourceMapper;
import com.hz.gather.master.core.mapper.VirtualCoinPriceMapper;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberGenerateModel;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import com.hz.gather.master.core.model.entity.VcMemberRewardTotal;
import com.hz.gather.master.core.model.price.VirtualCoinPriceModel;
import com.hz.gather.master.core.protocol.request.login.CommonModel;
import com.hz.gather.master.core.protocol.request.login.LoginModel;
import com.hz.gather.master.core.service.LoginService;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/2 20:47
 * @Version 1.0
 */
@Service
public class LoginServiceImpl<T> extends BaseServiceImpl<T> implements LoginService<T> {

    private  Long   PW_TOKEN_INVALID_TIME=18000L;
    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private VcMemberGenerateMapper vcMemberGenerateMapper;

    @Autowired
    private VirtualCoinPriceMapper virtualCoinPriceMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;

    @Override
    public BaseDao<T> getDao() {
        return vcMemberMapper;
    }

    @Override
    public boolean isPhoneExist(String phone) {
        boolean  flag =  true;
        //查询缓存里面是否有电话号码
        String      phoneRedis  =  (String)ComponentUtil.redisService.get(phone);
        if(!StringUtils.isBlank(phoneRedis)){
            return flag ;
        }

//        List<VirtualCoinPriceModel> virtualCoinPriceList = virtualCoinPriceMapper.findByCondition(new VirtualCoinPriceModel());
        VcMember    vcMember =  PublicMethod.queryPhoneVcMember(phone);
        VcMember   vcMember1 = vcMemberMapper.selectByPrimaryKey(vcMember);
        if(vcMember1!=null){
            return flag ;
        }
        return false;
    }

    @Override
    public String createTime(String phone,Integer type)throws  Exception {
        String  time = DateUtil.timeStamp()  ;
        String  amsVerification = "8888";//RandomUtil.getRandom(6);
        if (amsVerification.startsWith("0")){
            amsVerification = amsVerification.replaceFirst("0" , "1");
        }
        //SendSms.aliSendSms(phone,amsVerification);
        //缺少一个发送短信
        if(type==1){
            ComponentUtil.redisService.set(CacheKey.REGISTER_SMS+(phone+time),amsVerification, Constant.EFFECTIVE_IDENT_CODE_TIME, TimeUnit.MINUTES);
        }else if(type==2){
            ComponentUtil.redisService.set(CacheKey.FORGET_SMS+(phone+time),amsVerification, Constant.EFFECTIVE_IDENT_CODE_TIME, TimeUnit.MINUTES);
        }else if(type==3){
            ComponentUtil.redisService.set(CacheKey.SIGN_IN_SMS+(phone+time),amsVerification, Constant.EFFECTIVE_IDENT_CODE_TIME, TimeUnit.MINUTES);
        }else if(type==4){
            ComponentUtil.redisService.set(CacheKey.UPDATE_PAYPW_SMS+(phone+time),amsVerification, Constant.EFFECTIVE_IDENT_CODE_TIME, TimeUnit.MINUTES);
        }
        return time;
    }

    @Override
    public boolean checkVerifCode(String time, String phone,String verifCode,Integer type)throws ServiceException {
        // 查询缓存是否有验证码
        String    verifCodeRedis="";
        if(type==1){
            verifCodeRedis = (String)ComponentUtil.redisService.get(CacheKey.REGISTER_SMS+(phone+time));
        }else if(type==2){
            verifCodeRedis = (String)ComponentUtil.redisService.get(CacheKey.FORGET_SMS+(phone+time));
        }else if(type==3){
            verifCodeRedis = (String)ComponentUtil.redisService.get(CacheKey.SIGN_IN_SMS+(phone+time));
        }else if(type==4){
            verifCodeRedis = (String)ComponentUtil.redisService.get(CacheKey.UPDATE_PAYPW_SMS+(phone+time));
        }
        if (StringUtils.isBlank(verifCode)){
            throw  new ServiceException(ENUM_ERROR.A00008.geteCode(),ENUM_ERROR.A00008.geteDesc());
        }

        if(StringUtils.isBlank(verifCodeRedis)||StringUtils.isBlank(verifCode)){
             throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
        }
        //验证码是否一致
        if (!verifCodeRedis.equals(verifCode)){
            throw  new ServiceException(ENUM_ERROR.A00007.geteCode(),ENUM_ERROR.A00007.geteDesc());
        }
        return true;
    }

    @Override
    public String addMemberInfo(LoginModel loginModel) throws Exception {
//        ComponentUtil.redisService.get(loginModel);
        //获取新的memberId
        Integer   memberId  = ComponentUtil.loginService.getMemberId();
        String  strName =ComponentUtil.loginService.createInviteCode();
        String[]   InviteAdd  = strName.split(",");
        if(InviteAdd.length!=3){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.R000003.geteCode(),ErrorCode.ENUM_ERROR.R000003.geteDesc());
        }
        VcMember queryInviteCode=PublicMethod.queryInviteCode(loginModel.getInviteCode());
        VcMember   vcMember  =  vcMemberMapper.selectByPrimaryKey(queryInviteCode);
        addUserInfo(loginModel,memberId,InviteAdd,vcMember,loginModel.getPhone());
        addRedis(loginModel,InviteAdd,memberId);

//        ComponentUtil.redisService.set(InviteAdd[2]+"",memberId+"",Constant.TOKEN_EXPIRE_S);
//        CommonModel commonModel =  new  CommonModel();
//        BeanUtils.copy(loginModel,commonModel);
//        String   redisString = PublicMethod.toJson(commonModel);
//        ComponentUtil.redisService.set(memberId+"",redisString);

        return InviteAdd[2];
    }

    @Override
    public boolean checkInviteCode(String inviteCode) throws ServiceException {
        String strKey = CachedKeyUtils.getCacheKey(CacheKey.INVITE_INFO, inviteCode);
        String inviteCodeRedis =(String) ComponentUtil.redisService.get(strKey);
        //缓存里面是否有邀请码
        if(!StringUtils.isBlank(inviteCodeRedis)){
            return true;
        }

        VcMember    vcMember   =  PublicMethod.queryInviteCode(inviteCode);
        VcMember    vcMemberRs  = vcMemberMapper.selectByPrimaryKey(vcMember);
        if(vcMemberRs!=null){
            if(vcMemberRs.getGradeType()!=0){
                String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.INVITE_INFO, inviteCode);
                ComponentUtil.redisService.set(strKeyCache,vcMemberRs.getMemberId()+"");
                return true;
            }
        }


        return false;
    }

    @Override
    public Integer getMemberId()throws  Exception   {
        Integer             memberid =0;
        try{
            VcMemberGenerateModel vcMemberGenerateModel = new VcMemberGenerateModel();
            Integer  timestamp =Integer.parseInt(DateUtil.timeStamp());
            vcMemberGenerateModel.setCreateTime(timestamp);
            vcMemberGenerateModel.setUpdateTime(timestamp);
            vcMemberGenerateMapper.insertSelective(vcMemberGenerateModel);
            memberid= vcMemberGenerateModel.getId() ;
        }catch (Exception e){
            e.printStackTrace();
        }

        return memberid;
    }


    @Override
    public String createInviteCode()throws  Exception {
        //生成邀请码是否有重复的
        String inviteCode ="";
        String tradingAddress ="";
        String token = "" ;
        VcMember vcMember = new VcMember();
        List<VcMember> rsVcMember = new ArrayList<>();
        int i=0;
        while(true){
            token=ComponentUtil.generateService.getNonexistentInformation(Constant.TOKEN);
            inviteCode= ComponentUtil.generateService.getNonexistentInformation(Constant.INVITE_CODE) ;
            tradingAddress=ComponentUtil.generateService.getNonexistentInformation(Constant.TRADING_ADDRESS);
            vcMember.setInviteCode(inviteCode);
            vcMember.setTradingAddress(tradingAddress);
            vcMember.setToken(token);
            rsVcMember = vcMemberMapper.selectByCodeOrAddress(vcMember);
            if(rsVcMember.size()==0||i>3){
                break;
            }
            i++;
        }
        if(i>3){
            return "";
        }
        return inviteCode+","+tradingAddress+","+token;
    }

    @Override
    public void addUserInfo(LoginModel loginModel,Integer memberId,String[]  inviteAdd,VcMember vcMember,String phone) throws Exception {
        VcMember   vcMember1 =PublicMethod.insertVcMember(memberId,loginModel,inviteAdd,vcMember.getMemberId(),vcMember.getExtensionMemberId(),phone);
        VcMemberResource vcMemberResourceModel  =  PublicMethod.insertVcMemberResource(memberId);
        VcMemberResource updateResourcePeople  = PublicMethod.updateResourcePeopleAll(memberId,vcMember.getExtensionMemberId());

        List<VcMemberResource>  list =PublicMethod.updatePeopleInfo(memberId,vcMember.getExtensionMemberId(),vcMember.getMemberId());
        VcMemberRewardTotal vcMemberRewardTotal  =PublicMethod.insertVcMemberRewardTotal(memberId);
        ComponentUtil.transactionalService.userRegister(vcMember1,vcMemberResourceModel,updateResourcePeople,vcMemberRewardTotal,list);
    }


    /**
     * @Description: 给信息新注册的用添加到缓存里面
     * @param InviteAdd []
     * @return void
     * @author long
     * @date 2019/11/13 10:39
     */
    @Override
    public void addRedis(LoginModel loginModel,String [] InviteAdd,Integer memberId)throws  Exception{
        String phoneKeyCache = CachedKeyUtils.getCacheKey(CacheKey.PHONE_INFO, loginModel.getPhone());
//        String inviteCode= CachedKeyUtils.getCacheKey(CacheKey.TRADING_ADDRESS_INFO, InviteAdd[0]);
        String tradingAddress = CachedKeyUtils.getCacheKey(CacheKey.TRADING_ADDRESS_INFO, InviteAdd[1]);

        ComponentUtil.redisService.set(InviteAdd[2],memberId+"",Constant.TOKEN_EXPIRE_S);
        ComponentUtil.redisService.onlyData(phoneKeyCache, "1");
//        ComponentUtil.redisService.onlyData(inviteCode, "1");
        ComponentUtil.redisService.onlyData(tradingAddress, "1");
    }

    @Override
    public Integer getMemberId(String phone) throws Exception {
        Integer    memberId = 0;
        VcMember   vcMember = PublicMethod.toVcMember(phone);
        VcMember   rsVcmember = vcMemberMapper.selectByPrimaryKey(vcMember);
        if(rsVcmember!=null){
            memberId=rsVcmember.getMemberId();
        }
        return rsVcmember.getMemberId();
    }

    @Override
    public void toPwTokenRedis(String token, Integer memberId) throws Exception {
        ComponentUtil.redisService.set(token,memberId+"",PW_TOKEN_INVALID_TIME);
    }

    @Override
    public Integer updatePassWord(Integer memberId, String passWord,String  token) throws Exception {
        VcMember  vcMember = new VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setPassword(MD5Util.getMD5String(passWord));
        vcMember.setToken(token);
        Integer  count  = vcMemberMapper.updateByPrimaryKeySelective(vcMember);
        return count;
    }

    @Override
    public VcMember queryVcMember(Integer memberId) throws Exception {
        VcMember  vcMember = PublicMethod.toVcMember(memberId);
        VcMember  vcMember1 =vcMemberMapper.selectByPrimaryKey(vcMember);
        return vcMember1;
    }

    @Override
    public void removeToken(String delToken) throws Exception {
        ComponentUtil.redisService.remove(delToken);
    }

    @Override
    public String sendRegister(String phone, String areaCode) throws Exception {
        boolean flag = ComponentUtil.loginService.isPhoneExist(phone);
        if(flag){
            throw  new ServiceException(ENUM_ERROR.A00001.geteCode(),ENUM_ERROR.A00001.geteDesc());
        }
        String   time  = ComponentUtil.loginService.createTime(phone,1);
        return   time;
    }

    @Override
    public String sendForgetPassword(String phone) throws Exception {
        boolean flag = ComponentUtil.loginService.isPhoneExist(phone);
        if(!flag){
            throw  new ServiceException(ENUM_ERROR.A00009.geteCode(),ENUM_ERROR.A00009.geteDesc());
        }
        String  time  = ComponentUtil.loginService.createTime(phone,2);
        return time;
    }

    @Override
    public String sendSmsSignIn(String phone) throws Exception {
        boolean flag = ComponentUtil.loginService.isPhoneExist(phone);
        if(!flag){
            throw  new ServiceException(ENUM_ERROR.A00009.geteCode(),ENUM_ERROR.A00009.geteDesc());
        }
        String  time  = ComponentUtil.loginService.createTime(phone,3);
        return time;
    }

    @Override
    public String sendSmsPayPassword(String phone) throws Exception {
        boolean flag = ComponentUtil.loginService.isPhoneExist(phone);
        if(!flag){
            throw  new ServiceException(ENUM_ERROR.A00009.geteCode(),ENUM_ERROR.A00009.geteDesc());
        }
        String  time  = ComponentUtil.loginService.createTime(phone,4);
        return time;
    }

    @Override
    public String passwordSignIn(String phone, String password) throws Exception {
        VcMember   vcMember = PublicMethod.toVcMember(phone,password);
        VcMember   rsVcMember=vcMemberMapper.selectByPrimaryKey(vcMember);
        if(rsVcMember==null){
            return null;
        }
        String  token  = UUIDUtils.createUUID();
        VcMember   vcMemberUqdate  = PublicMethod.toVcMember(rsVcMember.getMemberId(),token);
        vcMemberMapper.updateByPrimaryKeySelective(vcMemberUqdate);
        ComponentUtil.redisService.set(token,rsVcMember.getMemberId()+"");
        return token;
    }

    @Override
    public String phoneSmsCodeSignIn(String phone, String timeStamp,String smsCode) throws Exception {
        String  key = CacheKey.SIGN_IN_SMS+(phone+timeStamp);
        String  code =(String)ComponentUtil.redisService.get(key);
        if (StringUtils.isBlank(code)){
            return "1";
        }
        if(!code.equals(smsCode)){
            return "2";
        }


        VcMember   vcMember = PublicMethod.toVcMember(phone);
        VcMember   rsVcMember=vcMemberMapper.selectByPrimaryKey(vcMember);
        if(rsVcMember==null){
            return null;
        }
        String  token  = UUIDUtils.createUUID();
        VcMember vcMemberUqdate  = PublicMethod.toVcMember(rsVcMember.getMemberId(),token);
        vcMemberMapper.updateByPrimaryKeySelective(vcMemberUqdate);
        ComponentUtil.redisService.set(token,rsVcMember.getMemberId()+"");
        return token;
    }


    @Override
    public void getPayPwToken(Integer memberId, String token) throws Exception {
        ComponentUtil.redisService.set(token,memberId+"",PW_TOKEN_INVALID_TIME);
    }
}
