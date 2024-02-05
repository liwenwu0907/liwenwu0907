package com.example.demo.util.proxy;

public class AliSmsService {

    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }

    public static void main(String[] args) {
        AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("java");
    }
}
