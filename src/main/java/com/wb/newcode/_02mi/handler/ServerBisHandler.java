package com.wb.newcode._02mi.handler;

import com.wb.newcode._02mi.session.ServerSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerBisHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        Channel channel = ctx.channel();
        ServerSession serverSession = ServerSession.getSession(ctx);
        if(serverSession != null){
            System.out.println(serverSession.getSessionId());
            System.out.println(serverSession.getChannel());
        }else{
            System.out.println("会话是空");
        }


    }

}
