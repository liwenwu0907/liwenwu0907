package com.example.demo.util.netty;

import com.example.demo.util.PublicUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li wenwu
 * @date 2020/11/27 13:59
 */
public class ServerHeartBeatHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static Map<String, String> AUTH_IP_MAP = new HashMap<>();

    private static ThreadLocal<Boolean> session_local = new ThreadLocal<>();

    private static final String SUCCESS_KEY = "auth_success_key";
    private static final String AUTH_FAILURE = "authfailure!";
    private static final String CONNECT_FAILURE = "connectfailure";

    static {
        AUTH_IP_MAP.put("127.0.0.1", "1234");
        AUTH_IP_MAP.put("192.168.37.170", "1234");
        session_local.set(false);
    }

    private boolean auth(ChannelHandlerContext ctx, Object msg) {
        String[] rets = ((String) msg).split(",");

        String auth = AUTH_IP_MAP.get(rets[0]);

        if (auth != null && rets.length > 1 && auth.equals(rets[1])) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(SUCCESS_KEY.getBytes()));

            return true;

        } else {
            ctx.writeAndFlush(Unpooled.copiedBuffer(AUTH_FAILURE.getBytes()));
            return false;

        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] data = new byte[byteBuf.readableBytes()];//创建byte数组
        byteBuf.readBytes(data);//将数据读取到数组中
        //转String
        String str = new String(data, "utf-8");
        System.out.println("服务端收到数据：" + str);
        if(session_local.get()){
            RequestInfo info = PublicUtil.getBeanByJsonObj(str,RequestInfo.class);
            if(null != info){
                System.out.println("当前主机ip：" + info.getIp());
            }
            channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("inforeceived!".getBytes()));
        }else{
            if(auth(channelHandlerContext, str)){
                session_local.set(true);
            } else {
                channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(CONNECT_FAILURE.getBytes()));
            }

        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端连接成功");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
