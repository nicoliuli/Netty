package com.wb._01timeserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        //1
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        //2
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("TimeClient请求是 ：【"+body+"】");
        //3
        ByteBuf resp =
                Unpooled.copiedBuffer(("当前时间是 : "
                        +new SimpleDateFormat("yyyy-MM-dd hh:MM:ss")
                        .format(new Date())).getBytes());
        ctx.write(resp);
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
