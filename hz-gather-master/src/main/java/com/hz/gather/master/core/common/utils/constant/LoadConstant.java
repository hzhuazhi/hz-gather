package com.hz.gather.master.core.common.utils.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 加载的配置文件
 * @Author yoko
 * @Date 2019/12/30 12:37
 * @Version 1.0
 */
@Component
public class LoadConstant {

    /**
     * 开发者应用公钥证书路径
     */
    @Value("${alipay.cert.path}")
    public String certPath;

    /**
     * 支付宝公钥证书文件路径
     */

    @Value("${alipay.public.cert.path}")
    public String alipayPublicCertPath;

    /**
     * 支付宝CA根证书文件路径
     */
    @Value("${alipay.root.cert.path}")
    public String rootCertPath;
}
