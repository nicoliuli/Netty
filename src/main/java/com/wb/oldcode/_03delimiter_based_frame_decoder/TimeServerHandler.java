package com.wb.oldcode._03delimiter_based_frame_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        //1
        String req = (String)msg;
        System.out.println("TimeClient请求是 ：【"+req+"】");
        //3
        req+="$_";
        ByteBuf resp =
                Unpooled.copiedBuffer(req.getBytes());
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
