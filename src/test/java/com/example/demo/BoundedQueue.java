package com.example.demo;

import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author li wenwu
 * @date 2021/5/18 15:45
 */
public class BoundedQueue<T> {
    private Object[] items;
    // 添加的下标，删除的下标和数组当前数量
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();
    public BoundedQueue(int size) {
        items = new Object[size];
    }
    // 添加一个元素，如果数组满，则添加线程进入等待状态，直到有"空位"
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[addIndex] = t;
            if (++addIndex == items.length)
                addIndex = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
    // 由头部删除一个元素，如果数组空，则删除线程进入等待状态，直到有新添加元素
    @SuppressWarnings("unchecked")
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[removeIndex];
            if (++removeIndex == items.length)
                removeIndex = 0;
            --count;
            notFull.signal();
            return (T) x;
        } finally {
            lock.unlock();
        }
    }




    public static void main(String[] args)throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        for(int i=0;i<3;i++){
            System.out.println(i);
        }
        for(int i=0;i<3;i++){
            System.out.println(i);
        }
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.parseInt("0001111",3));
        System.out.println(Integer.parseInt("0001111"));
//        BoundedQueue boundedQueue = new BoundedQueue(3);
//        boundedQueue.add("1");
//        boundedQueue.add("2");
//        boundedQueue.add("3");
//        boundedQueue.remove();
//        boundedQueue.add("4");
//
//        System.out.println(boundedQueue);

//        final HashMap<String, String> map = new HashMap<String, String>(2);
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10000; i++) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String uuid = UUID.randomUUID().toString();
//                            System.out.println(uuid);
//                            map.put(uuid, "");
//                        }
//                    }, "ftf" + i).start();
//                }
//            }
//        }, "ftf");
//        t.start();
//        t.join();
//输出当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
    }

}
