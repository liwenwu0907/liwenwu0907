package com.example.demo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerExample {

    public static void main(String[] args) {
        // 创建共享的并发队列
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
//        Queue<Integer> queue = new LinkedBlockingQueue<>();
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000));
        AtomicInteger ai = new AtomicInteger(0);
        // 提交生产者和消费者任务
        for (int i = 0; i < 2; i++) {
            executor.submit(new Producer(queue, ai));
        }
        for (int i = 0; i < 2; i++) {
            executor.submit(new Consumer(queue));
        }
        // 关闭线程池
        executor.shutdown();
    }

    // 生产者任务
    static class Producer implements Runnable {
        private final Queue<Integer> queue;
        private final AtomicInteger value;

        public Producer(Queue<Integer> queue, AtomicInteger value) {
            this.value = value;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                // 生产新的数据并添加到队列
                int val = value.incrementAndGet();
                System.out.println("Produced: " + val);
                queue.offer(val);
                // 模拟生产耗时
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 消费者任务
    static class Consumer implements Runnable {
        private final Queue<Integer> queue;

        public Consumer(Queue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                // 从队列中取出数据并消费
                Integer value = queue.poll();
                if (value != null) {
                    System.out.println("Consumed: " + value);
                }
                // 模拟消费耗时
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
