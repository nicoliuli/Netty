package com.wb.newcode.micluster.handler;

import com.wb.newcode.micluster.session.ClusterSession;
import com.wb.newcode.micluster.session.ClusterSessionMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 起到服务端聊天消息路由
 */
public class RouteHandler extends ChannelInboundHandlerAdapter {

    private String sessionId;
    private String serverId;
    private String toServerId;

    public RouteHandler(String serverId, String toServerId) {
        this.serverId = serverId;
        this.toServerId = toServerId;
        this.sessionId = serverId+"_"+toServerId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClusterSession clustorSession = ClusterSessionMap.getClustorSession(sessionId);
        System.out.println(clustorSession);
    }
}
