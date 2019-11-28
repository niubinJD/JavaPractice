package com.nb.nio;

public class NioTimeClient {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new Thread(new NioTimeClientHandler("127.0.0.1", PORT)).start();
    }
}
