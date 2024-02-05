//package com.example.demo.util.kafka;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//
//import javax.annotation.Resource;
//
///**
// * @author li wenwu
// * @date 2021/8/19 17:09
// */
//@Component
//public class KafkaProducer {
//
//    @Resource
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    //自定义topic
//    public static final String TOPIC_TEST = "topic.test";
//
//    //
//    public static final String TOPIC_GROUP1 = "topic.group1";
//
//    //
//    public static final String TOPIC_GROUP2 = "topic.group2";
//
//    public void send(Object obj) {
//        String obj2String = JSONObject.toJSONString(obj);
//        System.out.println("准备发送消息为：" + obj2String);
//        //发送消息
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_TEST, obj);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                //发送失败的处理
//                System.out.println(TOPIC_TEST + " - 生产者 发送消息失败：" + throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
//                //成功的处理
//                System.out.println(TOPIC_TEST + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
//            }
//        });
//
//
//    }
//}
