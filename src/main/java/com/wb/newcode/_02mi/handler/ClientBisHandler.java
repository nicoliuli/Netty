package com.wb.newcode._02mi.handler;

import com.wb.newcode._02mi.pojo.ChatMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientBisHandler extends SimpleChannelInboundHandler {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if(o instanceof ChatMsg){
            ChatMsg chatMsg = (ChatMsg) o;
            System.out.println(chatMsg);
        }
    }
}
