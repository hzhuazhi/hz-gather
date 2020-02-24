package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.entity.VcMemberPay;
import com.hz.gather.master.core.protocol.request.pay.RequestPayCashOut;
import com.hz.gather.master.core.protocol.response.pay.ResponesePayCashOut;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/22 2:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/test")
public class Test {
    private static Logger log = LoggerFactory.getLogger(Test.class);
    @GetMapping("/test")
    public JsonResult<Object> test(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ComponentUtil.userInfoService.caseMoneyFail(1 ,  1D,"asdasdasd");
        //public JsonResult<Object> payCashOut(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData)throws Exception{
        return JsonResult.successResult(null);
    }
}
