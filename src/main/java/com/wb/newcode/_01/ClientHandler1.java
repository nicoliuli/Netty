package com.wb.newcode._01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class ClientHandler1 extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte [] request = ("请求").getBytes();
        ByteBuf buf = Unpooled.buffer(request.length);
        buf.writeBytes(request);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ClientHandler1 channelRead执行：【】");
        ctx.fireChannelRead(msg);
    }


    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("ClientHandler1 connect执行");
        ctx.connect(remoteAddress, localAddress, promise);
    }

}
