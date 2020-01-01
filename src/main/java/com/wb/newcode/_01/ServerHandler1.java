package com.wb.newcode._01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerHandler1 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        System.out.println("ServerHandler1 channelRead 执行");
        ctx.fireChannelRead(msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("ServerHandler1 channelReadComplate被调用");
        ctx.flush();
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerHandler1 channelActive执行");
        ctx.fireChannelActive();
    }
}
