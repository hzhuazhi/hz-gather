package com.hz.gather.master.core.common.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.hz.gather.master.core.common.utils.constant.LoadConstant;
import com.hz.gather.master.core.model.alipay.AlipayModel;
import com.hz.gather.master.util.ComponentUtil;

/**
 * @Description 阿里支付的公共方法（支付宝）
 * @Author yoko
 * @Date 2019/12/19 16:56
 * @Version 1.0
 */
public class Alipay {

    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCVvR7XxYF71d13NrZfUaKegm0Eeti3x0RJumsdvKCaADLWqjmgzUPtt5QwHdJrp9GPvAKO5RiyoAS717lupn6dPKLb4HAAvBUDP4n7kWd/ccYopnCyNwelLhlRQuJKis0Kn0tMLXzH8A8gqhxHIranz/b9HI9QxAGj5q6u09pgFSNo0eYijgZSpmpcGE7sqKN3rsM3pBCPpdHeRF45TZOUkNm9tC88mZaLL1Ybg0uY4crGix0cX2DSlLkTRRUSxT6kxIVZwYrriy5cZE+BLtarwbXmUcbeA6utngU5kLb+Fs/0Rb9y+kNE1ufEsK3mj4OXGdJLvVc46XtzKGAnIrfpAgMBAAECggEAdeLqwV1RT7lUHmMIuYp/yONOCNTegfFzpcgKROMYXaYCYFasABkafbCmDiusWe1JYyvVp8Jd62Nn1qtD5gVWcVwnq4vLIgjxUhlX/KLBJOjh5WcEGoqQjk74x60skxsryk0W7uZw8sfgYQsMMET4IK3t+d/eROvLjsoyhJltt241oThs4CS4DUN2nDTOJRsGZsP3nkwAQ0Uqg7EKko4i27JvqwqVAR1RIc/2U0YYEefFLFqkqAq9yrYsRN7y9rC3/9pniQw+eDfFnmM4h0TD9O+XuvJ2Cvovh/CytnBDX56cljCUQfkXdXPlIMjPUXBL9Cp1Pf3sJKiSGpGhzYumAQKBgQDhKIUy0ZQmezwMubEK7yXLbIEExOCFtFgyKOtNjF02tfFCictjGDjgy9gtpvD//qfja8aNwo/dx7VpNXmu5HwtaQbb60Z/jmtOOWxzDunFN5x2+HwrJkmccS9wc015YGcBCm6fNoMNiT5FrTqLPADu5c8oIE2MPFNRYy1ScSvT8QKBgQCqP+mOibWU5CjLJiW4hPo+L0mdzzXx3RUShCF71CwDq5yJlKtzoNLbGlTLrUBAzlL0X40+JT19+XJ/qVgXgErwe9pc5/tk45fg0gzDzfRG5FH06KnXgg+LuPzJuq1pFJPIso/ieydwkKbPhFRqa4bBIGZ2co0EJtD+10zmD8M7eQKBgDYPG4PrC839cpp/jwFYD9P6ljOF7X5MoYxRhMVWuR6kqmtWWC3PqABZEOtNFLnx829H9iEdIJuYEndTLocONr5UZCAzKfcAQ8VBRkeNwhTm2Ds8R9u4Up4sEr7GAEjvG2wFjC/Pz8FVPU1PXSoG2xaAuK8iqlvMNBjrXftYTrSRAoGAYAy6yc3rgdrPIP2enmF3hQnMsJmsx42BS/b7pQ7o3hE9uWkE6bE8BJzwZhCShX1h07jsyLjd3mqTLG7v4iJhJRfa9Dt021CD6TTYOsfBHxXFHzT8TTd1adCt3WnSV8xiLAKBprbwY1NYqQ7xSTDsz5xl8kFiSYtUsdHp8onGDZECgYAJdU+TqyfyhKk+H92e6+BYQon89trmQtDmBbVlywC2F+T3SVXlgH0RF4cwenanKnkFRkh8DXymXV85lh956R1gaKdlVLK06zb9ZnWggGBSLr0cIWCChvyoj/Gd2s9SXEEcGGG7mQVNEZC71x2elrH8nrJ3VAxZPew6W7QdBUJQ9w==";
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAji9YdMSNYGWgCMKQrH8AuSZDryGKjws0U1zRjYAX6wh/ZQgFvg/NcBHtvRZy5mOPpbFa0EFHZrXXy6j6KY8pR+ht/38ncBAGOsvVcfX/aBkq2wyEUYtjW/pnK78k+iRW07Z1lyiPCesnTxsvQvMKadngoeQgau916hy7V8f+KZQmcbpFWiYR3gEoUzZvmXcfsHJOLwE7t2w4WqmP0/bUs24DFDIAL1nqTBbo9c4/0KLklwbrdJdBxS3lCeGzmI9SBj3tI/gw8qY+sk7op0z7JhK74Zt+l021Ig5T/AgJeuyWbYvXWl2f6XVznF1KbmG8VsAUc7MMXUEUVbg6LWKP2QIDAQAB";


