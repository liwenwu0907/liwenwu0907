//package com.example.demo.util.rabbitmq;
//
//import com.alibaba.fastjson.JSON;
//import com.jfh.ms.cmm.tenant.constant.Constant;
//import com.rabbitmq.client.*;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//@Controller
//@RequestMapping("RPCServer")
//public class RPCServer {
//
//    @Autowired
//    WebApplicationContext applicationContext;
//    @Value("${spring.application.name:}")
//    private String applicationName;
//
//    @RequestMapping("test")
//    public void test(HttpServletRequest request) {
//        receiveMessage(request);
//    }
//
//
//    public void receiveMessage(HttpServletRequest request) {
//        //首先获取全部的URL
//        List<String> allUrlList = getAllUrl();
////        List<String> allUrlList = new ArrayList<>();
////        allUrlList.add("cmm-tenant/RPCServer/test");
////        allUrlList.add("cmm-tenant/RPCClient/test");
//        if(!CollectionUtils.isEmpty(allUrlList)){
//            //循环监听各个URL
//            for(String url:allUrlList){
//                receiveMessage(url,request);
//            }
//        }
//    }
//
//    /**
//     * 监听mq
//     *
//     * @author liwenwu
//     * @date 2021/12/16
//     **/
//    public void receiveMessage(String queueName,HttpServletRequest request) {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("10.10.5.199");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("mquser1");
//        connectionFactory.setPassword("jfD289zqP");
//        connectionFactory.setVirtualHost("dev");
//        Connection connection = null;
//        try {
//            connection = connectionFactory.newConnection();
//            final Channel channel = connection.createChannel();
//            channel.queueDeclare(queueName, false, false, false, null);
//            //一次只处理一条消息
//            channel.basicQos(1);
//            Consumer consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    /*定义消息属性*/
//                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
//                            .Builder()
//                            .correlationId(properties.getCorrelationId())
//                            .build();
//                    String message = new String(body, StandardCharsets.UTF_8);
//                    log.info("{}收到的消息:{}",queueName,message);
//                    String[] strs = null;
//                    if(StringUtils.isNotBlank(message)){
//                        strs = message.split(Constant.SEPARATOR);
//                    }
//                    String response = null;
//                    try {
//                        response = (String) implementMethod(queueName,strs,request);
//                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                    channel.basicAck(envelope.getDeliveryTag(), false);
//                    /*向回调队列发送结果*/
//                    if (response != null) {
//                        channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes(StandardCharsets.UTF_8));
//                    }
//                }
//            };
//            /*消费消息*/
//            channel.basicConsume(queueName, false, consumer);
//        } catch (IOException | TimeoutException e) {
//            log.error("error：", e);
//        }
//
////        finally {
////            if(null != connection){
////                try {
////                    connection.close();
////                } catch (IOException e) {
////                    log.error("关闭链接失败：",e);
////                }
////            }
////        }
//    }
//
//    /**
//     * 获取项目中所有controller的URL
//     *
//     * @author liwenwu
//     * @date 2021/12/16
//     **/
//    public List<String> getAllUrl() {
//        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
//        // 拿到Handler适配器中的全部方法
//        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
//        List<String> urlList = new ArrayList<>();
//        for (RequestMappingInfo info : methodMap.keySet()) {
//            Set<String> urlSet = info.getPatternsCondition().getPatterns();
//            for (String url : urlSet) {
//                //过滤掉不需要的URL
//                if(!url.startsWith("/error") && !url.startsWith("/swagger")
//                && !url.startsWith("/RPCServer") && !url.startsWith("/RPCClient")){
//                    log.info("url:" + url);
//                    urlList.add(applicationName + url);
//                }
//
//            }
//        }
//        return urlList;
//    }
//
//    /**
//     * 执行对应的方法
//     *
//     * @author liwenwu
//     * @date 2021/12/17
//     **/
//    public Object implementMethod(String url, String[] message,HttpServletRequest request) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
//        // 拿到Handler适配器中的全部方法
//        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
//        for (RequestMappingInfo info : methodMap.keySet()) {
//            Set<String> urlSet = info.getPatternsCondition().getPatterns();
//            for (String uri : urlSet) {
//                if (StringUtils.equals(url, applicationName + uri)) {
//                    //匹配上对应的方法，执行对应的方法后跳出循环
//                    Class<?> clazz = methodMap.get(info).getBeanType();
//                    String methodName = methodMap.get(info).getMethod().getName();
//                    for(Method method : clazz.getMethods()){
//                        if(StringUtils.equals(methodName,method.getName())){
//                            if(method.getGenericParameterTypes().length>0){
//                                Object[] cParams = new Object[message.length + 1];
//                                for(int i=0;i<message.length;i++){
//                                    cParams[i] = JSON.parseObject(message[i],method.getParameterTypes()[i]);
//                                }
//                                cParams[message.length] = request;
//                                return clazz.getDeclaredMethod(methodName, method.getParameterTypes()).invoke(clazz.newInstance(), cParams);
//                            }else {
//                                return clazz.getDeclaredMethod(methodName).invoke(clazz.newInstance());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//}
