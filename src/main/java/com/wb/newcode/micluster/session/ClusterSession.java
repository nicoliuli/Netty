package com.wb.newcode.micluster.session;

import io.netty.channel.Channel;

public class ClusterSession {
    private String sessionId;
    private String serverId;
    private String toServerId;
    private Channel channel;

    public ClusterSession() {
    }

    public ClusterSession(String serverId, String toServerId, Channel channel) {
        this.serverId = serverId;
        this.toServerId = toServerId;
        this.channel = channel;
        //拼接sessionId
        this.sessionId = serverId+"_"+toServerId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getToServerId() {
        return toServerId;
    }

    public void setToServerId(String toServerId) {
        this.toServerId = toServerId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
