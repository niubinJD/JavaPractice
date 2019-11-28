package com.nb.aio;

public class AioTimeClient {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1", PORT)).start();
    }
}
