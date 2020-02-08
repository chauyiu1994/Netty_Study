package com.atguigu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        serverSocketChannel.configureBlocking(false);

        // listen to the event where a new connection is accepted
        // event type:
        // 1. OP_CONNECT -> new connection is established
        // 2. OP_ACCEPT -> the channel is ready to accept another connection
        // 3. OP_READ
        // 3. OP_WRITE
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // channel will put the event in a native queue for the selector to select and put in the selectedKey property
            if (selector.select(1000) == 0) {
                System.out.println("waited 1 second, no connection");
                continue;
            }

            // key store info of event and corresponding channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // operation depend on fetched events
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {

                    // since a client is acceptable confirmed, this accept operation will not result in blocking
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    // every channel must be non-blocking
                    socketChannel.configureBlocking(false);
                    System.out.println("socket channel established");


                    // listen to the read event of this channel and create a new Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {

                    // get channel from key
                    SocketChannel channel = (SocketChannel) key.channel();
                    // get buffer from channel
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from client " + new String(buffer.array()));
                }

                // remove all the selectionKeys
                keyIterator.remove();
            }
        }
    }
}
