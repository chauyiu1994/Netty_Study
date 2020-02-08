package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("/Users/yiuleungchau/IdeaProjects/NettyPro/dogs_1280p_0.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/yiuleungchau/IdeaProjects/NettyPro/dogs_1280p_0_copy.jpg");

        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel targetCh = fileOutputStream.getChannel();

        targetCh.transferFrom(sourceCh, 0, sourceCh.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
