//package com.example.demo.util;
//
//import io.seata.common.util.IdWorker;
//
//public class IdWorkerUtil {
//
//    public static void main(String[] args) {
//
//        for (int j = 0; j < 3; j++) {
//            new Thread(() -> {
//                IdWorker idWorker = new IdWorker(1023L);
//                for (int i = 0; i < 20; i++) {
//                    System.out.println(idWorker.nextId());
//                }
//            }).start();
//        }
//
//
//    }
//}
