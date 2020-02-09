package com.atguigu.nio.netty.tcp_solution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {

    // convert the ByteBuf to MessageProtocol
    // ReplayingDecoder correctly chop the message into correct bit and avoid the problem of sticking even if the packets is sent in the same connection
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyMessageDecoder decode");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);

        out.add(messageProtocol);
    }
}
