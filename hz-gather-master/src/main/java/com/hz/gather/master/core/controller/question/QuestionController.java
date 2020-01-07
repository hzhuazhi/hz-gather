package com.hz.gather.master.core.controller.question;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description 百问百答的Controller层
 * @Author yoko
 * @Date 2020/1/7 11:49
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/qt")
public class QuestionController {
    private static Logger log = LoggerFactory.getLogger(QuestionController.class);

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
     * @Description: 获取我的申诉（主动发起的申诉）-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/al/getActiveData
     * 请求的属性类:RequestAppeal
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJhTGlzdCI6W3siYXBwZWFsRGVzY3JpYmUiOiLnlLPor4nljp/lm6BfMSIsImFwcGVhbFJlcGxlbmlzaCI6IueUs+ivieihpeWFhV8xIiwiYXBwZWFsUmVzdWx0IjowLCJidXlOaWNrbmFtZSI6IuWwj+mjnum+mTEiLCJjcmVhdGVUaW1lIjoiMjAxOS0xMi0wNSAxNToyNTo0NCIsImlkIjoxLCJpZGVudGl0eVR5cGUiOjEsIm9yZGVyTm8iOiJvcmRlcl9ub18xIiwib3JkZXJUcmFkZVRpbWUiOiIyMDE5LTEyLTA0IDEwOjI1OjM2IiwicmVmdXRlRGVzY3JpYmUiOiLlj43pqbPljp/lm6BfMSIsInJlZnV0ZVJlcGxlbmlzaCI6IuWPjemps+ihpeWFhV8xIiwic2VsbE5pY2tuYW1lIjoi5bCP6aOe6b6ZMyIsInNlcnZpY2VDaGFyZ2UiOiIzLjMiLCJ0b3RhbFByaWNlIjoiMTIuMSIsInRyYWRlTnVtIjoiMTEiLCJ0cmFkZVByaWNlIjoiMS4xIn0seyJhcHBlYWxEZXNjcmliZSI6IueUs+ivieWOn+WboF8yIiwiYXBwZWFsUmVwbGVuaXNoIjoi55Sz6K+J6KGl5YWFXzIiLCJhcHBlYWxSZXN1bHQiOjAsImJ1eU5pY2tuYW1lIjoi5Lmw5a62X+aYteensF8zIiwiaWQiOjIsImlkZW50aXR5VHlwZSI6Miwib3JkZXJObyI6Im9yZGVyX25vXzIiLCJvcmRlclRyYWRlVGltZSI6IjIwMTktMTEtMjcgMjE6NTM6MDIiLCJyZWZ1dGVEZXNjcmliZSI6IiIsInJlZnV0ZVJlcGxlbmlzaCI6IiIsInNlbGxOaWNrbmFtZSI6IuWNluWutl/mmLXnp7BfNCIsInNlcnZpY2VDaGFyZ2UiOiIxLjUiLCJ0b3RhbFByaWNlIjoiIiwidHJhZGVOdW0iOiIzIiwidHJhZGVQcmljZSI6IjMifSx7ImFwcGVhbERlc2NyaWJlIjoi55Sz6K+J5Y6f5ZugXzQiLCJhcHBlYWxSZXBsZW5pc2giOiLnlLPor4nooaXlhYVfNCIsImFwcGVhbFJlc3VsdCI6MCwiYnV5Tmlja25hbWUiOiLkubDlrrZf5pi156ewXzciLCJpZCI6NCwiaWRlbnRpdHlUeXBlIjoyLCJvcmRlck5vIjoib3JkZXJfbm9fNCIsIm9yZGVyVHJhZGVUaW1lIjoiMjAxOS0xMS0yNyAyMTo1MzowMiIsInJlZnV0ZURlc2NyaWJlIjoiIiwicmVmdXRlUmVwbGVuaXNoIjoiIiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X+aYteensF84Iiwic2VydmljZUNoYXJnZSI6IjIuNSIsInRvdGFsUHJpY2UiOiIiLCJ0cmFkZU51bSI6IjUiLCJ0cmFkZVByaWNlIjoiNSJ9XSwicm93Q291bnQiOjEwLCJzaWduIjoiZGZmZmZjNTI2NzI5NmIwZjVjYTE3ZWI0MmY3OTZiZjciLCJzdGltZSI6MTU3NTU1MTU3OTMyNywidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912052111280000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getDataM", method = {RequestMethod.POST})
    public JsonResult<Object> getActiveData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;

//        RequestAppeal requestAppeal = new RequestAppeal();
        try{
//            // 解密
//            data = StringUtil.decoderBase64(jsonData);
//            requestAppeal  = JSON.parseObject(data, RequestAppeal.class);
//            // check校验数据、校验用户是否登录、获得用户ID
//            memberId = PublicMethod.checkActiveData(requestAppeal);
//            token = requestAppeal.getToken();
//            // 校验ctime
//            // 校验sign
//
//            // 申诉数据
//            AppealModel appealQuery = PublicMethod.assembleAppealQuery(requestAppeal, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
//            List<AppealModel> appealList = ComponentUtil.appealService.queryByList(appealQuery);
//            // 组装返回客户端的数据
//            long stime = System.currentTimeMillis();
//            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
//            String strData = PublicMethod.assembleAppealResult(stime, token, sign, appealList, appealQuery.getRowCount());
//            // 数据加密
//            String encryptionData = StringUtil.mergeCodeBase64(strData);
//            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
//            resultDataModel.jsonData = encryptionData;
//            // 用户注册完毕则直接让用户处于登录状态
////            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
//            // #添加流水
            // 返回数据给客户端
            return JsonResult.successResult(null, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // #添加异常
            log.error(String.format("this QuestionController.getActiveData() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }
}
