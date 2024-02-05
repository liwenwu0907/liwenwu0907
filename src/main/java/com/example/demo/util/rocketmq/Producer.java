//package com.example.demo.util.rocketmq;
//
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.springframework.stereotype.Component;
//
///**
// * @author li wenwu
// * @date 2020/11/30 15:45
// * rocketmq的生产者
// */
//@Component
//public class Producer {
//
//    private String produceGroup = "produceGroup";
//
//    private DefaultMQProducer producer;
//
//    public Producer() {
//
//        producer = new DefaultMQProducer(produceGroup);
//        producer.setVipChannelEnabled(false);
//        producer.setNamesrvAddr(RocketMqConfig.SERVER_IP);
//        start();
//        System.out.println("生产者 启动成功=======");
//    }
//
//    /**
//     * 对象在使用之前必须要调用一次，只能初始化一次
//     */
//    public void start(){
//        try {
//            this.producer.start();
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public DefaultMQProducer getProducer() {
//        return producer;
//    }
//
//    public void shutdownProducer(){
//        producer.shutdown();
//    }
//}
