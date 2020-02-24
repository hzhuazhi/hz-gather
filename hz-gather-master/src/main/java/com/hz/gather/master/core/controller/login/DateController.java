package com.hz.gather.master.core.controller.login;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.controller.notice.NoticeDController;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.protocol.request.data.RequestData;
import com.hz.gather.master.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/18 15:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/date")
public class DateController {
    private static Logger log = LoggerFactory.getLogger(NoticeDController.class);

    @RequestMapping(value = "/noticeInfo", method = {RequestMethod.POST})
    public JsonResult<Object> getDataList(HttpServletRequest request, HttpServletResponse response,@RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        String data = "";

        String strData = "";
        RequestData requestModel = new RequestData();
        try{
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestData.class);

            ComponentUtil.dateService.randomNotice(requestModel.getCount());

            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // #添加流水
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // #添加异常
            log.error(String.format("this NoticeController.getDataList() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }}
