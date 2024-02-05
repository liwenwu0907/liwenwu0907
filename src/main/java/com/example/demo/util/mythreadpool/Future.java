package com.example.demo.util.mythreadpool;

import java.util.concurrent.Semaphore;

public class Future<T> {
    private volatile boolean hasResp = false;
    private T t;
    private Semaphore semaphore = new Semaphore(1);
    public Future(){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public T get() throws InterruptedException {
//        等待对应的信号量，然后到对应任务编号，取出对应的值
        semaphore.acquire();
        return t;
    }

    protected void set(T t){
        this.t = t;
        semaphore.release();
    }

}
