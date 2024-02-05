package com.example.demo.util.rabbitmq;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
//
//    //队列名
//    public static String FANOUT_SMS_QUEUE = "fanout_sms_queue";
//    public static String FANOUT_SMS_QUEUE2 = "fanout_sms_queue2";
//    public static String FANOUT_SMS_EXCHANGE = "fanoutExchange1";
//
//    public static String DIRECT_SMS_QUEUE = "direct_sms_queue";
//    public static String DIRECT_SMS_QUEUE2 = "direct_sms_queue2";
//    public static String DIRECT_SMS_EXCHANGE = "directExchange1";
//
//    public static String TOPIC_SMS_QUEUE = "topic_sms_queue";
//    public static String TOPIC_SMS_QUEUE2 = "topic_sms_queue2";
//    public static String TOPIC_SMS_EXCHANGE = "topicExchange1";
//
//    //创建队列
//    /**
//     * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
//     * @version 2.0,
//     * @param
//     * @return
//     * @description  广播模式
//     */
//    @Bean
//    public Queue fanoutSmsQueue() {
//        return new Queue(FANOUT_SMS_QUEUE);
//    }
//    @Bean
//    public Queue fanoutSmsQueue2() {
//        return new Queue(FANOUT_SMS_QUEUE2);
//    }
//    //创建交换机
//    @Bean
//    public FanoutExchange fanoutExchange1() {
//        return ExchangeBuilder.topicExchange(FANOUT_SMS_EXCHANGE).durable(true).build();
//        //return new FanoutExchange(FANOUT_SMS_EXCHANGE);
//    }
//    //队列与交换机进行绑定
//    @Bean
//    Binding bindingSms() {
//        return BindingBuilder.bind(fanoutSmsQueue()).to(fanoutExchange1());
//    }
//    @Bean
//    Binding bindingSms2() {
//        return BindingBuilder.bind(fanoutSmsQueue2()).to(fanoutExchange1());
//    }
//    /**
//     * direct模式
//     * 消息中的路由键（routing key）如果和 Binding 中的 binding key 一致， 交换器就将消息发到对应的队列中。路由键与队列名完全匹配
//     * @return
//     */
//    @Bean
//    public Queue directQueue1() {
//        return new Queue(DIRECT_SMS_QUEUE);
//    }
//    @Bean
//    public Queue directQueue2() {
//        return new Queue(DIRECT_SMS_QUEUE2);
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange(DIRECT_SMS_EXCHANGE);
//    }
//
//    @Bean
//    public Binding directBinding1() {
//        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("sms");
//    }
//    @Bean
//    public Binding directBinding2() {
//        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("email");
//    }
//
//    /**
//     * Topics 通配符模式
//     * @return
//     */
//    @Bean
//    public Queue topicQueue1() {
//        return new Queue(TOPIC_SMS_QUEUE);
//    }
//    @Bean
//    public Queue topicQueue2() {
//        return new Queue(TOPIC_SMS_QUEUE2);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_SMS_EXCHANGE);
//    }
//
//    @Bean
//    public Binding topicBinding1() {
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("*.orange.*");
//    }
//    @Bean
//    public Binding topicBinding2() {
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("*.*.rabbit");
//    }
//    @Bean
//    public Binding topicBinding3() {
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("lazy.*.*");
//    }

}
