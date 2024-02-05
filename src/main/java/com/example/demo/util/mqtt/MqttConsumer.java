package com.example.demo.util.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * MQTT客户端订阅消息类
 *
 * @author zhongyulin
 */
//@Component
public class MqttConsumer implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(MqttConsumer.class);

    private static MqttClient client;

    private static MqttTopic mqttTopic;

    /**
     * MQTT连接属性配置对象
     */
    @Autowired
    public MqttConfigBean mqttConfigBean;

    /**
     * 初始化参数配置
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("初始化启动MQTT连接");
        this.connect();
    }


    /**
     * 用来连接服务器
     */
    private void connect() throws Exception {
        client = new MqttClient(mqttConfigBean.getHostUrl(), mqttConfigBean.getClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(mqttConfigBean.getUsername());
        options.setPassword(mqttConfigBean.getPassword().toCharArray());
        //是否清除session
        options.setCleanSession(true);
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(60);
        try {
            String[] msgtopic = mqttConfigBean.getMsgTopic();
            //订阅消息
            int[] qos = new int[msgtopic.length];
            for (int i = 0; i < msgtopic.length; i++) {
                qos[i] = 0;
            }
            client.setCallback(new TopMsgCallback(client, options, msgtopic, qos));
            client.connect(options);
            client.subscribe(msgtopic, qos);
            logger.info("MQTT连接成功:" + mqttConfigBean.getClientId() + ":" + client);
        } catch (Exception e) {
            logger.error("MQTT连接异常：" + e);
        }
    }


    /**
     * 重连
     *
     * @throws Exception
     */
    public void reConnect() throws Exception {
        if (null != client) {
            this.connect();
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            logger.info("topic:" + topic);
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public MqttTopic getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(MqttTopic mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    /**
     * @param :
     *            clientId
     * @param :
     *            topic
     * @param :
     *            msg
     * @Title: publishMessage
     * @Author : Hujh
     * @Date: 2019/10/29 17:14
     * @Description : 发布消息工具类
     * @Return : void
     */
    public void publishMessage(String topic, String msg,int qos) throws Exception {
        if (null == client) {
            connect();
        }
        MqttMessage message = new MqttMessage();
        message.setPayload(msg.getBytes("UTF-8"));
        message.setQos(qos);
        // 是否设置保留消息，若为true，后来的订阅者订阅该主题时仍可接收到该消息
        message.setRetained(true);
        MqttTopic mqttTopic = client.getTopic(topic);
        if (null == mqttTopic) {
            logger.error("topic 不存在!");
        }
        MqttDeliveryToken token;// Delivery:配送
        try {
            // 发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
            token = mqttTopic.publish(message);
            token.waitForCompletion();
            if (token.isComplete()) {
                logger.info("topic:{},消息发送成功!", topic);
            } else {
                logger.info("topic:{},消息发送失败!", topic);
            }
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            if (e.getReasonCode() == 32104) {
                client = null;
                publishMessage(topic, msg,qos);
            }
            e.printStackTrace();
        }
    }
}