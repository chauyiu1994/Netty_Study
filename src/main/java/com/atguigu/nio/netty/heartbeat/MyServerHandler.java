package com.atguigu.nio.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

// no need to receive any data
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    // handler event fired by the idleHandler
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "READER IDLE";
                    break;

                case WRITER_IDLE:
                    eventType = "WRITER IDLE";
                    break;

                case ALL_IDLE:
                    eventType = "ALL IDLE";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + " --get event-- " + eventType);
            System.out.println("Further Handling...");

            // close channel if idle
            ctx.channel().close();
        }
    }
}
