package com.wb.newcode._01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class ClientHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte [] request = ("请求").getBytes();
        ByteBuf buf = Unpooled.buffer(request.length);
        buf.writeBytes(request);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf)msg;
        byte[]resp = new byte[buf.readableBytes()];
        buf.readBytes(resp);

        String body = new String(resp,"UTF-8");
        System.out.println("ClientHandler2 channelRead执行：【"+body+"】");
        ctx.fireChannelRead(msg);
    }



    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("ClientHandler2 connect执行");
        ctx.connect(remoteAddress, localAddress, promise);
    }


}
