package com.example.demo;

import com.example.demo.util.MyTask;
import com.example.demo.util.MyThreadPool;
import com.example.demo.util.ThreadPoolConfig;
import com.example.demo.util.mythreadpool.Future;
import com.example.demo.util.mythreadpool.ThreadPool;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SpringBootTest
public class TestMyThreadPool {

    public static void ThreadPoolTest() throws Exception {
        //ThreadPool<Integer> t = ThreadPool.getThreadPool();
        ThreadPool<Integer> t = new ThreadPool<>(20,10,100);
        List<Future<Integer>> futures = new LinkedList<>();
        for (int i = 1; i <= 200; i++) {
            Integer data = i;
            Future<Integer> f = t.addTask(() -> {
                //执行逻辑代码
                System.out.println("正在执行代码：" + data);
                Thread.sleep(1000);
                return data;
            });
            futures.add(f);
        }
        for (Future<Integer> f : futures) {
            System.out.println("取出的结果：" + f.get());
        }
        t.close();
    }


    public static void MyThreadPoolTest() throws Exception {
        //创建3个线程的线程池
        MyThreadPool myThreadPool = new MyThreadPool(10, 100);
        for (int i = 0; i < 50; i++) {
            myThreadPool.execute(new MyTask(String.valueOf(i)));
        }
        System.out.println(myThreadPool);
        System.out.println(myThreadPool);
    }

    private static void countDownLatchTest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        ThreadPoolExecutor executor = threadPoolConfig.threadPoolTaskExecutor();
        //ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i=0;i<50;i++){
            Integer data = i;
            executor.execute(new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("正在执行：" + data);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));

        }
        countDownLatch.await();
        System.out.println("任务全部执行完成");
        executor.shutdown();
    }



    public static void main(String[] args) throws Exception {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        reentrantReadWriteLock.readLock();
        reentrantReadWriteLock.writeLock();
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();
//        long startTime = System.currentTimeMillis(); //获取开始时间
//        countDownLatchTest();
//        //ThreadPoolTest();
//        //MyThreadPoolTest();
//        long endTime = System.currentTimeMillis(); //获取结束时间
//        System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
//        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
//        new Thread(new Waiting(), "WaitingThread").start();
//// 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
//        new Thread(new Blocked(), "BlockedThread-1").start();
//        new Thread(new Blocked(), "BlockedThread-2").start();
//        Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
//        thread.setDaemon(true);
//        thread.start();
//        Thread.sleep(5000);
// sleepThread不停的尝试睡眠
//        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
//        sleepThread.setDaemon(true);
//// busyThread不停的运行
//        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
//        busyThread.setDaemon(true);
//        sleepThread.start();
//        busyThread.start();
//// 休眠5秒，让sleepThread和busyThread充分运行
//        TimeUnit.SECONDS.sleep(5);
//        sleepThread.interrupt();
//        busyThread.interrupt();
//        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
//        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
//// 防止sleepThread和busyThread立刻退出
//        SleepUtils.second(2);
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
// 将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } finally {
            out.close();
        }
    }

    static class Print implements Runnable {
        private PipedReader in;
        public Print(PipedReader in) {
            this.in = in;
        }
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException ex) {}
        }
    }


    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }
    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }

    // 该线程不断地进行睡眠
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }
    // 该线程在Waiting.class实例上等待
    static class Waiting implements Runnable {@Override
    public void run() {
        while (true) {
            synchronized (Waiting.class) {
                try {
                    Waiting.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }
    // 该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }

    static class SleepUtils {
        public static final void second(long seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
            }
        }
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("DaemonThread run.");
                SleepUtils.second(5);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }

}
