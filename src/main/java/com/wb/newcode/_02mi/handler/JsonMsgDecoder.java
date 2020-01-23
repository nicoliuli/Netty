package com.wb.newcode._02mi.handler;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.pojo.User;
import com.wb.newcode._02mi.session.ServerSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 将JSON转化为POJO
 */
public class JsonMsgDecoder extends ChannelInboundHandlerAdapter {
    ConcurrentHashMap<Integer, ServerSession> sessionMap = new ConcurrentHashMap();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String){
            String jsonMsg = (String) msg;
            User user = JSON.parseObject(jsonMsg, User.class);
            ctx.fireChannelRead(user);
        }
    }
}
