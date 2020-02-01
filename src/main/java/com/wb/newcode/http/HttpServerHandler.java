package com.wb.newcode.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        if(msg instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest) msg;

            String s = request.uri();

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
            ByteBuf buf = Unpooled.copiedBuffer(s.toString(), Charset.defaultCharset());
            response.content().writeBytes(buf);
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        System.out.println("exceptionCaught被调用");
        cause.printStackTrace();
        ctx.close();
    }
}
