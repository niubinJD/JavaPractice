package com.nb.netty.codec.nativeway;

import com.nb.netty.codec.nativeway.pojo.SubscribeReq;
import com.nb.netty.codec.nativeway.pojo.SubscribeResp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SubReqClientHandler extends ChannelHandlerAdapter {
    private static final Logger log = LogManager.getLogger(SubReqClientHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //        super.channelInactive(ctx);
//        发送订购消息

        for (int i = 0; i < 5; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setSubReqID(i);
        req.setUserName("xiaoming");
        req.setPhoneNumber("110");
        req.setProductName("110");
        req.setAddress("100");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        log.info("接收到来自服务端的消息: {}", (SubscribeResp) msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        ctx.flush();
    }
}
