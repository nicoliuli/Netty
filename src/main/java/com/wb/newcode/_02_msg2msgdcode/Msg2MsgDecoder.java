package com.wb.newcode._02_msg2msgdcode;

import com.wb.newcode._02_msg2msgdcode.pojo.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.net.SocketAddress;
import java.util.List;

public class Msg2MsgDecoder extends MessageToMessageDecoder<User> {



    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, User user, List<Object> list) throws Exception {

        user.id += 1;
        user.name = user.name + "a";
        list.add(user);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        ctx.writeAndFlush(buf);
    }
}
