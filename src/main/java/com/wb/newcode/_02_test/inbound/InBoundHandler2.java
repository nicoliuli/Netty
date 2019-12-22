package com.wb.newcode._02_test.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class InBoundHandler2 extends SimpleChannelInboundHandler {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object o) throws Exception {
        System.out.println("inhandler2");
        ctx.fireChannelRead(o);
    }
}
