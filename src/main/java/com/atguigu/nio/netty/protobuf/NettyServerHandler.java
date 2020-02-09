package com.atguigu.nio.netty.protobuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student student) throws Exception {

        System.out.println("from client: " + student.getId() + ", name = " + student.getName());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // write to buffer and send to client through channel
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client after read~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        // also close channel
        ctx.close();
    }
}