    public static String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDbx1v3H8wJmmZ/08HLiYo1nByvgys3UnRu+PjmtvEM11o9E/t6Y40AQwhIhAXbbAAxYjrhoixDYJ9eZ8hC59eJsoIINwLwCO+vwen956XQdAaVPLgwR0mUXABXKcHuN17QFBJNXt+Cux7Oq/OzJJud0wVuaXt1oZ6Gx3/+iqITqHgfZBUgl1gnTbnxJ6P3zMqb/2SNG6arGkZGdkveGLzSa6S2qLIn42nI8n1GxrEOhLZJ5XWFbNWtE2ksRjir/I6LXGQS7v0RiXHqrEa8DWm0q/pXytWJZ6iKhBQ5VitLmLTn9PVgf7CRd0uX0ptWnWlI+V9T19uH/8A9yAgTvHBRAgMBAAECggEBALcU0dWBVXVCe9RMUIn/X3TdQUAsIpoJRBkVqjJ3f0JVO0TaGomP2Gu5a3MNF9v7kgWRcL95WyodmWUJS1gAzhpU6+FwAjB3IUPmY8gxan3FyZ4UYNNG71J5eYcir/yDf6YSslRljgfXXQiJUY+QFVpZ6ZJaqJALPIXGNyeR0QZRq90QHczyjzVInVvOgy954BfUJoaHCcqSSNZMa+bWPkB7GKxXC2BwJC1IysOrT+tn5pEX3W/b2/sirH2/MNWIsV7hOR99JgrYsQiYkQ4Iq26CnDMNePm4sDF0s9sh/4gnOolFcZUTc/GY5rU08M+V2dGsB1b6PlY12FNR1McEg2kCgYEA7mUNVxhaqiElkhMwyrjgu8PQ/NLlRj3KuQjrDF0Pwetp3mKQep0CNKpUBzkfa+AdXCY07Ol6Y0hNGX+9J40f2BHp/JtMFJTt2k0qxfTabMBNSnPOyCzbdVYW6Vlw1qsaLgq600aeu3UNz4uK7JHhY53mjpSY/vCyu77jVEkYJnsCgYEA7AJdSzCNgFybZyP+JlEe1ZAlL0RINnX52JVFsCjvONyNnAQi6Nn4J0LaLpts05QL+rEMBqoaLJYN/dFplyu9pcYL1V5XfWocPDetjf1GnpMzblGllX3aBY9RISjQHRtH8RcNzEJGk38bA8VAEUHUKivBD1LovmjSL+vUyW7I0KMCgYEA03kg1FnuU20D/wi3B53o/ac/BIewixbVdj7LAzSqfcNvLq8QqzQMeNt/nsi1buRoJw5ddKvIvbmtayk9ipBN50Y84rCAVOGn/Tbm8qO5/y63YYxJqpjgNL4hpO6KgmNV3fH2uOS0emXj0nBe1Gy0G8I+e8yly8GJS7KRxnrwyXECgYBq4tM/x1h+hvJ2rs1eqySM0kCU/Ja724hw94HdO1zEYtbbjuAElxsVJOjNbOTHmegm/GIW7pj2Emt5xYrNxSiZ2GzpkFWNXi41c33trYR7Mu17DA0y/BFurS6wFtzSIdXeMXO1S1rNWCZy+bV/W1HsW26PMxxnh++RdnwjUkIugwKBgQDSehj0piK4TgMibFs1DYMJIFse+rm8CPXgOt12bSW3MjjjQg7J4M2EHrqfUP7UQPfdeThyEYqlGGKd2CcFy0BeQi+OPAyVw0E6mQVcXzzLDWE1KBQdjv/NP4MMxV3rBN4Oy4z6j8Zk14O6sL6gsE9CrweDxTsAiaeU/Cjyzk3CIQ==";

    /**
     * @Description: 组装阿里支付的订单号
     * @param alipayModel - 基本数据
     * @param notifyUrl - 同步地址
     * @return String
     * @author yoko
     * @date 2019/12/19 19:38
    */
    public static String createAlipaySend(AlipayModel alipayModel, String notifyUrl){
        String resultData;
        //实例化客户端
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                "2021001105669996", APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(alipayModel.body);
        model.setSubject(alipayModel.subject);
        model.setOutTradeNo(alipayModel.outTradeNo);
        model.setTimeoutExpress(alipayModel.timeoutExpress);
        model.setTotalAmount(alipayModel.totalAmount);
        model.setProductCode(alipayModel.productCode);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
//        model.setBody("我是测试数据");
//        model.setSubject("App支付测试Java");
////        model.setOutTradeNo(outtradeno);
//        model.setOutTradeNo("df_test_hzhz_001");
//        model.setTimeoutExpress("30m");
//        model.setTotalAmount("0.01");
//        model.setProductCode("QUICK_MSECURITY_PAY");
//        request.setBizModel(model);
//        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            resultData = response.getBody();
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
        return resultData;
    }
    
    
    /**
     * @Description: 支付宝单笔转账到账户
     * @param data - 要转账（提现）的数据
     * @return 
     * @author yoko
     * @date 2020/1/10 21:01 
    */
    public static AlipayFundTransUniTransferResponse transferAlipay(String data){
        try{
            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
            certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
            certAlipayRequest.setAppId("2017072507891356");
            certAlipayRequest.setPrivateKey(privateKey);
            certAlipayRequest.setFormat("json");
            certAlipayRequest.setCharset("UTF-8");
            certAlipayRequest.setSignType("RSA2");
            certAlipayRequest.setCertPath(ComponentUtil.loadConstant.certPath);
            certAlipayRequest.setAlipayPublicCertPath(ComponentUtil.loadConstant.alipayPublicCertPath);
            certAlipayRequest.setRootCertPath(ComponentUtil.loadConstant.rootCertPath);
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);

            AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
            request.setBizContent(data);
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
            AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
            return response;
//            if(response.isSuccess()){
//                System.out.println("调用成功");
//                return response;
//            } else {
//                System.out.println("调用失败");
//                return null;
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String [] args){
        createAlipaySend(new AlipayModel(), null);
    }
}
