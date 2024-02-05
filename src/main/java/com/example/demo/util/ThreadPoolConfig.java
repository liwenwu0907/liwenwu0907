package com.example.demo.util;

import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author li wenwu
 * @date 2020/12/2 19:22
 */
@Configuration
public class ThreadPoolConfig {

    public ThreadPoolExecutor threadPoolTaskExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,0L, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        return executor;
    }
}
