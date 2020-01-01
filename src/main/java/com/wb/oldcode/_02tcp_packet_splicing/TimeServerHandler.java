package com.wb.oldcode._02tcp_packet_splicing;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        //1
        String req = (String)msg;
        System.out.println("TimeClient请求是 ：【"+req+"】");
        //3
        ByteBuf resp =
                Unpooled.copiedBuffer(("当前时间是 : "
                        +new SimpleDateFormat("yyyy-MM-dd hh:MM:ss")
                        .format(new Date())+"\r\n").getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channelReadComplate被调用");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        System.out.println("exceptionCaught被调用");
        ctx.close();
    }
}
