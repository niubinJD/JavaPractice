package com.nb.bio.pool;

import com.nb.bio.BioTimeServerHandler;
import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * timeserver线程池版
 * <p>
 * 输入对象inputstream是阻塞读取，直到有数据可读,数据读取完成或发生异常，结束阻塞,在阻塞期间，其他接入消息在消息队列中等待
 * <p>
 * 输出outputStream也是同步阻塞输出，直到数据写入完成或发生异常，否则无限期阻塞
 * <p>
 * bio输入输出都是同步阻塞的，其对消息的处理取决于与客户端之间的I/O处理速度和网络状况
 */
public class BioPoolTimeServer {

    private static final Logger log = LogManager.getLogger(BioPoolTimeServer.class);

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        int port = PORT;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 使用默认值
                log.error(e.getMessage(), e);
            }
        }

        @Cleanup ServerSocket server = new ServerSocket(port);
        log.info("the time server is start in port:{}", port);
        Socket socket = null;
        BioTimeServerHandlerExecutePool pool = new BioTimeServerHandlerExecutePool(50, 500);

        while (true) {
            socket = server.accept();
           pool.execute(new BioTimeServerHandler(socket));// 使用线程池来处理客户端接入
        }
    }
}
