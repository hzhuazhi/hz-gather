package com.hz.gather.master.core.runner;

import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 阿里支付宝的task类
 * @Author yoko
 * @Date 2020/1/9 19:28
 * @Version 1.0
 */
@Component
@EnableScheduling
public class TaskAlipay {
    private final static Logger log = LoggerFactory.getLogger(TaskAlipay.class);


    @Value("${task.limit.num}")
    private int limitNum;


    /**
     * @Description: 阿里云支付：单笔转账到账户
     * @author yoko
     * @date 2019/12/27 21:30
     */
//    @Scheduled(cron = "1 * * * * ?")
//    @Scheduled(fixedDelay = 1000) // 每秒执行
    public void taskTransferAlipay() throws Exception{
        log.info("TaskAlipay.taskTransferAlipay()------------------进来了!");
        // 查询要跑的数据
//        try{
//            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
//            certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
//            certAlipayRequest.setAppId("2017072507891356");
//            certAlipayRequest.setPrivateKey(privateKey);
//            certAlipayRequest.setFormat("json");
//            certAlipayRequest.setCharset("UTF-8");
//            certAlipayRequest.setSignType("RSA2");
//            certAlipayRequest.setCertPath(certPath);
//            certAlipayRequest.setAlipayPublicCertPath(alipayPublicCertPath);
//            certAlipayRequest.setRootCertPath(rootCertPath);
//            DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
//
//            AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
//            request.setBizContent("{" +
//                    "\"out_biz_no\":\"df-alipay-test-1\"," +
//                    "\"trans_amount\":1.00," +
//                    "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
//                    "\"biz_scene\":\"DIRECT_TRANSFER\"," +
//                    "\"order_title\":\"段峰-测试-提现-1\"," +
//                    "\"payee_info\":{" +
//                    "\"identity\":\"duanfeng_1712@qq.com\"," +
//                    "\"identity_type\":\"ALIPAY_LOGON_ID\"," +
//                    "\"name\":\"段峰\"," +
//                    "    }," +
//                    "\"remark\":\"测试金额-哈哈\"," +
//                    "\"business_params\":\"\"," +
////                    "\"business_params\":\"{\\\"payer_show_name\\\":\\\"服务代理\\\"}\"," +
//                    "  }");
//            AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
//            if(response.isSuccess()){
//                System.out.println("调用成功");
//            } else {
//                System.out.println("调用失败");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        log.info("TaskAlipay.taskTransferAlipay()------------------结束了!");
    }

    public static void main(String [] args) throws Exception{
        TaskAlipay t = new TaskAlipay();
        t.taskTransferAlipay();
    }
}
