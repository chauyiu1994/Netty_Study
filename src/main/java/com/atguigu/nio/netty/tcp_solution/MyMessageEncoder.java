package com.atguigu.nio.netty.tcp_solution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol in, ByteBuf out) throws Exception {

        System.out.println("MyMessageEncoder encode");
        out.writeInt(in.getLen());
        out.writeBytes(in.getContent());
    }
}
