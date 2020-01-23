package com.wb.newcode._02mi.handler;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.pojo.ChatMsg;
import com.wb.newcode._02mi.pojo.User;
import com.wb.newcode._02mi.session.ServerSession;
import com.wb.newcode._02mi.session.ServerSessionMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

public class ServerBisHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof User) {
            User u = (User) msg;
            System.out.println(u);
        } else if (msg instanceof ChatMsg) {
            ChatMsg chatMsg = (ChatMsg) msg;
            System.out.println(chatMsg);
            ctx.fireChannelRead(msg);
            //转发消息给对方用户
            List<ServerSession> sessions = ServerSessionMap.getSessionByUid(chatMsg.getToId());
            if (sessions != null && sessions.size() != 0) {
                for (ServerSession session : sessions) {
                    Channel channel = session.getChannel();
                    channel.writeAndFlush(JSON.toJSONString(chatMsg));
                }
            }
        }
    }

}
