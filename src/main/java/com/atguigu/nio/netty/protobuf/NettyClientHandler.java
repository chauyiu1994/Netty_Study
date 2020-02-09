package com.atguigu.nio.netty.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // triggered when channel ready
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("Mary123").build();
        ctx.writeAndFlush(student);
    }

    // read data when ready
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("server says: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("server ip address: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
