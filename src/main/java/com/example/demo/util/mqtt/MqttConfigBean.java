package com.example.demo.util.mqtt;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Xiao yunhuan
 * @date 2020/11/24 14:07
 */
//@Component
public class MqttConfigBean {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.topic}")
    private String msgTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String[] getMsgTopic() {
        String[] msgTopics = msgTopic.split(",");
        return msgTopics;
    }

    public int getCompletionTimeout() {
        return completionTimeout;
    }
}
