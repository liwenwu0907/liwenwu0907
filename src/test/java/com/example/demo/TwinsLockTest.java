package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.TwinsLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 * @author li wenwu
 * @date 2021/4/28 19:54
 */
@SpringBootTest
public class TwinsLockTest {

    @Test
    public void test() {
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(5);
        Collections.reverse(list);
        System.out.println(JSON.toJSONString(list));
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        TestMyThreadPool.SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        TestMyThreadPool.SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
// 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
// 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            TestMyThreadPool.SleepUtils.second(1);
            System.out.println();
        }
    }

    @Test
    public static void completableFuture() {
        CompletableFuture<String> task1 =
                CompletableFuture.supplyAsync(()->{
                    //自定义业务操作
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "1";
                });
        CompletableFuture<String> task6 =
                CompletableFuture.supplyAsync(()->{
                    //自定义业务操作
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "2";
                });
        CompletableFuture<Void> headerFuture = CompletableFuture.allOf(task1,task6);
//        CompletableFuture<Object> headerFuture = CompletableFuture.anyOf(task1,task6);
        Object object = headerFuture.join();
        System.out.println(JSON.toJSONString(object));
    }

    public static void main(String[] args) {
//        completableFuture();
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(thread1);
//        executorService.submit(thread2);
//        executorService.submit(thread3);
//        executorService.shutdown();

        final Thread thread1 = new Thread(()-> {
            System.out.println("1");
        });
        final Thread thread2 = new Thread(()-> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("2");
        });
        final Thread thread3 = new Thread(()-> {
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("3");
        });
        thread1.start();
        thread2.start();
        thread3.start();

    }

}
