package com.wb.newcode._02mi.handler;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.pojo.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientBisHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User u = new User(1,"zhangsan",20);
        Channel channel = ctx.channel();
        channel.writeAndFlush(JSON.toJSONString(u));

    }

}
