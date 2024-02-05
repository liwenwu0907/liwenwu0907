package com.example.demo.util.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li wenwu
 * @date 2021/4/28 16:37
 */
public class ZooKeeperClient {

    private static String connectString = "139.224.235.119:2181";
    private static int sessionTimeOut = 2000;
    private static ZooKeeper zooKeeperClient;

    public static void main(String[] args)throws Exception {
        ZooKeeperClient zooKeeperClient = new ZooKeeperClient();
        //创建连接
        zooKeeperClient.getConnection();
        //注册监听
        zooKeeperClient.getChildren();
        //业务处理
        zooKeeperClient.business();

    }

    private void business() throws Exception{
        Thread.sleep(Long.MAX_VALUE);
    }

    private void getChildren()throws Exception {
        List<String> children = zooKeeperClient.getChildren("/servers",true);
        List<String> hosts = new ArrayList<>();
        for(String child:children){
            byte[] data = zooKeeperClient.getData("/servers/" + child, false, null);
            hosts.add(new String(data));
        }
        System.out.println(hosts);
    }

    private void getConnection()throws Exception{
        zooKeeperClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getChildren();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
