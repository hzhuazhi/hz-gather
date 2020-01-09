package com.hz.gather.master.core.protocol.request.user;

import com.hz.gather.master.core.protocol.page.BasePage;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 16:45
 * @Version 1.0
 */
public class RequestFundList extends BasePage {
    /***
     * token
     */
    private String   token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
