package com.hz.gather.master.core.controller.price;

import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.price.VirtualCoinPriceModel;
import com.hz.gather.master.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description 虚拟币每天兑换的价格的Controller层
 * @Author yoko
 * @Date 2019/11/21 22:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/ger/pe")
public class VirtualCoinPriceController {
    private static Logger log = LoggerFactory.getLogger(VirtualCoinPriceController.class);

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
     * @Description: 获取虚拟币每天兑换的价格
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/21 22:58
     * local:http://localhost:8082/ger/pe/getData
     * 请求的属性类:RequestConsumer
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:maxPrice+minPrice+stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getData", method = {RequestMethod.POST})
//    public JsonResult<Object> getData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getData(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        try{
            // 虚拟币每天兑换的价格
            ComponentUtil.virtualCoinPriceService.test();
//            VirtualCoinPriceModel virtualCoinPriceQuery = PublicMethod.assembleVirtualCoinPriceQuery();
            List<VirtualCoinPriceModel> virtualCoinPriceList =  ComponentUtil.virtualCoinPriceService.getVirtualCoinPriceList(new VirtualCoinPriceModel(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 返回数据给客户端
            return JsonResult.successResult(null, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            log.error(String.format("this VirtualCoinPriceController.getData() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, jsonData));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }

}
