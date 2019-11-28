package com.nb.netty.codec.nativeway;

import com.nb.netty.codec.nativeway.pojo.SubscribeReq;
import com.nb.netty.codec.nativeway.pojo.SubscribeResp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SubReqServerHandler extends ChannelHandlerAdapter {
    private static final Logger log = LogManager.getLogger(SubReqServerHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        log.error(cause.getMessage(), cause);
        ctx.close();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        SubscribeReq req = (SubscribeReq) msg;
        log.info("订购消息对象：{}", req);
        ctx.writeAndFlush(resp(req));
    }

    private SubscribeResp resp(SubscribeReq req) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(req.getSubReqID());
        resp.setRespCode(0);
        resp.setDesc("order success!");
        return resp;
    }
}
