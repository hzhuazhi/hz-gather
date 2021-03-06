package com.hz.gather.master.core.controller.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.hz.gather.master.core.common.alipay.Alipay;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.SignUtil;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.alipay.AlipayH5Model;
import com.hz.gather.master.core.model.alipay.AlipayModel;
import com.hz.gather.master.core.model.alipay.AlipayNotifyModel;
import com.hz.gather.master.core.model.pay.PayCustModel;
import com.hz.gather.master.core.model.region.RegionModel;
import com.hz.gather.master.core.model.stream.ConsumerChannelModel;
import com.hz.gather.master.core.model.stream.StreamConsumerModel;
import com.hz.gather.master.core.protocol.request.RequestAlipay;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.HodgepodgeMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description 阿里支付的Controller层
 * @Author yoko
 * @Date 2019/12/19 19:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/ali")
public class AlipayController {
    private static Logger log = LoggerFactory.getLogger(AlipayController.class);

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

    @Value("${alipay.notify.url}")
    private String alipayNotifyUrl;


    /**
     * @Description: 阿里支付宝：生成订单码-APP
     * <p>
     *     把调用阿里支付宝的类生成的订单码返回给客户端
     * </p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/ali/sendAli
     * 请求的属性类:RequestAlipay
     * 必填字段:{"memberId":1,"body":"1","subject":"subject1","outTradeNo":"outTradeNo1","totalAmount":"0.01","productCode":"productCode1","agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","androidVer":"7.1.2","channel":"channel_1","channelNum":"channelNum_1","spreadValue":"spreadValue_1"}
     * 客户端加密字段:ctime+cctime+totalAmount+memberId/token+秘钥=sign
     * 服务端加密字段:aliOrder+stime+token+秘钥=sign
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJhbGlPcmRlciI6ImFsaXBheV9zZGs9YWxpcGF5LXNkay1qYXZhLTQuOC43My5BTEwmYXBwX2lkPTIwMTgwMzE1MDIzNzY5MDMmYml6X2NvbnRlbnQ9JTdCJTIyYm9keSUyMiUzQSUyMiVFOCVCNCVCOSVFNyU5NCVBOCVFNyVCQyVCNCVFNyVCQSVCMyUyMiUyQyUyMm91dF90cmFkZV9ubyUyMiUzQSUyMjIwMjAwMTE2MTEyMjQzMDAwMDAwMSUyMiUyQyUyMnByb2R1Y3RfY29kZSUyMiUzQSUyMjUwMF9IWSUyMiUyQyUyMnN1YmplY3QlMjIlM0ElMjI1MDAlRTglQjQlQjklRTclOTQlQTglMjIlMkMlMjJ0aW1lb3V0X2V4cHJlc3MlMjIlM0ElMjIzMG0lMjIlMkMlMjJ0b3RhbF9hbW91bnQlMjIlM0ElMjI1LjAlMjIlN0QmY2hhcnNldD1VVEYtOCZmb3JtYXQ9anNvbiZtZXRob2Q9YWxpcGF5LnRyYWRlLmFwcC5wYXkmbm90aWZ5X3VybD1odHRwJTNBJTJGJTJGMTE0LjU1LjY3LjE2NyUzQTgwODIlMkZwbGF5JTJGYWxpJTJGbm90aWZ5JnNpZ249TWZPR2pYNkN5Y0ltJTJCWWZDJTJCZDJaaWhxMXVDcGZqdmI1empYJTJGS0hrU1M3QjFDRXVyUVlLaHQ3c1hlJTJCZDY2ZDIwZnhjY3daTWQzT0dDTnhGd3ZmbFgydHNaTk5Bc2ZpJTJCSzFCclhscFElMkZmM1hOSUIzT2lEQ2JTU012U1RjVEg4N25GR2NFaFpUMkRadSUyRjZkYlFtOCUyQnlzSmlSbSUyRkYlMkZYWHJWWmVTTWtPb1lLQyUyQjhTanlHUVhtTUtUYnhIRiUyQnJKNExpUiUyQm5TUEI5MEo5cXhBNUtOSzhoZTJ3aWdteWtGMlYlMkYwM3JrSm5ySlFhMUtvbko0dGJCelpHNjU1a1liYVdDbFhmb3l6UzRSQ091MGtFNDYyNjZmUG9TNGwwbFNLREpaOUdJRXRMcEJuYlhpMGExekdBNWpSVHE1Z05BV2U1eGFYZ214alZFbWVTVXZGS2l4WSUyQllDMk93JTNEJTNEJnNpZ25fdHlwZT1SU0EyJnRpbWVzdGFtcD0yMDIwLTAxLTE2KzExJTNBMjIlM0E1NSZ2ZXJzaW9uPTEuMCIsInN0aW1lIjoxNTc5MTQ0OTgyNTMwLCJ0b2tlbiI6ImM4M2I0YjZhNmVhOGM5Y2VkODgyZjYxOGQ2MDY4Nzc1In0="
     *     },
     *     "sgid": "202001161122430000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/sendAli", method = {RequestMethod.POST})
    public JsonResult<Object> sendAli(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();

        RequestAlipay requestModel = new RequestAlipay();
        try{
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestAlipay.class);
            if (requestModel.memberId != null){
                memberId = requestModel.memberId;
            }else{
                 // check校验数据、校验用户是否登录、获得用户ID
                memberId = HodgepodgeMethod.checkAlipayData(requestModel);
                requestModel.memberId = memberId;
                token = requestModel.getToken();
            }

            // check用户是否支付过于频繁
            HodgepodgeMethod.checkAliPayMember(memberId);

            // check用户是否已经支付过
            PayCustModel payCustQuery = HodgepodgeMethod.assemblePayCustQuery(memberId);
            PayCustModel payCustModel = (PayCustModel) ComponentUtil.payCustService.findByObject(payCustQuery);
            HodgepodgeMethod.checkIsPayCust(payCustModel);
            // 校验ctime
            // 校验sign
            String totalAmount = ComponentUtil.loadConstant.totalAmount;
            requestModel.totalAmount = totalAmount;
            // 调用阿里云支付宝生成订单
            AlipayModel alipayModel = HodgepodgeMethod.assembleAlipayData(requestModel, sgid, totalAmount);
            String aliOrder = Alipay.createAlipaySend(alipayModel, alipayNotifyUrl);
            // 添加请求阿里支付的纪录
            AlipayModel addAlipayModel = HodgepodgeMethod.assembleAlipayModel(alipayModel, aliOrder);
            ComponentUtil.alipayService.add(addAlipayModel);


            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(aliOrder, stime, secretKeySign); // aliOrder+stime+秘钥=sign
            String strData = HodgepodgeMethod.assembleAlipayResult(stime, sign, aliOrder);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 纪录用户操作存储到redis：用于check用户是否频繁操作
            HodgepodgeMethod.setAliPayMember(memberId);

            // 添加流水
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestModel, ServerConstant.InterfaceEnum.ALI_SENDALI.getType(),
                    ServerConstant.InterfaceEnum.ALI_SENDALI.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestModel, ServerConstant.InterfaceEnum.ALI_SENDALI.getType(),
                    ServerConstant.InterfaceEnum.ALI_SENDALI.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AlipayController.sendAli() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }




    /**
     * @Description: 阿里支付宝：生成订单码-H5页面
     * <p>
     *     把调用阿里支付宝的类生成的订单码返回给客户端
     * </p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/ali/sendAliH5
     * 请求的属性类:RequestAlipay
     * 必填字段:{"memberId":1,"returnUrl":"http://www.baidu.com","body":"1","subject":"subject1","outTradeNo":"outTradeNo1","totalAmount":"0.01","productCode":"productCode1","agtVer":1,"clientVer":1,"clientType":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","androidVer":"7.1.2","channel":"channel_1","channelNum":"channelNum_1","spreadValue":"spreadValue_1"}
     * 客户端加密字段:ctime+cctime+totalAmount+memberId/token+秘钥=sign
     * 服务端加密字段:aliOrder+stime+token+秘钥=sign
     * result={
     *     "resultCode": "0",
     *     "message": "success",
     *     "data": {
     *         "jsonData": "eyJhbGlPcmRlciI6Ijxmb3JtIG5hbWU9XCJwdW5jaG91dF9mb3JtXCIgbWV0aG9kPVwicG9zdFwiIGFjdGlvbj1cImh0dHBzOi8vb3BlbmFwaS5hbGlwYXkuY29tL2dhdGV3YXkuZG8/Y2hhcnNldD1VVEYtOCZtZXRob2Q9YWxpcGF5LnRyYWRlLndhcC5wYXkmc2lnbj1LdHh5JTJCM25JTkpiTW1rWUJIWnZNMDBCJTJGOEVsR2RwWVJwQURrWjVEbGdtUjQxalBRdGtzbWc4Y052ZDgzT25rUkR5SE1CRE40Y3o4YWhHbXk2VXRJQ2hqRDBBYXRxZU9IR2k3YzZSanRHczZKeDZGSTJTOGkwdkdsNkxDQnFaZyUyRlRnSEhtcyUyQlNqbWV3dWFvQ0tudjBjJTJCOXNuMmhDNXNZM3p3UUIlMkJveW9hdlZWcUIyVHRwNE1XckdzalB5R3ptQTVSdVJYMW1jemI4VEdMQmc3QzFtdk1qenVuajglMkJjNWxWcnZKc1laRTEyZllqTjREQzZHYlVTV3E2MiUyRnVuQ1V6aHl5MUlkRyUyRmtNemRwZGNENzklMkJmWlJBd1didnhURjE0Y2tVdENDeGJvdXRIclFRaVZjemx5TnNDJTJCYlBSbjkza0tGc1NkNDN1NzJWT3BBbUQzTU1UMGJ3JTNEJTNEJm5vdGlmeV91cmw9aHR0cCUzQSUyRiUyRjExNC41NS42Ny4xNjclM0E4MDgzJTJGbWclMkZhbGklMkZub3RpZnkmdmVyc2lvbj0xLjAmYXBwX2lkPTIwMTgxMTEzNjIxMDM5OTkmc2lnbl90eXBlPVJTQTImdGltZXN0YW1wPTIwMjAtMDEtMTkrMTclM0EzOSUzQTQwJmFsaXBheV9zZGs9YWxpcGF5LXNkay1qYXZhLTQuOC43My5BTEwmZm9ybWF0PWpzb25cIj5cbjxpbnB1dCB0eXBlPVwiaGlkZGVuXCIgbmFtZT1cImJpel9jb250ZW50XCIgdmFsdWU9XCJ7JnF1b3Q7Ym9keSZxdW90OzomcXVvdDvotLnnlKjnvLTnurMmcXVvdDssJnF1b3Q7b3V0X3RyYWRlX25vJnF1b3Q7OiZxdW90OzIwMjAwMTE5MTczOTA0MDAwMDAwMSZxdW90OywmcXVvdDtwcm9kdWN0X2NvZGUmcXVvdDs6JnF1b3Q7NTAwX0hZJnF1b3Q7LCZxdW90O3N1YmplY3QmcXVvdDs6JnF1b3Q7NTAw6LS555SoJnF1b3Q7LCZxdW90O3RpbWVvdXRfZXhwcmVzcyZxdW90OzomcXVvdDszMG0mcXVvdDssJnF1b3Q7dG90YWxfYW1vdW50JnF1b3Q7OiZxdW90OzAuMDEmcXVvdDt9XCI+XG48aW5wdXQgdHlwZT1cInN1Ym1pdFwiIHZhbHVlPVwi56uL5Y2z5pSv5LuYXCIgc3R5bGU9XCJkaXNwbGF5Om5vbmVcIiA+XG48L2Zvcm0+XG48c2NyaXB0PmRvY3VtZW50LmZvcm1zWzBdLnN1Ym1pdCgpOzwvc2NyaXB0PiIsInNpZ24iOiIxNDM3MWFhNTFhNmM0ZmQwM2I2NmM2M2NlYTdiYTY3OCIsInN0aW1lIjoxNTc5NDI2NzkzMTI2fQ=="
     *     },
     *     "sgid": "202001191739040000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/sendAliH5", method = {RequestMethod.POST})
    public JsonResult<Object> sendAliH5(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();

        RequestAlipay requestModel = new RequestAlipay();
        try{
            // 解密
            data = StringUtil.decoderBase64(requestData.jsonData);
            requestModel  = JSON.parseObject(data, RequestAlipay.class);
            if (requestModel.memberId != null){
                memberId = requestModel.memberId;
            }else{
                // check校验数据、校验用户是否登录、获得用户ID
                memberId = HodgepodgeMethod.checkAlipayData(requestModel);
                requestModel.memberId = memberId;
                token = requestModel.getToken();
            }

            // check用户是否支付过于频繁
            HodgepodgeMethod.checkAliPayMember(memberId);
            // check用户是否已经支付过
            PayCustModel payCustQuery = HodgepodgeMethod.assemblePayCustQuery(memberId);
            PayCustModel payCustModel = (PayCustModel) ComponentUtil.payCustService.findByObject(payCustQuery);
            HodgepodgeMethod.checkIsPayCust(payCustModel);

            // 校验ctime
            // 校验sign
            String totalAmount = ComponentUtil.loadConstant.totalAmount;
            requestModel.totalAmount = totalAmount;
            // 调用阿里云支付宝生成订单-h5
            AlipayH5Model alipayModel = HodgepodgeMethod.assembleH5AlipayData(requestModel, sgid, totalAmount);
            String alipayData = JSON.toJSONString(alipayModel);
            String aliOrder = Alipay.createH5AlipaySend(alipayData, requestModel.returnUrl, alipayNotifyUrl);
            // 添加请求阿里支付的纪录-H5
            AlipayModel addAlipayModel = HodgepodgeMethod.assembleH5AlipayModel(alipayModel, requestModel, aliOrder);
            ComponentUtil.alipayService.add(addAlipayModel);


            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(aliOrder, stime, secretKeySign); // aliOrder+stime+秘钥=sign
            String strData = HodgepodgeMethod.assembleAlipayResult(stime, sign, aliOrder);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 纪录用户操作存储到redis：用于check用户是否频繁操作
            HodgepodgeMethod.setAliPayMember(memberId);
            // 添加流水
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestModel, ServerConstant.InterfaceEnum.ALI_SENDALIH5.getType(),
                    ServerConstant.InterfaceEnum.ALI_SENDALIH5.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, requestModel, ServerConstant.InterfaceEnum.ALI_SENDALIH5.getType(),
                    ServerConstant.InterfaceEnum.ALI_SENDALIH5.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AlipayController.sendAliH5() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 接收阿里支付宝的数据-APP
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/mg/ali/notify
     */
    @RequestMapping(value = "/notify", method = {RequestMethod.POST})
//    public JsonResult<Object> notify(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        RequestAlipay requestAlipay = new RequestAlipay();
        try{
            //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            String resultData = JSON.toJSONString(params);// 阿里返回的数据
            log.info(String.format("the AlipayController.notify() , the resultData=%s ", resultData));
            String ALIPAY_PUBLIC_KEY = "";
            if(params.get("app_id").equals(Alipay.ALIPAY_SEND_APP_ID)){
                ALIPAY_PUBLIC_KEY = Alipay.ALIPAY_PUBLIC_KEY;
            }else if(params.get("app_id").equals(Alipay.ALIPAY_SEND_H5_APP_ID)){
                ALIPAY_PUBLIC_KEY = Alipay.H5_ALIPAY_PUBLIC_KEY;
            }
            boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8","RSA2");
            if (flag){
                AlipayNotifyModel alipayNotifyModel = HodgepodgeMethod.assembleAlipayNotify(params);
                if (alipayNotifyModel != null){
                    ComponentUtil.alipayService.addAlipayNotify(alipayNotifyModel);
                }
            }
            log.info(String.format("the AlipayController.notify() , the flag=%s ", flag));
            // 返回数据给客户端
            response.getWriter().write("success");//直接将完整的表单html输出到页面
//            return JsonResult.successResult(null, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            log.error(String.format("this AlipayController.notify() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, request.getQueryString()));
            e.printStackTrace();
//            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }


//    /**
//     * @Description: 接收阿里支付宝的数据-H5
//     * @param request
//     * @param response
//     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
//     * @author yoko
//     * @date 2019/11/25 22:58
//     * local:http://localhost:8082/mg/ali/notifyH5
//     */
//    @RequestMapping(value = "/notifyH5", method = {RequestMethod.POST})
////    public JsonResult<Object> notifyH5(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
//    public void notifyH5(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        String sgid = ComponentUtil.redisIdService.getNewId();
//        String cgid = "";
//        String ip = StringUtil.getIpAddress(request);
//        RequestAlipay requestAlipay = new RequestAlipay();
//        try{
//            //获取支付宝POST过来反馈信息
//            Map<String,String> params = new HashMap<String,String>();
//            Map requestParams = request.getParameterMap();
//            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//                String name = (String) iter.next();
//                String[] values = (String[]) requestParams.get(name);
//                String valueStr = "";
//                for (int i = 0; i < values.length; i++) {
//                    valueStr = (i == values.length - 1) ? valueStr + values[i]
//                            : valueStr + values[i] + ",";
//                }
//                log.info("");
//                //乱码解决，这段代码在出现乱码时使用。
//                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//                params.put(name, valueStr);
//            }
//            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
//            String resultData = JSON.toJSONString(params);// 阿里返回的数据
//            log.info(String.format("the AlipayController.notify() , the resultData=%s ", resultData));
//            boolean flag = AlipaySignature.rsaCheckV1(params, Alipay.ALIPAY_PUBLIC_KEY, "UTF-8","RSA2");
//            if (flag){
//                AlipayNotifyModel alipayNotifyModel = HodgepodgeMethod.assembleAlipayNotify(params);
//                if (alipayNotifyModel != null){
//                    ComponentUtil.alipayService.addAlipayNotify(alipayNotifyModel);
//                }
//            }
//            log.info(String.format("the AlipayController.notifyH5() , the flag=%s ", flag));
//            // 返回数据给客户端
//            response.getWriter().write("success");//直接将完整的表单html输出到页面
////            return JsonResult.successResult(null, cgid, sgid);
//        }catch (Exception e){
//            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
//            log.error(String.format("this AlipayController.notifyH5() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, request.getQueryString()));
//            e.printStackTrace();
////            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
//        }
//    }


}
