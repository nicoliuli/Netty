package com.wb.newcode.micluster.handler;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.pojo.ChatMsg;
import com.wb.newcode.micluster.session.ClusterSession;
import com.wb.newcode.micluster.session.UserLoginMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

/**
 * 起到服务端聊天消息路由的作用
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
        if(msg instanceof ChatMsg){
            ChatMsg chatMsg = (ChatMsg) msg;
            Integer toId = chatMsg.getToId();
            ClusterSession toClusterSession = UserLoginMap.get(toId + "");
            if(toClusterSession == null){
                System.out.println(toId+"不在线");
                ctx.fireChannelRead(msg);
            }
            Channel channel = toClusterSession.getChannel();
            ChannelPipeline pipeline = channel.pipeline();
            channel.writeAndFlush(JSON.toJSONString(chatMsg));
        }
    }
}
