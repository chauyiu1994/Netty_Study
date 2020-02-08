package com.atguigu.nio.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {

        // create a buffer of capacity 10 bytes
        // it keep two pointers: readerIndex and writerIndex
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0 ; i < 10 ; i++) {

            buffer.writeByte(i);
        }

        System.out.println("capacity = " + buffer.capacity());

        // no need to flip for netty buffer
        for (int i = 0 ; i < buffer.capacity() ; i++) {

            System.out.println(buffer.readByte());
        }
    }
}
