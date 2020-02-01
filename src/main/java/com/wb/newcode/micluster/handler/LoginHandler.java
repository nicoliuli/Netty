package com.wb.newcode.micluster.handler;

import com.wb.newcode.mi.dao.UserDao;
import com.wb.newcode.mi.pojo.User;
import com.wb.newcode.micluster.session.ServerSession;
import com.wb.newcode.micluster.session.UserLoginMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginHandler extends ChannelInboundHandlerAdapter {


    private String serverId;

    public LoginHandler(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof User){
            User u = (User) msg;
            if(checkLogin(u)){
                //创建并绑定会话
                ServerSession serverSession = new ServerSession(ctx.channel());
                serverSession.setUser(u);
                serverSession.bind();
                //在缓存里记录客户端链接打到哪一台机器上
                UserLoginMap.add(u.getId()+"",serverId);
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
