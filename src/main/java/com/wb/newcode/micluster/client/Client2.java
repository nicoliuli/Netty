package com.wb.newcode.micluster.client;

import com.alibaba.fastjson.JSON;
import com.wb.newcode.mi.dao.UserDao;
import com.wb.newcode.mi.handler.ClientBisHandler;
import com.wb.newcode.mi.pojo.ChatMsg;
import com.wb.newcode.mi.pojo.MsgType;
import com.wb.newcode.mi.pojo.User;
import com.wb.newcode.micluster.handler.JsonMsgDecoderHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Scanner;

public class Client2 {
    //客户端与用户绑定
    private static User user = UserDao.getUserById(2);


    public static void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChildChannelHandler());

            ChannelFuture f = b.connect(host, port).sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("链接成功");
                    }
                }
            });
            //发一条登录消息
            f.channel().writeAndFlush(JSON.toJSONString(user)).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendChatMsg(f);
                        }
                    }).start();
                }
            });

            f.channel().closeFuture().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("client close");
                }
            });

        } finally {
            group.shutdownGracefully();
        }
    }

    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {
            //out编码
            ch.pipeline().addLast(new LengthFieldPrepender(4));
            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            //in解码
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new JsonMsgDecoderHandler());
            ch.pipeline().addLast(new ClientBisHandler());
        }
    }

    public static void main(String []args) throws Exception {
        String host = "127.0.0.1";
        int port = 8081;
        connect(host, port);
    }

    private static void sendChatMsg(ChannelFuture f) {
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setFromId(user.getId());
        chatMsg.setMsgType(MsgType.MSG_TYPE_CHATMSG);
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入对方的uid：");
        String uid = sc.nextLine();
        chatMsg.setToId(Integer.parseInt(uid));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if("exit".equals(line) || "quit".equals(line)){
                chatMsg.setText(line);
                f.channel().writeAndFlush(JSON.toJSONString(chatMsg));
                f.channel().close();
                return;
            }
            chatMsg.setText(line);
            f.channel().writeAndFlush(JSON.toJSONString(chatMsg));
        }
    }
}
