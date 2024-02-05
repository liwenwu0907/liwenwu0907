package com.example.demo.util.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String massage) {
        //rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_SMS_QUEUE, massage);
        //rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_SMS_QUEUE2, massage);
        //rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_SMS_EXCHANGE,"sms", massage);
        //rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_SMS_EXCHANGE,"email", massage);
        //rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_SMS_EXCHANGE,"lazy.orange.rabbit", massage);
    }
}
