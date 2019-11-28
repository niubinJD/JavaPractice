package com.nb.aio;

import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

@Data
public class AsyncTimeServerHandle implements Runnable {
    private int port;

    private CountDownLatch latch;
    private AsynchronousServerSocketChannel channel;

    public AsyncTimeServerHandle(int port) {
        this.port = port;

        try {
            channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(port));
            System.out.println("time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {

        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        channel.accept(this, new AcceptCompletionHandler());
    }
}
