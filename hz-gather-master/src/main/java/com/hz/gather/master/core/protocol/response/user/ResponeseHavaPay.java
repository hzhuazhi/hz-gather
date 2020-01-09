package com.hz.gather.master.core.protocol.response.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 21:20
 * @Version 1.0
 */
public class ResponeseHavaPay {
    /**
     * 是否有支付 1 、有支付  2 是还没设置支付
     */
    private  boolean  flag ;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
