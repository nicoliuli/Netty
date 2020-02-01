package com.wb.newcode.mi.session;

import com.wb.newcode.mi.pojo.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.UUID;

/**
 * 会话
 */
public class ClientSession {
    public static final AttributeKey<ClientSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");

    private String sessionId;
    private User user;
    private Channel channel;

    public ClientSession(Channel channel) {
        this.channel = channel;
        this.sessionId = buildSessionId();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel() {
        this.channel = channel;
    }

    public void bind(){

    }

    private String buildSessionId(){
        return UUID.randomUUID().toString();
    }

    public static ClientSession getSession(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        return channel.attr(SESSION_KEY).get();
    }

    @Override
    public String toString() {
        return "ClientSession{" +
                "sessionId='" + sessionId + '\'' +
                ", user=" + user +
                '}';
    }
}
