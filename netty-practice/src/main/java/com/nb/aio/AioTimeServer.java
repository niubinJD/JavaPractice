package com.nb.aio;

public class AioTimeServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        AsyncTimeServerHandle server = new AsyncTimeServerHandle(PORT);
        new Thread(server).start();
    }
}
