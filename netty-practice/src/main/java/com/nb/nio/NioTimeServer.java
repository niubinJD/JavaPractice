package com.nb.nio;

public class NioTimeServer {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        MultiplexerTimeServer server = new MultiplexerTimeServer(PORT);
        new Thread(server).start();
    }
}
