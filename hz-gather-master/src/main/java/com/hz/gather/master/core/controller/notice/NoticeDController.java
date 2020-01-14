package com.hz.gather.master.core.controller.notice;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.SignUtil;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.notice.NoticeModel;
import com.hz.gather.master.core.protocol.request.notice.RequestNotice;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.HodgepodgeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description 公告的Controller层
 * @Author yoko
 * @Date 2020/1/14 20:09
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/nc")
public class NoticeDController {
    private static Logger log = LoggerFactory.getLogger(NoticeDController.class);

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    /**
     * 15分钟.
     */
    public long FIFTEEN_MIN = 900;

    /**
     * 30分钟.
     */
    public long THIRTY_MIN = 30;

    @Value("${secret.key.token}")
    private String secretKeyToken;

    @Value("${secret.key.sign}")
    private String secretKeySign;


    /**
     * @Description: 获取百问百答类别数据
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/nc/getDataList
     * 请求的属性类:RequestAppeal
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","pageNumber":1,"pageSize":3}
     *
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJuY0xpc3QiOlt7ImRhdGFUeXBlIjo3LCJuaWNrbmFtZSI6IuaYteensF8xNiIsInJlY2VpdmVNb25leSI6IjE2MDAuMDAifSx7ImRhdGFUeXBlIjo2LCJuaWNrbmFtZSI6IuaYteensF8xNSIsInJlY2VpdmVNb25leSI6IjE1MDAuMDAifSx7ImRhdGFUeXBlIjo1LCJuaWNrbmFtZSI6IuaYteensF8xNCIsInJlY2VpdmVNb25leSI6IjE0MDAuMDAifV0sInJvd0NvdW50IjoxNiwic2lnbiI6ImVkNjFiZmI3ZGI5MWQ0YzY5MzRmMjBkNTZjYzI1ZjA4Iiwic3RpbWUiOjE1NzkwMDkwNDAwNjh9"
     *     },
     *     "sgid": "202001142137190000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getDataList", method = {RequestMethod.POST})
    public JsonResult<Object> getDataList(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        String data = "";

        RequestNotice requestModel = new RequestNotice();
        try{
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestNotice.class);
            // 公告数据
            NoticeModel noticeQuery = BeanUtils.copy(requestModel, NoticeModel.class);
            List<NoticeModel> noticeList = ComponentUtil.noticeService.queryByList(noticeQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, secretKeySign); // stime+秘钥=sign
            String strData = HodgepodgeMethod.assembleNoticeResult(stime, sign, noticeList, noticeQuery.getRowCount());
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
    }
}
