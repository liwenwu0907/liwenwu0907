//package com.example.demo.util.rocketmq;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.Message;
//import org.springframework.stereotype.Component;
//
//import java.io.UnsupportedEncodingException;
//
///**
// * @author li wenwu
// * @date 2020/11/30 15:51
// * rocketmq的消费者
// */
//@Component
//public class Consumer {
//
//    private String consumerGroup = "consumerGroup";
//
//    private DefaultMQPushConsumer consumer;
//
//    public Consumer() throws MQClientException {
//
//        consumer = new DefaultMQPushConsumer(consumerGroup);
//        consumer.setNamesrvAddr(RocketMqConfig.SERVER_IP);
//        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        //订阅主题和 标签（ * 代表所有标签)下信息
//        consumer.subscribe(RocketMqConfig.TOPIC, "*");
//        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
//        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
//            // msgs中只收集同一个topic，同一个tag，并且key相同的message
//            // 会把不同的消息分别放置到不同的队列中
//            try {
//                for (Message msg : msgs) {
//
//                    //消费者获取消息 这里只输出 不做后面逻辑处理
//                    String body = new String(msg.getBody(), "utf-8");
//                    System.out.println("Consumer-获取消息-主题topic为={" + msg.getTopic() + "}, 消费消息为={" + body + "}");
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//            }
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        });
//
//        consumer.start();
//        System.out.println("消费者 启动成功=======");
//    }
//}
