package com.atguigu.nio.netty.protobuf2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyClient {
    public static void main(String[] args) throws Exception {

        // one group is enough ofr client
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            // client use Bootstrap instead of ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("...client server ok");

            // netty async model
            // connect to server
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            channelFuture.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }

    }
}
