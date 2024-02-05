package com.example.demo.util;

import java.util.Random;

public class MyTask implements Runnable {

    private String name;
    public String getName() {
        return name;
    }

    private Random random = new Random();

    public MyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            //执行逻辑代码，需要1s
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("task--- " + name + "---done!");
    }
}
