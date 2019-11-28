package com.nb.bio;

import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class BioTimeClient {
    private static final Logger log = LogManager.getLogger(BioTimeClient.class);

    public static void main(String[] args) throws IOException {
        int port = 8080;
        @Cleanup Socket socket = new Socket("127.0.0.1", port);
        @Cleanup BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        @Cleanup PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("QUERY TIME ORDER");
        out.flush();
        log.info("send order to server success");
        String resp = in.readLine();
        log.info("now is {}", resp);

    }
}