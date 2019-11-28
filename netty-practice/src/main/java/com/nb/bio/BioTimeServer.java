package com.nb.bio;

import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 时间服务-bio实现
 */
public class BioTimeServer {
    private static final Logger log = LogManager.getLogger(BioTimeServer.class);

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        int port = PORT;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                port = PORT;
            }
        }

        @Cleanup ServerSocket server = new ServerSocket(port);
        log.info("the time server is start in port: {}", port);
        Socket socket = null;
        while (true) {
            socket = server.accept();
            new Thread(new BioTimeServerHandler(socket)).start(); // 每一个新客户端接入都需要创建一个线程来处理
        }

    }
}