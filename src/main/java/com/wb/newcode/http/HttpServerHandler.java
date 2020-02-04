package com.wb.newcode.http;

import com.alibaba.fastjson.JSON;
import com.wb.newcode.mi.pojo.User;
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


            ctx.executor().execute(new Runnable() {
                @Override
                public void run() {
                    User u = new User();
                    u.setAge(10);u.setId(1);u.setMsgType(2);u.setName("张三");
                    String s = JSON.toJSONString(u);
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
                    response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/json;charset=UTF-8");
                    ByteBuf buf = Unpooled.copiedBuffer(s, Charset.defaultCharset());
                    response.content().writeBytes(buf);
                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                    ((FullHttpRequest) msg).release();
                    //有可能不需要关闭也行
                    ctx.channel().close();
                    ctx.close();
                }
            });
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
