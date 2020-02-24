package com.hz.gather.master.core.controller.user;

import com.alibaba.fastjson.JSON;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ExceptionMethod;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.utils.JsonResult;
import com.hz.gather.master.core.common.utils.StringUtil;
import com.hz.gather.master.core.common.utils.constant.Constant;
import com.hz.gather.master.core.common.utils.constant.ServerConstant;
import com.hz.gather.master.core.model.RequestEncryptionJson;
import com.hz.gather.master.core.model.ResponseEncryptionJson;
import com.hz.gather.master.core.model.region.RegionModel;
import com.hz.gather.master.core.model.stream.ConsumerChannelModel;
import com.hz.gather.master.core.model.stream.StreamConsumerModel;
import com.hz.gather.master.core.model.user.CommonModel;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.HodgepodgeMethod;
import com.hz.gather.master.util.PublicMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/21 10:55
 * @Version 1.0
 */
@RestController
@RequestMapping("/mg/reward")

public class RewardController {
    private static Logger log = LoggerFactory.getLogger(RewardController.class);

    /**
     * @Description: 永久vip 领取裂变 奖励
     * @param request
    * @param response
    * @param requestData
     * @return com.hz.gather.master.core.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2020/2/21 16:10
     */

    @PostMapping("/vipReceivefissionReward")
    public JsonResult<Object> vipReceivefissionReward(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String data = "";
        CommonModel commonModel = new CommonModel();
        String time ="";
        String sgid = ComponentUtil.redisIdService.getNewId();
        String cgid = "";
        String ip = StringUtil.getIpAddress(request);
        Integer memberId = 0;

        RegionModel regionModel = HodgepodgeMethod.assembleRegionModel(ip);
        ConsumerChannelModel consumerChannelModel = new ConsumerChannelModel();
        log.info("----------:vipReceivefissionReward 进来啦!");
        String  strData="";
        try{
            data        = StringUtil.decoderBase64(requestData.jsonData);
            commonModel  = JSON.parseObject(data, CommonModel.class);

            boolean   flag  = PublicMethod.isCommonModel(commonModel);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }

            memberId = PublicMethod.tokenGetMemberId(commonModel.getToken());
            if(memberId==0){
                throw  new ServiceException(ENUM_ERROR.INVALID_USER.geteCode(),ENUM_ERROR.INVALID_USER.geteDesc());
            }


            flag = ComponentUtil.userInfoService.memberIsPermanentVIP(memberId);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.R00001.geteCode(),ENUM_ERROR.R00001.geteDesc());
            }

            flag = ComponentUtil.payService.receiveVIPSurplusReward(memberId);
            if(!flag){
                throw  new ServiceException(ENUM_ERROR.R00002.geteCode(),ENUM_ERROR.R00002.geteDesc());
            }
            ResponeseHavaPay responeseHavaPay=new ResponeseHavaPay();
            responeseHavaPay.setFlag(flag);

            strData  =   PublicMethod.toJson(responeseHavaPay);
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;

            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.REWARD_VIPRECEIVEFISSIONREWARD.getType(),
                    ServerConstant.InterfaceEnum.REWARD_VIPRECEIVEFISSIONREWARD.getDesc(), data, strData, consumerChannelModel, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(resultDataModel);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = HodgepodgeMethod.assembleStream(sgid, cgid, memberId, regionModel, commonModel, ServerConstant.InterfaceEnum.REWARD_VIPRECEIVEFISSIONREWARD.getType(),
                    ServerConstant.InterfaceEnum.REWARD_VIPRECEIVEFISSIONREWARD.getDesc(), data, null, consumerChannelModel, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }
}
