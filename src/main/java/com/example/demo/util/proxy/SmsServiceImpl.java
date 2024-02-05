package com.example.demo.util.proxy;

import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService{

    @Override
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }

    public static void main(String[] args) {
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("java");
    }
}
