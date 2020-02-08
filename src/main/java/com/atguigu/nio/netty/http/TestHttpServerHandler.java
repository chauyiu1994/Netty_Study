package com.atguigu.nio.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

// handler for http object
// subclass of ChannelInboundHandler, Handle wrapper of source (HttpObject in this case)
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {

            // each batch of requests from a client will result in a new pipeline since http is stateless
            System.out.println("pipeline hashcode = " + ctx.pipeline().hashCode() + " handler hashcode = " + this.hashCode());

            System.out.println("msg type = " + msg.getClass());
            System.out.println("client ip address" + ctx.channel().remoteAddress());

            // resource filtering, when client requests for ico, no need to handle
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("requested favicon");
                return;
            }

            // response msg to client
            ByteBuf content = Unpooled.copiedBuffer("hello from server", CharsetUtil.UTF_8);

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/pain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);
        }
    }
}
