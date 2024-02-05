//package com.example.demo;
//
//import com.sun.xml.internal.ws.api.message.Packet;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import sun.plugin2.message.Message;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedTransferQueue;
//
///**
// * @author li wenwu
// * @date 2021/6/3 10:42
// */
//public class MsgQueueManager implements IMsgQueue {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MsgQueueManager.class);
//    /**
//     * 消息总队列
//     */
//    public final BlockingQueue<Message> messageQueue;
//
//    private MsgQueueManager() {
//        messageQueue = new LinkedTransferQueue<Message>();
//    }
//
//    public void put(Message msg) {
//        try {
//            messageQueue.put(msg);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    public Message take() {
//        try {
//            return messageQueue.take();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        return null;
//    }
//
//    /**
//     * 分发消息，负责把消息从大队列塞到小队列里
//     *
//     * @author tengfei.fangtf
//     */
//    static class DispatchMessageTask implements Runnable {
//        @Override
//        public void run() {
//            BlockingQueue<Message> subQueue;
//            for (; ; ) {// 如果没有数据，则阻塞在这里
//                Message msg = MsgQueueFactory.getMessageQueue().take();
//// 如果为空，则表示没有Session机器连接上来，
//// 需要等待，直到有Session机器连接上来
//                while ((subQueue = getInstance().getSubQueue()) == null) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//// 把消息放到小队列里
//                try {
//                    subQueue.put(msg);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }
//    }
//
//    /**
//     * 均衡获取一个子队列。
//     *
//     * @return
//     */
//    public BlockingQueue<Message> getSubQueue() {
//        int errorCount = 0;
//        for (;;) {
//            if (subMsgQueues.isEmpty()) {
//                return null;
//            }
//            int index = (int) (System.nanoTime() % subMsgQueues.size());
//            try {
//                return subMsgQueues.get(index);
//            } catch (Exception e) {
//// 出现错误表示，在获取队列大小之后，队列进行了一次删除操作
//                LOGGER.error("获取子队列出现错误", e);
//                if ((++errorCount) < 3) {
//                    continue;
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        IMsgQueue messageQueue = MsgQueueFactory.getMessageQueue();
//        Packet msg = Packet.createPacket(Packet64FrameType.
//                TYPE_DATA, "{}".getBytes(), (short) 1);
//        messageQueue.put(msg);
//    }
//
//}
