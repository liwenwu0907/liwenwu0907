package com.example.demo.util.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author li wenwu
 * @date 2020/11/27 14:19
 */
public class NettyClient {

    private final int port;

    public NettyClient(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        try {
            new NettyClient(12345).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {

        //进行网络通信（读写）
        EventLoopGroup group = new NioEventLoopGroup();
        //客户端
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                //.localAddress(this.port)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ClientHeartBeatHandler());
            }
        });
        //客户端异步创建绑定
        ChannelFuture cf = b.connect(new InetSocketAddress("127.0.0.1",port)).sync();
        //可以创建多个客户端
        //ChannelFuture cf2 = b.connect("127.0.0.1",2223).sync();
        System.out.println(NettyClient.class + "客户端启动正在监听： " );
        //向服务端发送数据   Unpooled: netty提供的工具类,可以将其他类型转为buf类型
        //cf.channel().writeAndFlush(Unpooled.copiedBuffer("我是客户端".getBytes()));
        // 关闭服务器通道
        cf.channel().closeFuture().sync();
        // 释放线程池资源
        group.shutdownGracefully().sync();

    }

}
