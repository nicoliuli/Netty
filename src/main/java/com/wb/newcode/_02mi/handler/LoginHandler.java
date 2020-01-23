package com.wb.newcode._02mi.handler;

import com.wb.newcode._02mi.pojo.ChatMsg;
import com.wb.newcode._02mi.pojo.User;
import com.wb.newcode._02mi.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof User){
            User u = (User) msg;
            if(checkLogin(u)){
                //创建会话
                ServerSession serverSession = new ServerSession(ctx.channel());
                serverSession.setUser(u);
                serverSession.bind();
                //删除handler
                ctx.pipeline().remove(LoginHandler.this);
                System.out.println("login:"+u);
                //临时构造一条聊天
                ChatMsg chatMsg = new ChatMsg(u.getId(),1,"你好，我是"+u.getName());
                ctx.fireChannelRead(chatMsg);
            }
            //返回登陆失败的响应

        }
    }

    private boolean checkLogin(User u){
        if(u.getId()==1 || u.getId()==2){
            return true;
        }
        return false;
    }

}
