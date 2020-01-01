package com.wb.oldcode._04fixed_length_frame_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<100;i++){
            byte [] request = "一二三四五".getBytes();
            ByteBuf buf = Unpooled.buffer(request.length);
            buf.writeBytes(request);
            ctx.writeAndFlush(buf);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //3
        String resq = (String)msg;
        //4
        System.out.println("TimeServer的响应是：【"+resq+"】");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught被调用");
        ctx.close();
    }
}
