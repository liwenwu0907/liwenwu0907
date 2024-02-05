package com.example.demo.util.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author li wenwu
 * @date 2020/11/27 14:32
 */
public class ClientHeartBeatHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> heartBeat;

    private InetAddress address;

    private static final String SUCCESS_KEY = "auth_success_key";


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接成功");
        address = InetAddress.getLocalHost();

        String ip = address.getHostAddress();

        String key = "1234";

        String auth = ip + "," + key;

        ctx.writeAndFlush(Unpooled.copiedBuffer(auth.getBytes()));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        if (heartBeat != null) {
            heartBeat.cancel(true);

            heartBeat = null;

        }

        ctx.fireExceptionCaught(cause);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] data = new byte[byteBuf.readableBytes()];//创建byte数组
        byteBuf.readBytes(data);//将数据读取到数组中
        //转String
        String str = new String(data, "utf-8");
        try {


            if (SUCCESS_KEY.equals(str)) {
                heartBeat = scheduled.scheduleWithFixedDelay(new HeartBeatTask(channelHandlerContext), 0, 5, TimeUnit.SECONDS);

            }


        } finally {
            ReferenceCountUtil.release(str);

        }
    }


    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;

        }


        @Override
        public void run() {
            try {
                RequestInfo requestInfo = new RequestInfo();

                requestInfo.setIp(address.getHostAddress());


                ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(requestInfo).getBytes()));

            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

}
