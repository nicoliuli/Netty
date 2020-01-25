package com.wb.newcode.micluster.handler;

import com.wb.newcode.micluster.session.ClusterSession;
import com.wb.newcode.micluster.session.ClusterSessionMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClusterSessionHandler extends ChannelInboundHandlerAdapter {

    private String serverId;
    private String toServerId;

    public ClusterSessionHandler(String serverId,String toServerId) {
        this.serverId = serverId;
        this.toServerId = toServerId;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //构建服务端会话
        ClusterSession clusterSession = new ClusterSession(serverId,toServerId,ctx.channel());
        ClusterSessionMap.addClusterSession(clusterSession);
        ctx.fireChannelActive();
        ctx.pipeline().remove(this);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }
}
