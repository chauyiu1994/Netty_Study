package com.atguigu.nio.netty.tcp_solution;

import lombok.Data;

@Data
public class MessageProtocol {
    private int len;
    private  byte[] content;

}
