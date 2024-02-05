package com.example.demo.util.netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**通道组池，管理所有websocket连接
 * @author Xiao yunhuan
 * @date 2020/11/25 11:05
 */
public class MyChannelHandlerPool {
    public MyChannelHandlerPool(){}

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
