package com.atguigu.nio.netty.tcp_solution;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("from server:");
        System.out.println("length = " + len);
        System.out.println("content = " + new String(content, CharsetUtil.UTF_8));

        System.out.println("server received packets count: " + (++count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // use a wrapper to transfer the item so that the server can know the length of the content
        for (int i = 0 ; i < 5 ; i++) {
            String msg = "Message" + i;
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            int length = msg.getBytes(CharsetUtil.UTF_8).length;

            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
