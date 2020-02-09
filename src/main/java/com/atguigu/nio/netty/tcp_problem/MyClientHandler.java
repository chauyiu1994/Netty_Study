package com.atguigu.nio.netty.tcp_problem;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("from server: " + message);
        System.out.println("from server data size: " + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("connected");

        // there is no guarantee that these 10 packets is getting sent in what sequence
        // may be sent in 1 connection, may be sent in 2 connection...
        for (int i = 0 ; i < 10 ; i++) {

            ByteBuf buf = Unpooled.copiedBuffer("hello, server " + i, CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
