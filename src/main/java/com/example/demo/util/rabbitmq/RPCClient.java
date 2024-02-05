//package com.example.demo.util.rabbitmq;
//
//import com.alibaba.fastjson.JSON;
//import com.jfh.ms.cmm.tenant.constant.Constant;
//import com.jfh.ms.cmm.tenant.dto.request.AppsRequestDTO;
//import com.rabbitmq.client.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.UUID;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//@Controller
//@RequestMapping("RPCClient")
//public class RPCClient {
//
//    @RequestMapping("test")
//    public void test(){
//        AppsRequestDTO appsRequestDTO = new AppsRequestDTO();
//        Integer pageNo = 1;
//        Integer pageSize = 10;
//        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//        System.out.println(sendMessage(true,"cmm-tenant","/appsApi/test"));
//        System.out.println(sendMessage(false,"cmm-tenant","/appsApi/listappsByParam",appsRequestDTO,pageNo,pageSize,request));
//
//    }
//
//    /**
//     * 调用方发送消息并获取返回结果
//     * async 是否异步（异步不需要返回值，同步有返回值） project 系统名称 path 路径 parameter 参数
//     * @author  liwenwu
//     * @date  2021/12/15
//     **/
//    public String sendMessage(boolean async,String project,String path,Object... parameter){
//        String result = null;
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("10.10.5.199");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("mquser1");
//        connectionFactory.setPassword("jfD289zqP");
//        connectionFactory.setVirtualHost("dev");
//        Connection connection = null;
//        try {
//            String rpcQueueName = project + path;
//            connection = connectionFactory.newConnection();
//            Channel channel = connection.createChannel();
//            //创建回调队列
//            String callbackQueue = channel.queueDeclare().getQueue();
//            //创建消息带有correlationId的消息属性
//            String correlationId = UUID.randomUUID().toString();
//            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().correlationId(correlationId).replyTo(callbackQueue).build();
//            StringBuilder sb = new StringBuilder();
//            for(Object object:parameter){
//                //将所有的参数以自定义分隔符连接起来
//                if(!(object instanceof HttpServletRequest)){
//                    sb.append(JSON.toJSONString(object)).append(Constant.SEPARATOR);
//                }
//            }
//            String message = sb.toString();
//            channel.basicPublish("",rpcQueueName,basicProperties,message.getBytes());
//            log.info("RpcClient send message=" + message + ", correaltionId = " + correlationId + ",path=" + rpcQueueName);
//            if(!async){
//                final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
//                channel.basicConsume(callbackQueue,true,new DefaultConsumer(channel){
//                    @Override
//                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
//                        /*检查corrId是否匹配*/
//                        if (properties.getCorrelationId().equals(correlationId)){
//                            response.offer(new String(body, StandardCharsets.UTF_8));
//                        }
//                    }
//                });
//                result = response.take();
//            }
//        } catch (IOException | TimeoutException | InterruptedException e) {
//            log.error("error：",e);
//        }
////        finally {
////            try {
////                if(null != connection){
////                    connection.close();
////                }
////            } catch (IOException e) {
////                log.error("connection关闭异常",e);
////            }
////        }
//        return result;
//    }
//}
