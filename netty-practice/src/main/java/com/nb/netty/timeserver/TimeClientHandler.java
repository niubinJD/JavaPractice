package com.nb.netty.timeserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private final ByteBuf message;
    public TimeClientHandler(){
        byte[] bytes = "QUERY TIME ORDER".getBytes();
        message = Unpooled.buffer(bytes.length);
        message.writeBytes(bytes);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf b = (ByteBuf) msg;
        byte[] bytes = new byte[b.readableBytes()];
        b.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("now time is " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        ctx.writeAndFlush(message);
    }
}
