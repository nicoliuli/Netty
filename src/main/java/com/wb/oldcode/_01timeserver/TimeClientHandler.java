package com.wb.oldcode._01timeserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;

import java.nio.charset.Charset;
import java.util.UUID;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.channel().config().setWriteBufferWaterMark(new WriteBufferWaterMark(32768,65536));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    byte [] request = (UUID.randomUUID().toString()).getBytes("UTF-8");
                    while(true){
                        if(ctx.channel().isWritable()) {
                            //处于低水位
                            ByteBuf buf = Unpooled.buffer(request.length);
                            buf.writeBytes(request);
                            ctx.writeAndFlush(buf);
                            System.out.println("低水位："+ctx.channel().unsafe().outboundBuffer().size());
                        }else {
                            //处于高水位
                            System.out.println("高水位："+ctx.channel().unsafe().outboundBuffer().size());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //3
        ByteBuf buf = (ByteBuf)msg;
        byte[]resp = new byte[buf.readableBytes()];
        buf.readBytes(resp);
        //4
        String body = new String(resp,"UTF-8");
   //     System.out.println("TimeServer的响应是：【"+body+"】");
     //   ReferenceCountUtil.release(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught被调用");
        ctx.close();
    }
}
