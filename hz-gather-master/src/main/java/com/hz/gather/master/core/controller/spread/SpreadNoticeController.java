package com.hz.gather.master.core.controller.spread;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.utils.*;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.itembank.ItemBankModel;
import com.hz.gather.master.core.model.question.QuestionDModel;
import com.hz.gather.master.core.model.spread.SpreadNoticeModel;
import com.hz.gather.master.core.protocol.request.itembank.RequestItemBank;
import com.hz.gather.master.core.protocol.request.question.RequestQuestion;
import com.hz.gather.master.core.protocol.request.spread.RequestSpreadNotice;
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
 * @Description ：系统通知，系统公告，传播、扩散的通知的Controller层
 * @Author yoko
 * @Date 2020/1/16 20:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/sd")
public class SpreadNoticeController {
    private static Logger log = LoggerFactory.getLogger(SpreadNoticeController.class);

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
     * @Description: 系统通知，系统公告，传播、扩散的通知的数据集合
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/sd/getDataList
     * 请求的属性类:RequestAppeal
     * 必填字段:{"agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg"}
     * 客户端加密字段:ctime+cctime+秘钥=sign
     * 服务端加密字段:stime+秘钥=sign
     *
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJzZExpc3QiOlt7ImNvbnRlbnQiOiLlhazlkYrlhoXlrrlfMSIsImljb25BZHMiOiJodHRwOi8vaW1nMS51dHVrdS5jaGluYS5jb20vNDAweDAvZW50LzIwMTcwNDA1L2IzZTVjMjRkLWQ5NmQtNGQwZi1iODgyLTU4ZGI5YTA1NzQwNy5qcGciLCJpZCI6MSwicGFnZUFkcyI6IiIsInNrZXRjaCI6IuWFrOWRiueugOS7i18xIiwidGl0bGUiOiLlhazlkYrmoIfpophfMSJ9LHsiY29udGVudCI6IiIsImljb25BZHMiOiJodHRwOi8vaW1nMS51dHVrdS5jaGluYS5jb20vNDAweDAvZW50LzIwMTcwNDA1L2IzZTVjMjRkLWQ5NmQtNGQwZi1iODgyLTU4ZGI5YTA1NzQwNy5qcGciLCJpZCI6MiwicGFnZUFkcyI6Imh0dHA6Ly9uLnNpbmFpbWcuY24vc2luYWNuMTUvMTE0L3c1NDVoMzY5LzIwMTgwNDE5LzJhZjAtZnppaG5lcDk2NjgzMDcucG5nIiwic2tldGNoIjoi5YWs5ZGK566A5LuLXzIiLCJ0aXRsZSI6IuWFrOWRiuagh+mimF8yIn0seyJjb250ZW50Ijoi5YWs5ZGK5YaF5a65XzMiLCJpY29uQWRzIjoiaHR0cDovL2ltZzEudXR1a3UuY2hpbmEuY29tLzQwMHgwL2VudC8yMDE3MDQwNS9iM2U1YzI0ZC1kOTZkLTRkMGYtYjg4Mi01OGRiOWEwNTc0MDcuanBnIiwiaWQiOjMsInBhZ2VBZHMiOiIiLCJza2V0Y2giOiLlhazlkYrnroDku4tfMyIsInRpdGxlIjoi5YWs5ZGK5qCH6aKYXzMifSx7ImNvbnRlbnQiOiIiLCJpY29uQWRzIjoiaHR0cDovL2ltZzEudXR1a3UuY2hpbmEuY29tLzQwMHgwL2VudC8yMDE3MDQwNS9iM2U1YzI0ZC1kOTZkLTRkMGYtYjg4Mi01OGRiOWEwNTc0MDcuanBnIiwiaWQiOjQsInBhZ2VBZHMiOiJodHRwOi8vbi5zaW5haW1nLmNuL3NpbmFjbjE1LzExNC93NTQ1aDM2OS8yMDE4MDQxOS8yYWYwLWZ6aWhuZXA5NjY4MzA3LnBuZyIsInNrZXRjaCI6IuWFrOWRiueugOS7i180IiwidGl0bGUiOiLlhazlkYrmoIfpophfNCJ9LHsiY29udGVudCI6IuWFrOWRiuWGheWuuV81IiwiaWNvbkFkcyI6Imh0dHA6Ly9pbWcxLnV0dWt1LmNoaW5hLmNvbS80MDB4MC9lbnQvMjAxNzA0MDUvYjNlNWMyNGQtZDk2ZC00ZDBmLWI4ODItNThkYjlhMDU3NDA3LmpwZyIsImlkIjo1LCJwYWdlQWRzIjoiIiwic2tldGNoIjoi5YWs5ZGK566A5LuLXzUiLCJ0aXRsZSI6IuWFrOWRiuagh+mimF81In0seyJjb250ZW50IjoiIiwiaWNvbkFkcyI6Imh0dHA6Ly9pbWcxLnV0dWt1LmNoaW5hLmNvbS80MDB4MC9lbnQvMjAxNzA0MDUvYjNlNWMyNGQtZDk2ZC00ZDBmLWI4ODItNThkYjlhMDU3NDA3LmpwZyIsImlkIjo2LCJwYWdlQWRzIjoiaHR0cDovL24uc2luYWltZy5jbi9zaW5hY24xNS8xMTQvdzU0NWgzNjkvMjAxODA0MTkvMmFmMC1memlobmVwOTY2ODMwNy5wbmciLCJza2V0Y2giOiLlhazlkYrnroDku4tfNiIsInRpdGxlIjoi5YWs5ZGK5qCH6aKYXzYifSx7ImNvbnRlbnQiOiLlhazlkYrlhoXlrrlfNyIsImljb25BZHMiOiJodHRwOi8vaW1nMS51dHVrdS5jaGluYS5jb20vNDAweDAvZW50LzIwMTcwNDA1L2IzZTVjMjRkLWQ5NmQtNGQwZi1iODgyLTU4ZGI5YTA1NzQwNy5qcGciLCJpZCI6NywicGFnZUFkcyI6IiIsInNrZXRjaCI6IuWFrOWRiueugOS7i183IiwidGl0bGUiOiLlhazlkYrmoIfpophfNyJ9LHsiY29udGVudCI6IiIsImljb25BZHMiOiJodHRwOi8vaW1nMS51dHVrdS5jaGluYS5jb20vNDAweDAvZW50LzIwMTcwNDA1L2IzZTVjMjRkLWQ5NmQtNGQwZi1iODgyLTU4ZGI5YTA1NzQwNy5qcGciLCJpZCI6OCwicGFnZUFkcyI6Imh0dHA6Ly9uLnNpbmFpbWcuY24vc2luYWNuMTUvMTE0L3c1NDVoMzY5LzIwMTgwNDE5LzJhZjAtZnppaG5lcDk2NjgzMDcucG5nIiwic2tldGNoIjoi5YWs5ZGK566A5LuLXzgiLCJ0aXRsZSI6IuWFrOWRiuagh+mimF84In0seyJjb250ZW50Ijoi5YWs5ZGK5YaF5a65XzkiLCJpY29uQWRzIjoiaHR0cDovL2ltZzEudXR1a3UuY2hpbmEuY29tLzQwMHgwL2VudC8yMDE3MDQwNS9iM2U1YzI0ZC1kOTZkLTRkMGYtYjg4Mi01OGRiOWEwNTc0MDcuanBnIiwiaWQiOjksInBhZ2VBZHMiOiIiLCJza2V0Y2giOiLlhazlkYrnroDku4tfOSIsInRpdGxlIjoi5YWs5ZGK5qCH6aKYXzkifSx7ImNvbnRlbnQiOiIiLCJpY29uQWRzIjoiaHR0cDovL2ltZzEudXR1a3UuY2hpbmEuY29tLzQwMHgwL2VudC8yMDE3MDQwNS9iM2U1YzI0ZC1kOTZkLTRkMGYtYjg4Mi01OGRiOWEwNTc0MDcuanBnIiwiaWQiOjEwLCJwYWdlQWRzIjoiaHR0cDovL24uc2luYWltZy5jbi9zaW5hY24xNS8xMTQvdzU0NWgzNjkvMjAxODA0MTkvMmFmMC1memlobmVwOTY2ODMwNy5wbmciLCJza2V0Y2giOiLlhazlkYrnroDku4tfMTAiLCJ0aXRsZSI6IuWFrOWRiuagh+mimF8xMCJ9XSwic2lnbiI6ImNlZDgxNzE4YWZkYjk3YzRlOTE2NmI3ZGQyNzA3ZDRlIiwic3RpbWUiOjE1NzkyMjc3NDcwNzB9"
     *     },
     *     "sgid": "202001171022250000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getDataList", method = {RequestMethod.POST})
    public JsonResult<Object> getDataList(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        String data = "";

        RequestSpreadNotice requestModel = new RequestSpreadNotice();
        try{
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestSpreadNotice.class);
            // 推广通知数据
            SpreadNoticeModel spreadNoticeQuery = BeanUtils.copy(requestModel, SpreadNoticeModel.class);
            spreadNoticeQuery.setNowTime(DateUtil.getNowPlusTime());
            List<SpreadNoticeModel> spreadNoticeList = ComponentUtil.spreadNoticeService.getSpreadNoticeList(spreadNoticeQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, secretKeySign); // stime+秘钥=sign
            String strData = HodgepodgeMethod.assembleSpreadNoticeResult(stime, sign, spreadNoticeList, null);
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
            log.error(String.format("this SpreadNoticeController.getDataList() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }


    /**
     * @Description: 系统通知，系统公告，传播、扩散的通知-详情数据
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/sd/getData
     * 请求的属性类:RequestAppeal
     * 必填字段:{"id":1,"agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg"}
     * 客户端加密字段:ctime+cctime+秘钥=sign
     * 服务端加密字段:stime+秘钥=sign
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJzZCI6eyJjb250ZW50Ijoi5YWs5ZGK5YaF5a65XzEiLCJpY29uQWRzIjoiaHR0cDovL2ltZzEudXR1a3UuY2hpbmEuY29tLzQwMHgwL2VudC8yMDE3MDQwNS9iM2U1YzI0ZC1kOTZkLTRkMGYtYjg4Mi01OGRiOWEwNTc0MDcuanBnIiwiaWQiOjEsInBhZ2VBZHMiOiIiLCJza2V0Y2giOiLlhazlkYrnroDku4tfMSIsInRpdGxlIjoi5YWs5ZGK5qCH6aKYXzEifSwic2lnbiI6IjM5ZTAwZGYyMmRlNzNhNzExYTJlNWQ0NWRmZWZjZmM4Iiwic3RpbWUiOjE1NzkyMjg1MDE0NjZ9"
     *     },
     *     "sgid": "202001171035010000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getData", method = {RequestMethod.POST})
    public JsonResult<Object> getData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        String data = "";

        RequestSpreadNotice requestModel = new RequestSpreadNotice();
        try{
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestSpreadNotice.class);
            // check校验请求的数据
            HodgepodgeMethod.checkSpreadNoticeData(requestModel);
            // 推广通知数据-详情
            SpreadNoticeModel spreadNoticeQuery = BeanUtils.copy(requestModel, SpreadNoticeModel.class);
            SpreadNoticeModel spreadNoticeModel = (SpreadNoticeModel) ComponentUtil.spreadNoticeService.getSpreadNotice(spreadNoticeQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, secretKeySign); // stime+秘钥=sign
            String strData = HodgepodgeMethod.assembleSpreadNoticeDataResult(stime, sign, spreadNoticeModel);
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
            log.error(String.format("this SpreadNoticeController.getData() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }
}
