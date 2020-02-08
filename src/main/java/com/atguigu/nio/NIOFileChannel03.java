package com.atguigu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {

        File file = new File("/Users/yiuleungchau/IdeaProjects/NettyPro/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel inputFileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        inputFileChannel.read(byteBuffer);

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/yiuleungchau/IdeaProjects/NettyPro/file02.txt");

        FileChannel outputFileChannel = fileOutputStream.getChannel();

        byteBuffer.flip();

        outputFileChannel.write(byteBuffer);

        fileOutputStream.close();
        fileInputStream.close();
    }
}
