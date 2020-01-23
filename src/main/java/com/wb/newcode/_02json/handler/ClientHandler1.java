package com.wb.newcode._02json.handler;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02json.pojo.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class ClientHandler1 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


        for(int i=0;i<9;i++){
            User u = new User(i,"zhangsan"+i,20+i);
            byte [] request = JSON.toJSONString(u).getBytes();
            ByteBuf buf = Unpooled.buffer(request.length);
            buf.writeBytes(request);
            ctx.writeAndFlush(buf);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ClientHandler1 channelRead执行：【】");
        ctx.fireChannelRead(msg);
    }



    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("ClientHandler1 connect执行");
        ctx.connect(remoteAddress, localAddress, promise);
    }

}
