package com.atguigu.nio.inboundAndoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {


    // decode the message and pass to next inbound handler
    // this method will be executed many time until there is no readable byte in the buffer
    // AND ALSO WILL LEAD TO MANY TIME EXECUTIONS OF THE NEXT HANDLER!
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyByteToLongDecoder decode");

        if (in.readableBytes() >= 8) {
            Long msg = in.readLong();
            System.out.println("msg = " + msg);
            out.add(msg);
        } else {
            System.out.println("not readable yet");
        }
    }
}
