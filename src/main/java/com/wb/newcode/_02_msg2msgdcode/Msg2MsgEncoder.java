package com.wb.newcode._02_msg2msgdcode;

import com.wb.newcode._02_msg2msgdcode.pojo.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Msg2MsgEncoder extends MessageToMessageEncoder<User> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, User user, List<Object> list) throws Exception {
        user.id += 1;
        user.name = user.name + "a";
        list.add(user);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        User u = (User) msg;
        System.out.println(u);
        ctx.writeAndFlush(u);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes("a".getBytes());
        ctx.writeAndFlush(buf);
    }
}
