package com.atguigu.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String filename = "oh-my-zsh-agnoster-fcamblor-master.zip";

        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        long startTime = System.currentTimeMillis();

        // use zero-copy to transfer the copy
        // max 8mb in windows
        // DMA copy to ram -> CPU copy to protocol engine instead of traditional 4 stage copy
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("transferred byte count: " + transferCount + " time passes: " + (System.currentTimeMillis() - startTime));
    }
}
