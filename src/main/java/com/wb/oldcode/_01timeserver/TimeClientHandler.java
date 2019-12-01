package com.wb.oldcode._01timeserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte [] request = ("请求").getBytes();
        ByteBuf buf = Unpooled.buffer(request.length);
        buf.writeBytes(request);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //3
        ByteBuf buf = (ByteBuf)msg;
        byte[]resp = new byte[buf.readableBytes()];
        buf.readBytes(resp);
        //4
        String body = new String(resp,"UTF-8");
        System.out.println("TimeServer的响应是：【"+body+"】");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught被调用");
        ctx.close();
    }
}
