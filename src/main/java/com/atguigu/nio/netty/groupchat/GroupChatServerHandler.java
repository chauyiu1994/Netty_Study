package com.atguigu.nio.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

// accepting String as msg
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // manage all the current channel to redirect the msg
    // use an extra event loop to manage the channelGroup
    // one task is picked up by one event loop, so no sync is needed
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // triggered immediately after connection is accepted by server and SocketChannel is created
    // add channel to the channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        System.out.println("Handler added for " + channel.remoteAddress());

        channelGroup.add(channel);
        // notify other users that a client is connect
        // this method write to all channel managed
        channelGroup.writeAndFlush(LocalDateTime.now().toString() + " [client] " + channel.remoteAddress() + " connected!\n");
    }

    // handlerRemoved will automatically remove channel from ChannelGroup
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(LocalDateTime.now().toString() + " [client] " + channel.remoteAddress() + " disconnected\n");
        System.out.println("channelGroup size: " + channelGroup.size());
    }

    // triggered when channel is active
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(LocalDateTime.now().toString() + " " + ctx.channel().remoteAddress() + " online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(LocalDateTime.now().toString() + " " + ctx.channel().remoteAddress() + " offline");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        // redirect the msg
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(LocalDateTime.now().toString() + " [client] " + channel.remoteAddress() + " say: " + msg + "\n");
            } else {
                ch.writeAndFlush(LocalDateTime.now().toString() + " you just sent: " + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }
}
