package com.example.demo.util.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author li wenwu
 * @date 2021/4/27 17:42
 */
public class TestZooKeeper {

    private static String connectString = "139.224.235.119:2181";
    private static int sessionTimeOut = 2000;
    private static ZooKeeper zooKeeperClient;

    public static void main(String[] args)throws Exception {
        zooKeeperClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    List<String> list = zooKeeperClient.getChildren("/",true);
                    list.forEach((str)-> System.out.println(str));
                    System.out.println("---------------------结束------------------");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        createNode();
//        getDataAndWatch();
//        exist();
    }

    /**
     * 创建节点
     * @author li wenwu
     * @date 2021/4/28
     * @param
     * @return
     */
    private static void createNode()throws Exception{
        String path = zooKeeperClient.create("/test","diaochan".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    /**
     * 监听节点
     * @author li wenwu
     * @date 2021/4/28
     * @param
     * @return
     */
    private static void getDataAndWatch()throws Exception{
        List<String> list = zooKeeperClient.getChildren("/",true);
        list.forEach((str)-> System.out.println(str));
        Thread.sleep(1000000);
    }

    /**
     * 判断节点是否存在
     * @author li wenwu
     * @date 2021/4/28
     * @param
     * @return
     */
    private static void exist()throws Exception{
        Stat stat = zooKeeperClient.exists("/test",false);
        System.out.println(stat == null?"not exist":"exist");
    }


}
