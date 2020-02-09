package com.atguigu.nio.inboundAndoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

// can be slower than MessageDecoder in some cases
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyByteToLongDecoder2 decode");

        // the ReplayingDecoder will manage whether the readable bytes is enough
        out.add(in.readLong());
    }
}
