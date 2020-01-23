package com.wb.newcode._02mi.session;

import com.wb.newcode._02mi.pojo.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.UUID;

/**
 * 会话
 */
public class ServerSession {
    public static final AttributeKey<ServerSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");

    private String sessionId;
    private User user;
    private Channel channel;

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

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void bind(Channel channel){
        this.channel = channel;
        this.sessionId = buildSessionId();
        channel.attr(SESSION_KEY).set(this);
    }

    private String buildSessionId(){
        return UUID.randomUUID().toString();
    }

    public static ServerSession getSession(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        return channel.attr(SESSION_KEY).get();
    }
}
