package com.example.demo.util.mythreadpool;

import java.util.concurrent.Callable;

public class Task<T> {
    public Callable<T> getCallable() {
        return callable;
    }

    public void setCallable(Callable<T> callable) {
        this.callable = callable;
    }

    public Future<T> getFuture() {
        return future;
    }

    public void setFuture(Future<T> future) {
        this.future = future;
    }

    private Callable<T> callable;
    private Future<T> future;

    public Task(Callable<T> callable){
        this.callable = callable;
        this.future = new Future<>();
    }

}
