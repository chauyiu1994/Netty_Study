package com.atguigu.nio.netty.protobuf2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

        switch (msg.getDataType()) {
            case StudentType:
                MyDataInfo.Student student = msg.getStudent();
                System.out.println("student id = " + student.getId() + ", student name = " + student.getName());
                break;

            case WorkerType:
                MyDataInfo.Worker worker = msg.getWorker();
                System.out.println("worker age = " + worker.getAge() + ", worker name = " + worker.getName());
                break;

            default:
                System.out.println("message type not correct");
        }
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
