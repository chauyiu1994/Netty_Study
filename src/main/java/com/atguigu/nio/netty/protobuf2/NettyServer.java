package com.atguigu.nio.netty.protobuf2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

// BossGroup is responsible for accepting request and construct a SocketChannel for each
// the constructed SocketChannel will then be registered to the one of the WorkerLoopGroup selector (selected by the next method)
// then run through a pipeline of handlers

public class NettyServer {
    public static void main(String[] args) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            //
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // define actions along with channel creation (getting SocketChannel from ServeSocketChannel in nio
                        // add handler to the pipeline
                        @Override
                        protected void initChannel (SocketChannel ch) throws Exception {

                            ch.pipeline()
                                    // must add ProtobufDecoder if using ProtoBuf
                                    .addLast("decoder", new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()))
                                    .addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("...Server instance is ready");

            // bind to a port (non-blocking)
            // start the server
            // sync = block until future ready
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            System.out.println("After future sync");

            // You can add listener to ChannelFuture
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("connect success");
                    } else {
                        System.out.println("connect fail");
                    }
                }
            });

            //
            channelFuture.channel().closeFuture().sync();



        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
