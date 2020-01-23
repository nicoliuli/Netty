package com.wb.newcode._02json.handler;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02json.pojo.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 将JSON转化为POJO
 */
public class JsonMsgDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String){
            String jsonMsg = (String) msg;
            User user = JSON.parseObject(jsonMsg, User.class);
            System.out.println("JsonMsgDecoder===>"+user);
            ctx.fireChannelRead(msg);
        }
    }
}
