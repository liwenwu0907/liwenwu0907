package com.example.demo.util.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * NettyServer Netty服务器配置
 *
 * @author Xiao yunhuan
 * @date 2020/11/25 11:02
 */
public class NettyServer {


    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    /**
     * 对于ChannelOption.SO_BACKLOG的解释：
     * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept从B队列中取出完成了三次握手的连接。A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的连接数并无影响，backlog影响的只是还没有被accept取出的连接
     */
    public void start() throws Exception {
        //用于处理服务器端接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //进行网络通信（读写）
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap sb = new ServerBootstrap();
            //设置TCP缓冲区
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            //绑定两个线程组
            sb.group(group, bossGroup)
                    //指定NIO的模式
                    .channel(NioServerSocketChannel.class)
                    // 绑定监听端口
                    .localAddress(this.port)
                    //配置具体的数据处理方式
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("收到新连接");
                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            //ch.pipeline().addLast(new HttpServerCodec());
                            //以块的方式来写的处理器
                            //ch.pipeline().addLast(new ChunkedWriteHandler());
                            //ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            //ch.pipeline().addLast(new MyWebSocketHandler());
                            ch.pipeline().addLast(new ServerHeartBeatHandler());
                            //5秒未与服务端连接则断开
                            //ch.pipeline().addLast(new ReadTimeoutHandler(5));
                            //ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10));
                        }
                    });
            // 服务器异步创建绑定
            ChannelFuture cf = sb.bind(new InetSocketAddress(port)).sync();
            System.out.println(NettyServer.class + "服务端启动正在监听： " + cf.channel().localAddress());
            // 关闭服务器通道
            cf.channel().closeFuture().sync();
        } finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }
}
