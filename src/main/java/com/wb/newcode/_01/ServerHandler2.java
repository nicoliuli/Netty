package com.wb.newcode._01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerHandler2 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        //1
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        //2
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("ServerHandler2 channelRead 执行："+body);
        //3
        ByteBuf resp =
                Unpooled.copiedBuffer(("当前时间是 : "
                        +new SimpleDateFormat("yyyy-MM-dd hh:MM:ss")
                        .format(new Date())).getBytes());
        ctx.writeAndFlush(resp);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("ServerHandler2 channelReadComplate被调用");
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerHandler2 channelActive执行");
        ctx.fireChannelActive();
    }
}
