package com.example.demo;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author li wenwu
 * @date 2021/5/26 15:52
 */
public class ExchangerTest {

    private static final Exchanger<String> exgr = new Exchanger<String>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    public static void main(String[] args)throws Exception {
        List<Map> list = new ArrayList();
        Map map = new HashMap();
        map.put("code","ROLE_STATION_MANAGER2");
        list.add(map);
        Map map2 = new HashMap();
        map2.put("code","ROLE_FINANCER");
        list.add(map2);
        boolean flag = list.stream().anyMatch(a -> null != a.get("code") && StringUtils.equals("ROLE_STATION_MANAGER",a.get("code").toString()));
        System.out.println(flag);
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                String A = "银行流水A"; // A录入银行流水数据
                try {
                    exgr.exchange(A);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "银行流水B"; // B录入银行流水数据
                    String A = exgr.exchange(B);
                    System.out.println("A和B数据是否一致：" + A.equals(B) + "，A录入的是："+ A + "，B录入是：" + B);
                } catch (InterruptedException e) {
                }
            }
        });
        threadPool.shutdown();

        System.out.println(executionTask("aabbcc"));
    }

    private final static ConcurrentMap<Object, Future<String>> taskCache = new ConcurrentHashMap<Object, Future<String>>();
    private static String executionTask(final String taskName) throws ExecutionException, InterruptedException {
        while (true) {
            Future<String> future = taskCache.get(taskName); // 1.1,2.1
            if (future == null) {
                Callable<String> callable = () ->{
                    return "";
                };
                Callable<String> task = new Callable<String>() {
                    public String call() throws InterruptedException {
                        return taskName;
                    }
                }; // 1.2创建任务
                FutureTask<String> futureTask = new FutureTask<String>(task);
                future = taskCache.putIfAbsent(taskName, futureTask); // 1.3
                if (future == null) {
                    future = futureTask;
                    futureTask.run(); // 1.4执行任务
                    //((FutureTask<String>) future).run();
                }
            }
            try {
                return future.get(); // 1.5,2.2线程在此等待任务执行完成
            } catch (CancellationException e) {
                taskCache.remove(taskName, future);
            }
        }
    }

}
