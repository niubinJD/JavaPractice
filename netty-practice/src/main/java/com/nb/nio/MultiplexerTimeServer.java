package com.nb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {
    private Selector selector;

    private ServerSocketChannel channel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定端口
     *
     * @param port
     */
    public MultiplexerTimeServer(int port) {

        try {
            selector = Selector.open(); //创建一个选择器
            channel = ServerSocketChannel.open(); // 创建一个socket服务通道
            channel.configureBlocking(false); // 设置通道为非阻塞模式
            channel.socket().bind(new InetSocketAddress(port), 1024); // 配置socket server监听端口， 及最大等待队列长度(backlog)

            channel.register(selector, SelectionKey.OP_ACCEPT); // 将sockerchannel注册到selector 上监听accept事件

            System.out.println("time server is start in port: " + port);

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("system exit");
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }


                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            // 处理新接入请求
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel socket = ssc.accept();
                socket.configureBlocking(false);
                socket.register(selector, SelectionKey.OP_READ); // 注册新接入请求到selector,并监听读事件
            }
            if (key.isReadable()) { // 处理读事件
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) { // 读到字节，对消息进行处理
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("time server receive order is " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                    return;
                }
                if (readBytes < 0) { // 链路已关闭,需要关闭资源
                    key.cancel();
                    sc.close();
                }
                // readBytes == 0  没有读取到消息，不做处理
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}
