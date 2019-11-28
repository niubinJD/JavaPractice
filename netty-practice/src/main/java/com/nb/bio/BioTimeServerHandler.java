package com.nb.bio;

import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class BioTimeServerHandler implements Runnable {
    private static final Logger log = LogManager.getLogger(BioTimeServerHandler.class);

    private Socket socket;

    public BioTimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            @Cleanup Socket socket = this.socket;
            @Cleanup BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            @Cleanup PrintWriter out = new PrintWriter(socket.getOutputStream());
            String currentTime = null;
            String body = null;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                log.info("time server receive body is {}", body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                out.println(currentTime);
                out.flush();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}