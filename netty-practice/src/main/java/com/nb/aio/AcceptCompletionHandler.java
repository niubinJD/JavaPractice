package com.nb.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandle> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandle attachment) {
        attachment.getChannel().accept(attachment, this);
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        result.read(allocate, allocate, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandle attachment) {
        exc.printStackTrace();
        attachment.getLatch().countDown();

    }
}
