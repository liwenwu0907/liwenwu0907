package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class DemoApplicationTests {

    @Test
    static void javacvTest(){
        String originPath = "http://wlw-x.oss-cn-hangzhou.aliyuncs.com/c4073baffd254c3d8a6642b8cd0bcbbb.mp4";
        String jpgPath = "D://test.jpg";
        File videoFile = new File(originPath);
        if(videoFile.exists()){

        }else{
            System.out.println("视频不存在");
        }
    }

    public static synchronized Runnable sychionzed(){
        return () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行完毕");
        };
    }

    public static Runnable sychionzed(String bh){

        return () -> {
            synchronized (bh){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行完毕"+bh);
            }
        };
    }

    public static void main(String[] args) {
        new Thread(()->sychionzed().run()).start();
        new Thread(()->sychionzed().run()).start();
        new Thread(()->sychionzed("2222").run()).start();
        new Thread(()->sychionzed("3333").run()).start();
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,8,5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
//        try {
//            threadPoolExecutor.execute(sychionzed());
//            threadPoolExecutor.execute(sychionzed());
//            threadPoolExecutor.execute(sychionzed("2222"));
//            threadPoolExecutor.execute(sychionzed("3333"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        threadPoolExecutor.shutdown();
//        // 获取Java线程管理MXBean
//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//// 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
//        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
//// 遍历线程信息，仅打印线程ID和线程名称信息
//        for (ThreadInfo threadInfo : threadInfos) {
//            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.
//                    getThreadName());
//        }

    }


}
