//package com.example.demo.util.validation;
//
//import com.ctrip.framework.apollo.Config;
//import com.ctrip.framework.apollo.ConfigService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.context.annotation.Configuration;
//
//import java.text.MessageFormat;
//
///**
// * 动态获取Apollo配置
// * @author  liwenwu
// * @date  2021/11/11
// **/
//@Configuration
//public class ApolloMessageUtil implements InitializingBean {
//
//    /**
//     *  apollo 创建的命名空间名字
//     */
//    private static Config enPublicConfig = ConfigService.getConfig("common.ms.base");
//
//    private static ApolloMessageUtil apolloMessage = null;
//
//    public static ApolloMessageUtil getApolloMessage() {
//        return apolloMessage;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        apolloMessage = this;
//    }
//
//    public static String getString(String key, Object[] params) {
//        String msg = null;
//        if (!StringUtils.isEmpty(key)) {
//            String messageKey = enPublicConfig.getProperty(key, "");
//            msg = MessageFormat.format(messageKey, params);
//        }
//        return msg;
//    }
//
//    public static String getString(String key) {
//        String msg = null;
//        if (!StringUtils.isEmpty(key)) {
//            msg = enPublicConfig.getProperty(key, "");
//        }
//        return msg;
//    }
//
//    public static String getString(Config config ,String key) {
//        String msg = null;
//        if (!StringUtils.isEmpty(key)) {
//            msg = config.getProperty(key, "");
//        }
//        return msg;
//    }
//
//}
