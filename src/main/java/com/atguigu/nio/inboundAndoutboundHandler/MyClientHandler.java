package com.atguigu.nio.inboundAndoutboundHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("server ip: " + ctx.channel().remoteAddress());
        System.out.println("received: " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("MyClientHandler send data");
        // let encodeHandler to handler the encoding
        // convert inbound to outbound
        ctx.writeAndFlush(1234567L);

        // if you send a string instead of long, it will not be handled y the encoder, but will still get sent
        // ctx.writeAndFlush(Unpooled.copiedBuffer("sdsadsdfdsfbdfgbf", CharsetUtil.UTF_8));
    }
}
