package com.atguigu.nio.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

// BossGroup is responsible for accepting request and construct a SocketChannel for each
// the constructed SocketChannel will then be registered to the one of the WorkerLoopGroup selector (selected by the next method)
// then run through a pipeline of handlers

public class NettyServer {
    public static void main(String[] args) throws Exception {

        // bossGroup handler connection accept
        // workGroup handler business logic
        // bossGroup and workerGroup default number of thread = cpu core * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        //
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            //
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // use this as channel in boss group
                    .option(ChannelOption.SO_BACKLOG, 128) // set initial queue connection count
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // set connection type, keep alive
                    // set handler to the workerGroup, handler() will set to the BossGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // define actions along with channel creation (getting SocketChannel from ServeSocketChannel in nio
                        // add handler to the pipeline
                        @Override
                        protected void initChannel (SocketChannel ch) throws Exception {
                            // can utilize channel to push message to different client since one channel correspond to one client
                            System.out.println("client socket channel hashcode = " + ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // set worker group handler

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
