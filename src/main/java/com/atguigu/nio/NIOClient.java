package com.atguigu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if (!socketChannel.connect(inetSocketAddress)) {

            while (!socketChannel.finishConnect()) {
                System.out.println("connection not yet established, doing other thing");
            }
        }

        String string = "hello world";
        // construct buffer by string length
        ByteBuffer buffer = ByteBuffer.wrap(string.getBytes());

        socketChannel.write(buffer);

        System.in.read();
    }
}
