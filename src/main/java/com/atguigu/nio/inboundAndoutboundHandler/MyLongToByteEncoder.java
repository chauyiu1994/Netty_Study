package com.atguigu.nio.inboundAndoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

// This encode will determine whether the outbound message is a Long, if not, just pass to the next handler
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {

        System.out.println("MyLongToByteEncoder encode");
        System.out.println("msg = " + msg);
        out.writeLong(msg);
    }
}
