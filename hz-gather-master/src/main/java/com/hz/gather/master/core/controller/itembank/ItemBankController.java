package com.hz.gather.master.core.controller.itembank;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.BeanUtils;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.SignUtil;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.CacheKey;
import com.hz.gather.master.core.common.utils.constant.CachedKeyUtils;
import com.hz.gather.master.core.common.utils.constant.ErrorCode;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.itembank.ItemBankAnswerModel;
import com.hz.gather.master.core.model.itembank.ItemBankModel;
import com.hz.gather.master.core.protocol.request.itembank.RequestItemBank;
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
import java.util.concurrent.TimeUnit;

/**
 * @Description 密保的Controller层
 * @Author yoko
 * @Date 2020/1/15 11:28
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/bk")
public class ItemBankController {
    private static Logger log = LoggerFactory.getLogger(ItemBankController.class);

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
     * @Description: 获取密保数据集合
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/bk/getDataList
     * 请求的属性类:RequestAppeal
     * 必填字段:{"agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg"}
     * 客户端加密字段:ctime+cctime+秘钥=sign
     * 服务端加密字段:stime+秘钥=sign
     *
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJia0xpc3QiOlt7ImlkIjoxLCJxdWVzdGlvbiI6IuaCqOeahOeItuS6sueahOWQjeWtl+aYr++8nyIsInNlYXQiOjF9LHsiaWQiOjIsInF1ZXN0aW9uIjoi5oKo55qE5q+N5Lqy55qE5ZCN5a2X5piv77yfIiwic2VhdCI6Mn0seyJpZCI6MywicXVlc3Rpb24iOiLmgqjnmoTlsI/lrabmr43moKHmmK/vvJ8iLCJzZWF0IjozfSx7ImlkIjo0LCJxdWVzdGlvbiI6IuaCqOeahOWIneS4reavjeagoeaYr++8nyIsInNlYXQiOjR9LHsiaWQiOjUsInF1ZXN0aW9uIjoi5oKo55qE6auY5Lit5q+N5qCh5piv77yfIiwic2VhdCI6NX0seyJpZCI6NiwicXVlc3Rpb24iOiLmgqjnmoTlpKflrabmr43moKHmmK/vvJ8iLCJzZWF0Ijo2fV0sInNpZ24iOiJiNDkzNGQyNDY2MDI4ODQ5NTllMTgyNjRiYjc0OGU0OSIsInN0aW1lIjoxNTc5MDY5NjQ0MTQzfQ=="
     *     },
     *     "sgid": "202001151427220000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getDataList", method = {RequestMethod.POST})
    public JsonResult<Object> getDataList(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        String data = "";

        RequestItemBank requestModel = new RequestItemBank();
        try{
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestItemBank.class);
            // 密保数据
            ItemBankModel itemBankQuery = BeanUtils.copy(requestModel, ItemBankModel.class);
            List<ItemBankModel> itemBankList = ComponentUtil.itemBankService.getItemBankList(itemBankQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, secretKeySign); // stime+秘钥=sign
            String strData = HodgepodgeMethod.assembleItemBankResult(stime, sign, itemBankList, null);
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
            log.error(String.format("this ItemBankController.getDataList() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 获取用户的密保数据集合
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/bk/getCustomerDataList
     * 请求的属性类:RequestAppeal
     * 必填字段:{"agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJia0xpc3QiOlt7ImlkIjoxLCJxdWVzdGlvbiI6IuaCqOeahOeItuS6sueahOWQjeWtl+aYr++8nyIsInNlYXQiOjF9LHsiaWQiOjIsInF1ZXN0aW9uIjoi5oKo55qE5q+N5Lqy55qE5ZCN5a2X5piv77yfIiwic2VhdCI6Mn0seyJpZCI6MywicXVlc3Rpb24iOiLmgqjnmoTlsI/lrabmr43moKHmmK/vvJ8iLCJzZWF0IjozfSx7ImlkIjo0LCJxdWVzdGlvbiI6IuaCqOeahOWIneS4reavjeagoeaYr++8nyIsInNlYXQiOjR9LHsiaWQiOjUsInF1ZXN0aW9uIjoi5oKo55qE6auY5Lit5q+N5qCh5piv77yfIiwic2VhdCI6NX0seyJpZCI6NiwicXVlc3Rpb24iOiLmgqjnmoTlpKflrabmr43moKHmmK/vvJ8iLCJzZWF0Ijo2fV0sInNpZ24iOiJhOTllNWI1YjVlOTA3ZGU4NTQ4YmY3NjYxNDVjYmNkZiIsInN0aW1lIjoxNTc5MDc1NDE1Mjc1fQ=="
     *     },
     *     "sgid": "202001151603330000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getCustomerDataList", method = {RequestMethod.POST})
    public JsonResult<Object> getCustomerDataList(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;

        RequestItemBank requestModel = new RequestItemBank();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "1");
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestItemBank.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = HodgepodgeMethod.checkCustomerData(requestModel);
            token = requestModel.getToken();
            // 用户密保数据
            ItemBankModel itemBankQuery = BeanUtils.copy(requestModel, ItemBankModel.class);
            itemBankQuery.setMemberId(memberId);
            List<ItemBankModel> itemBankList = ComponentUtil.itemBankService.getItemBankByCustomerList(itemBankQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = HodgepodgeMethod.assembleItemBankResult(stime, sign, itemBankList, null);
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
            log.error(String.format("this ItemBankController.getCustomerDataList() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 用户添加密保
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/bk/add
     * 请求的属性类:RequestAppeal
     * 必填字段:{"answerList":[{"itemBankId":1,"answer":"小一_4"},{"itemBankId":2,"answer":"小二_4"},{"itemBankId":3,"answer":"国小_4"},{"itemBankId":4,"answer":"国初_4"}],"agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJzaWduIjoiNzMxMDY3OGNmZjYxYjlhOGY1MTljNzY0YTU2YjUwMmYiLCJzdGltZSI6MTU3OTA3OTMxNzcwN30="
     *     },
     *     "sgid": "202001151708350000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public JsonResult<Object> add(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;

        RequestItemBank requestModel = new RequestItemBank();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "1");
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestItemBank.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = HodgepodgeMethod.checkAddAnswerData(requestModel);
            token = requestModel.getToken();
            // 组装要添加
            List<ItemBankAnswerModel> itemBankAnswerList = HodgepodgeMethod.assembleItemBankAnswerList(requestModel.answerList, memberId);
            // check组装的数据是否是有效数据
            HodgepodgeMethod.checkItemBankAnswerData(itemBankAnswerList);
            for (ItemBankAnswerModel addData : itemBankAnswerList){
                ComponentUtil.itemBankAnswerService.add(addData);
            }
            // 更新用户属性状态
            VcMember vcMemberModel = HodgepodgeMethod.assembleVcMember(memberId);
            ComponentUtil.userInfoService.updateMemberIsQuestions(vcMemberModel);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = HodgepodgeMethod.assembleAddItemBankResult(stime, sign);
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
            log.error(String.format("this ItemBankController.add() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }


    /**
     * @Description: 用户校验密保
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/bk/check
     * 请求的属性类:RequestAppeal
     * 必填字段:{"answerList":[{"itemBankId":1,"answer":"小一_4"},{"itemBankId":2,"answer":"小二_4"},{"itemBankId":3,"answer":"国小_4"},{"itemBankId":4,"answer":"国初_4"}],"agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJzaWduIjoiNzMxMDY3OGNmZjYxYjlhOGY1MTljNzY0YTU2YjUwMmYiLCJzdGltZSI6MTU3OTA3OTMxNzcwN30="
     *     },
     *     "sgid": "202001151708350000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/check", method = {RequestMethod.POST})
    public JsonResult<Object> check(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;

        RequestItemBank requestModel = new RequestItemBank();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "4");
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestItemBank.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = HodgepodgeMethod.checkAnswerData(requestModel);
            token = requestModel.getToken();
            // 组装要添加
            List<ItemBankAnswerModel> itemBankAnswerList = HodgepodgeMethod.assembleItemBankAnswerList(requestModel.answerList, memberId);
            // check组装的数据是否是有效数据
            HodgepodgeMethod.checkItemBankAnswerData(itemBankAnswerList);
            boolean flag = false;
            for (ItemBankAnswerModel addData : itemBankAnswerList){
                ItemBankAnswerModel itemBankAnswerModel = ComponentUtil.itemBankAnswerService.checkItemBankAnswer(addData);
                if (itemBankAnswerModel != null){
                    flag = true;
                    break;
                }
            }
            if (flag){
                // 组装返回客户端的数据
                long stime = System.currentTimeMillis();
                String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
                String strData = HodgepodgeMethod.assembleAddItemBankResult(stime, sign);
                // 数据加密
                String encryptionData = StringUtil.mergeCodeBase64(strData);
                ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
                resultDataModel.jsonData = encryptionData;
                // #添加流水
                // 返回数据给客户端
                String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.ITEM_BANK_ANSWER, memberId);
                ComponentUtil.redisService.set(strKeyCache, String.valueOf(memberId), FIVE_MIN);
                return JsonResult.successResult(resultDataModel, cgid, sgid);
            }else{
                throw new ServiceException(ErrorCode.ENUM_ERROR.I00010.geteCode(), ErrorCode.ENUM_ERROR.I00010.geteDesc());
            }

        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // #添加异常
            log.error(String.format("this ItemBankController.add() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }







}
