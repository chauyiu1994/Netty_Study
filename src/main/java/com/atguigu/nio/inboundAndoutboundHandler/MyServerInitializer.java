package com.atguigu.nio.inboundAndoutboundHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MyByteToLongDecoder2())
                .addLast(new MyLongToByteEncoder())
                .addLast(new MyServerHandler());
    }
}
