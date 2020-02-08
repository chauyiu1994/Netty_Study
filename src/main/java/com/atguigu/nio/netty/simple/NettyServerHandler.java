package com.atguigu.nio.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

// only handle inbounding
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // ChannelHandlerContext keep reference of handler related info, it contains pipeline, channel, ip address... (one context per ctx)
    // each pipeline can contain a number of handlers
    // msg is the msg sent by client
    // this method is triggered AFTER the msg is ready to read, it is passed in with the form of ByteBuf
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // assign the blocking task to the EventLoop associated with this channel instead of go blocking
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client time consuming 1~", CharsetUtil.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // put in the same thread, so will return after 10 + 20 seconds
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(20000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client time consuming 2~", CharsetUtil.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(5000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client time consuming 3~", CharsetUtil.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("go on ...");

//        System.out.println("server read thread: " + Thread.currentThread().getName());
//        System.out.println("server ctx = " + ctx);
//        System.out.println("check relationship between channel and pipeline:");
//        Channel channel = ctx.channel();
//        // pipeline is like a double-linked-list of handlers
//        ChannelPipeline pipeline = ctx.pipeline();
//
//        // the msg is given in the form of ByteBuff at this time, since it is ready to read
//        // cast msg into a ByteBuf (different from nio ByteBuff) (higher efficiency)
//        // decode by utf-8
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("client says: " + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("client ip address: " + channel.remoteAddress());
    }

    // triggered when data read completed
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // write to buffer and send to client through channel
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client after read~", CharsetUtil.UTF_8));
    }

    // close channel if exception occurred
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        // also close channel
        ctx.close();
    }
}
