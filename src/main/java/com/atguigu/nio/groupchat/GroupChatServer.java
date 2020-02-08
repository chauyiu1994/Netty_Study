package com.atguigu.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

// ServerSocketChannel is for managing (TCP) connection accepting event
// SocketChannel is for managing (TCP) IO event
// DatagramChannel is for managing (UDP) event
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {

        try {

            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {

        try {

            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        // construct new channel when connection request is received
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + " connected");
                        }
                        // read from buffer once data is ready
                        if (key.isReadable()) {

                            readData(key);
                        }

                        // remove key from selectedKeys
                        iterator.remove();
                    }
                } else {
                    System.out.println("waiting for connection");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void readData(SelectionKey key) {

        SocketChannel sc = null;
        try {

            sc = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = sc.read(buffer);

            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from client: " + msg);

                sendInfoToOtherClients(msg, sc);
            }

        } catch (Exception e) {

            try {
                System.out.println(sc.getRemoteAddress() + " offline...");

                // cancel registration
                key.cancel();
                // close channel
                sc.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }

    // re-forward the msg to OTHER clients (channel)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {

        System.out.println("server re-forwarding message...");

        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();

            if (targetChannel instanceof SocketChannel && targetChannel != self) {

                // the thread will be free once the data is written to the buffer, will not wait for the nic
                SocketChannel dest = ((SocketChannel) targetChannel);
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {

        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
