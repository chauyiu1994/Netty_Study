package com.atguigu.nio.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        System.out.println("server received: " + msg.text());

        // must response in TextWebSocketFrame format
        ctx.channel().writeAndFlush(new TextWebSocketFrame("server time: " + LocalDateTime.now() + " " + msg.text()));
    }

    // triggered immediately after connection is accepted by server and SocketChannel is created
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        // asLongText = unique, asShortText = not unique
        System.out.println("handlerAdded " + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        System.out.println("handlerRemoved " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("Exception occurred");
        ctx.close();
    }
}
