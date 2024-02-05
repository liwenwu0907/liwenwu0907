package com.example.demo.util.flowcontrol;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class FlowControlTest {

    @Autowired
    private RedisRateLimiterFactory redisRateLimiterFactory;

    public void test(String[] args) throws FlowException {
        RedisRateLimiter redisRateLimiter = redisRateLimiterFactory.build("key", 10, 10, 10);
        if(!redisRateLimiter.tryAcquire("key", 0, TimeUnit.SECONDS)){
            throw new FlowException("限流了");
        }
    }

    public static void main(String[] args)throws Exception {
        //1代表一秒最多多少个
        RateLimiter rateLimiter = RateLimiter.create(10);
        for(int i=0;i<100;i++){
            int num = i;
            new Thread(()-> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(rateLimiter.tryAcquire());

            }).start();
        }
//        List<Runnable> tasks = new ArrayList<Runnable>();
//        for (int i = 0; i < 10; i++) {
//            int num = i;
//            tasks.add(() -> System.out.println(num));
//        }
//        ExecutorService threadPool = Executors.newCachedThreadPool();
//        for (Runnable runnable : tasks) {
//            System.out.println("等待时间：" + rateLimiter.acquire());
//            threadPool.execute(runnable);
//        }
    }

}
