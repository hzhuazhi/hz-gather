package com.hz.gather.master.core.common.utils.constant;

/**
 * @author df
 * @Description:服务端常量
 * @create 2018-07-24 16:05
 **/
public class ServerConstant {

    /**
     * 公共，公用的常量
     */
    public final class PUBLIC_CONSTANT{
        /**
         *值是否等于0的判断条件
         */
        public static final int SIZE_VALUE_ZERO = 0;

        /**
         * 值是否等于1的判断条件
         */
        public static final int SIZE_VALUE_ONE = 1;

        /**
         * 值是否等于2的判断条件
         */
        public static final int SIZE_VALUE_TWO = 2;

        /**
         * 值是否等于3的判断条件
         */
        public static final int SIZE_VALUE_THREE = 3;

        /**
         * 字符串值等于1
         */
        public static final String STR_VALUE_ONE = "1";

        /**
         * token计算标识
         */
        public static final String TAG_HZ = "HZ";

        /**
         * 是否需要邮件提醒：1不需要邮件提醒，2需要邮件提醒
         * 这里2表示需要邮件提醒
         */
        public static final int MAIL_REMIND_YES = 2;

        /**
         * 钱包地址：前缀标签
         */
        public static final String CURRENCY_TAG = "gd";

        /**
         * 锁redis的key的前缀标签
         */
        public static final String REDIS_LOCK_TAG = "lock";


        /**
         * 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功，4扣量
         * 这里3表示成功
         */
        public static final int RUN_STATUS_THREE = 3;

        /**
         * 跑数据被执行了5次为上限
         */
        public static final int RUN_NUM_FIVE = 5;


    }






    /**
     * @Description: 服务的所有接口说明的枚举
     * @author yoko
     * @date 2019/12/10 9:54
     */
    public enum InterfaceEnum{
        ALI_SENDALI(1, "AlipayController.sendAli", "阿里支付宝：生成订单码-APP"),
        ALI_SENDALIH5(2, "AlipayController.sendAliH5", "阿里支付宝：生成订单码-H5页面"),

        BK_GETDATALIST(3, "ItemBankController.getDataList", "获取密保数据集合"),
        BK_GETCUSTOMERDATALIST(4, "ItemBankController.getCustomerDataList", "获取用户的密保数据集合"),
        BK_ADD(5, "ItemBankController.add", "用户添加密保"),
        BK_CHECK(6, "ItemBankController.check", "用户校验密保"),

        NC_GETDATALIST(7, "NoticeDController.getDataList", "获取公告数据"),


        QT_GETDATAMLIST(8, "QuestionController.getDataMList", "获取百问百答-类别数据"),
        QT_GETDATADLIST(9, "QuestionController.getDataDList", "获取百问百答-详情-集合数据"),
        QT_GETDATAD(10, "QuestionController.getDataD", "获取百问百答-详情数据"),

        SD_GETDATALIST(11, "SpreadNoticeController.getDataList", "系统通知，系统公告，传播、扩散的通知的数据集合"),
        SD_GETDATA(12, "SpreadNoticeController.getData", "系统通知，系统公告，传播、扩散的通知-详情数据"),

        UP_GETDATA(13, "UpgradeController.getData", "获取客户端版本更新数据"),


        ;
        /**
         * 类型
         */
        private int type;
        /**
         * 接口地址
         */
        private String ads;
        /**
         * 接口描述
         */
        private String desc;

        private InterfaceEnum(int type,String ads, String desc){
            this.type = type;
            this.ads = ads;
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAds() {
            return ads;
        }

        public void setAds(String ads) {
            this.ads = ads;
        }
    }






}
