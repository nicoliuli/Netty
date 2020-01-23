package com.wb.newcode._02mi.handler;

import com.wb.newcode._02mi.pojo.User;
import com.wb.newcode._02mi.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerBisHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof User){
            User u = (User) msg;
            System.out.println(u);
            //创建会话
            ServerSession serverSession = new ServerSession(ctx.channel());
            serverSession.setUser(u);
            serverSession.bind();
        }
    }

}
