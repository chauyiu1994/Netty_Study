package com.atguigu.nio.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf02 {
    public static void main(String[] args) {

        // create a buffer of the corresponding capacity and write to it
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello, world", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()) {

            byte[] content = byteBuf.array();

            System.out.println(new String(content, CharsetUtil.UTF_8));

            System.out.println("byteBuf = " + byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());

            System.out.println(byteBuf.readByte());
            System.out.println(byteBuf.readerIndex());
        }
    }
}
