package com.wb.newcode.micluster.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wb.newcode._02mi.pojo.ChatMsg;
import com.wb.newcode._02mi.pojo.MsgType;
import com.wb.newcode._02mi.pojo.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 将JSON转化为POJO
 */
public class JsonMsgDecoderHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            String jsonMsg = (String) msg;
            JSONObject jsonObject = JSON.parseObject(jsonMsg);
            if(jsonObject.containsKey("msgType")){
                Integer msgType = jsonObject.getInteger("msgType");
                if(msgType == MsgType.MSG_TYPE_USER){
                    User user = JSON.parseObject(jsonMsg, User.class);
                    ctx.fireChannelRead(user);
                }else if(msgType == MsgType.MSG_TYPE_CHATMSG){
                    ChatMsg chatMsg = JSON.parseObject(jsonMsg, ChatMsg.class);
                    ctx.fireChannelRead(chatMsg);
                }
            }
        }
    }
}
