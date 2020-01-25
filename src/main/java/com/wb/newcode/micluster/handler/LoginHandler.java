package com.wb.newcode.micluster.handler;

import com.wb.newcode._02mi.dao.UserDao;
import com.wb.newcode._02mi.pojo.User;
import com.wb.newcode.micluster.session.ClusterSession;
import com.wb.newcode.micluster.session.ClusterSessionMap;
import com.wb.newcode.micluster.session.ServerSession;
import com.wb.newcode.micluster.session.UserLoginMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginHandler extends ChannelInboundHandlerAdapter {


    private String sessionId;
    private String serverId;
    private String toServerId;

    public LoginHandler(String serverId, String toServerId) {
        this.serverId = serverId;
        this.toServerId = toServerId;
        this.sessionId = serverId+"_"+toServerId;
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
                ClusterSession clustorSession = ClusterSessionMap.getClustorSession(sessionId);
                UserLoginMap.add(u.getId()+"",clustorSession);
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
