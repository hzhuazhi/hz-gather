package com.hz.gather.master.core.protocol.response.user;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 15:57
 * @Version 1.0
 */
public class ResponseEditUser {
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
