package com.hz.gather.master.core.common.utils.constant;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 15:07
 * @Version 1.0
 */
public class Constant {
    public static  int   TOKEN = 1;
    public static  int   INVITE_CODE = 2;
    public static  int   TRADING_ADDRESS = 3;
    public static  int   MEMBERID = 4;

    public static  int   PW_TOKEN = 5;




    /***********注册验证码有效时间(单位分钟)*********/
    public  static  int  EFFECTIVE_IDENT_CODE_TIME =30;


    /*********** code 错误码 **********/
    public  static  int  CODE_ERROR_TYPE1    =  1 ; //显示给前端
    public  static  int  CODE_ERROR_TYPE2    =  2 ; //不显示显示给前端

    public  static  long  TOKEN_EXPIRE_S    =  1296000L ;


    /*************** 每个推荐用户分红金额****************/
    public  static  int   EVERY_PEOPLE_MONEY  =  50 ;


    /*************** 裂变数量****************/
    public  static  int   FISSION_NUMBER  =  3 ;

    /*************** 支付宝最大数量****************/
    public  static  int   PAY_MAX_NUMBER  =  3 ;

    /*************** 支付宝最大金额****************/
    public  static  Double   PAY_MAX_MOMNEY  =  500D ;
    /*************** 支付宝最小金额****************/
    public  static  Double   PAY_MIN_MOMNEY  =  1D ;

    /*************** 支付宝手续费****************/
    public  static  Double   SERVICE_MOMNEY  =  0.01;

    /*************** 提示文案****************/
    public  static  String   PAY_REMARKS  =  "《500理财》提现到账通知";

    /*************** 奖励裂变层级数****************/
    public  static  Integer   REWARD_FISSION_COUNT  =  9;


}
