package com.example.demo.util.zookeeper;

import org.apache.zookeeper.*;

/**
 * @author li wenwu
 * @date 2021/4/28 16:23
 */
public class ZooKeeperServer {
    private static String connectString = "139.224.235.119:2181";
    private static int sessionTimeOut = 2000;
    private static ZooKeeper zooKeeperClient;
    public static void main(String[] args)throws Exception {
        //连接zookeeper
        ZooKeeperServer zooKeeperServer = new ZooKeeperServer();
        zooKeeperServer.getConnection();
        //注册节点
        zooKeeperServer.regist(args[0]);
        //业务逻辑处理
        zooKeeperServer.bussiness();
    }

    private void bussiness()throws Exception {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void regist(String hostName)throws Exception {
        zooKeeperClient.create("/servers/server",hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName + "is online");
    }

    private void getConnection()throws Exception{
        zooKeeperClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
    
    
}
