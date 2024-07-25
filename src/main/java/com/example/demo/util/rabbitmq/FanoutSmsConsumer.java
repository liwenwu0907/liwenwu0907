package com.example.demo.util.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class FanoutSmsConsumer {
    Logger logger = LoggerFactory.getLogger(FanoutSmsConsumer.class);

//    @RabbitListener(queues = "topic_sms_queue")
//    @RabbitHandler
//    public void process(Message massage) throws UnsupportedEncodingException {
//        String id = massage.getMessageProperties().getMessageId();
//        String msg = new String(massage.getBody(), "UTF-8");
//        JSONObject jsonObject = JSONObject.parseObject(msg);
//        Integer taskid = jsonObject.getInteger("taskId");
//        logger.info("消費者1：taskId:" + taskid);
//    }
//
//    @RabbitListener(queues = "topic_sms_queue")
//    @RabbitHandler
//    public void process2(Message massage) throws UnsupportedEncodingException {
//        String id = massage.getMessageProperties().getMessageId();
//        String msg = new String(massage.getBody(), "UTF-8");
//        JSONObject jsonObject = JSONObject.parseObject(msg);
//        Integer taskid = jsonObject.getInteger("taskId");
//        logger.info("消費者2：taskId:" + taskid);
//
//    }

}
