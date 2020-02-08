package com.atguigu.nio.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

// ChannelInitializer is also a InboundHandler
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // add handler to pipeline
        // pass message to the http decoder first, and the custom encoder
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 1. http decoder provided by netty
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        // 2. custom handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
