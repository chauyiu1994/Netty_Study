package com.atguigu.nio;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {

        // bio is based on stream (reading constantly, so blocking and consume cpu
        // nio is based on block, cpu read it when the block (buffer) of data is loaded into ram
        // buffer can be written from and read to, while stream is not one directional
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0 ; i < intBuffer.capacity() ; i++) {
            intBuffer.put(i * 2);
        }

        // change write mode to read mode or opposite
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
