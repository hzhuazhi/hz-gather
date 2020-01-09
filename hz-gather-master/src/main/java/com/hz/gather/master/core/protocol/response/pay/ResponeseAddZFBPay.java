package com.hz.gather.master.core.protocol.response.pay;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 23:05
 * @Version 1.0
 */
public class ResponeseAddZFBPay {
    /**
     * 修改结果  修改失败 false、修改失败  ture
     */
    public  boolean   flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
