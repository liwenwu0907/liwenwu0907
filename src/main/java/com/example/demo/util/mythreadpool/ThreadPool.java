package com.example.demo.util.mythreadpool;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPool<T> {

    private int maxRunThreadNum;
    private int maxWaitTaskNum;
    private int minActiveThreadNum;
    private int addTaskTimeout;
    private Vector<Worker> workerList;
    private static ThreadPool threadPool;
    private LinkedBlockingQueue<Task<T>> taskBlockingDeque;
    private AtomicLong workingNum = new AtomicLong();
    //初始化参数
    private ThreadPool(){
        maxRunThreadNum = 10;
        maxWaitTaskNum = 50;
        minActiveThreadNum = 5;
        addTaskTimeout = 5;
        workerList = new Vector<>(maxRunThreadNum);
        taskBlockingDeque = new LinkedBlockingQueue<>(maxWaitTaskNum);
        createWorker(minActiveThreadNum);

    }

    public ThreadPool(int maxRunThreadNum,int minActiveThreadNum,int maxWaitTaskNum){
        this.maxRunThreadNum = maxRunThreadNum;
        this.maxWaitTaskNum = maxWaitTaskNum;
        this.minActiveThreadNum = minActiveThreadNum;
        this.addTaskTimeout = 5;
        this.workerList = new Vector<>(maxRunThreadNum);
        this.taskBlockingDeque = new LinkedBlockingQueue<>(maxWaitTaskNum);
        createWorker(minActiveThreadNum);

    }

    public synchronized static <T> ThreadPool<T> getThreadPool(){
        if(threadPool==null){
            threadPool = new ThreadPool<T>();
        }
        return threadPool;
    }

    public synchronized void close(){
        for (Worker worker:workerList){
            worker.close();
            worker.interrupt();
        }
    }
    //    添加线程
    private synchronized boolean createWorker(int num){
        if(workerList.size()+num<maxRunThreadNum){
            for (int i=0;i<num;i++) {
                Worker worker = new Worker();
                workerList.add(worker);
                worker.start();
            }
            return true;
        }
        return false;
    }

    public Future<T> addTask(Callable<T> callable) throws Exception {
//        TODO::考虑 计算当前启动的worker和最大worker之间的数量 以及当前正在运行的和启动的数量，启动更多worker
        Task<T> task = new Task<>(callable);
        boolean flag = taskBlockingDeque.offer(task,addTaskTimeout, TimeUnit.SECONDS);
        if(!flag) return null;
        return task.getFuture();
    }

    public class Worker extends Thread{

        private boolean closeFlag = false;


        @Override
        public void run() {
            while(!closeFlag){
                if(taskBlockingDeque.isEmpty()){
//                    TODO::新增信号量，等待唤醒
                }else{
                    Task<T> task = taskBlockingDeque.poll();
                    if(task!=null){
                        Callable<T> callable = task.getCallable();
                        Future<T> future = task.getFuture();
                        try {
                            workingNum.getAndDecrement();
                            T t = callable.call();
//                            System.out.println("get t="+t);
                            future.set(t);
                            workingNum.decrementAndGet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        }

        public void close(){
            closeFlag = true;
        }
    }

}
