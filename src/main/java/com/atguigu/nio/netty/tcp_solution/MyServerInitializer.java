package com.atguigu.nio.netty.tcp_solution;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

// this is run by the workerGroup
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        System.out.println(Thread.currentThread().getName());

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline
                .addLast(new MyMessageDecoder())
                .addLast(new MyMessageEncoder())
                .addLast(new MyServerHandler());
    }
}
