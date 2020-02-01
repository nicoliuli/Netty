package com.wb.newcode.mi.handler;

import com.alibaba.fastjson.JSON;
import com.wb.newcode.mi.dao.UserDao;
import com.wb.newcode.mi.pojo.ChatMsg;
import com.wb.newcode.mi.pojo.MsgType;
import com.wb.newcode.mi.pojo.User;
import com.wb.newcode.mi.session.ServerSession;
import com.wb.newcode.mi.session.ServerSessionMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

public class ServerBisHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof User) {
            //释放内存
            ctx.fireChannelRead(msg);
            User u = (User) msg;
            System.out.println(u);
        } else if (msg instanceof ChatMsg) {
            ChatMsg chatMsg = (ChatMsg) msg;
            //释放内存
            ctx.fireChannelRead(msg);
            System.out.println(chatMsg);
            if("quit".equals(chatMsg.getText()) || "exit".equals(chatMsg.getText())){
              releaseSession(ctx);
               return;
            }

            //转发消息给对方用户
            List<ServerSession> sessions = ServerSessionMap.getSessionByUid(chatMsg.getToId());
            if (sessions != null && sessions.size() != 0) {
                for (ServerSession session : sessions) {
                    Channel channel = session.getChannel();
                    channel.writeAndFlush(JSON.toJSONString(chatMsg));
                }
            }else{
                System.out.println(UserDao.getUserById(chatMsg.getToId())+"不在线");
                List<ServerSession> sessionList = ServerSessionMap.getSessionByUid(chatMsg.getFromId());
                if(sessionList!=null && sessionList.size()!=0){
                    for(ServerSession s:sessionList){
                        ChatMsg chat = new ChatMsg();
                        chat.setFromId(10000001);
                        chat.setToId(chatMsg.getFromId());
                        chat.setMsgType(MsgType.MSG_TYPE_CHATMSG);
                        chat.setText(chatMsg+"不在线");
                        // 写离线表
                        s.getChannel().writeAndFlush(JSON.toJSONString(chat));
                    }
                }
            }
        }
    }

    //如果是用户推出的命令，释放session
    private void releaseSession(ChannelHandlerContext ctx){
        ServerSession session = ServerSession.getSession(ctx);
        if(session != null){
            ServerSessionMap.removeSession(session.getSessionId());
            session = null;
        }
    }

}
