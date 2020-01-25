package com.wb.newcode.micluster.handler;

import com.wb.newcode._02mi.dao.UserDao;
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
            }
        }
    }

    private boolean checkLogin(User u){
        return UserDao.getUserById(u.getId()) != null;
    }

}
