package org.pliers.chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author shenx
 * @Description: 配置参数
 * @date 2018/3/20
 */
@Component
public class Prop {

    @Value("${keystore.path}")
    public String keystorePath;

    @Value("${keystore.password}")
    public  String keystorePassword;

    @Value("${keystore.type}")
    public  String keystoreType;
}
