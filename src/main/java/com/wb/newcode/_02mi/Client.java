package com.wb.newcode._02mi;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.handler.ClientBisHandler;
import com.wb.newcode._02mi.handler.ExceptionHandler;
import com.wb.newcode._02mi.handler.JsonMsgDecoder;
import com.wb.newcode._02mi.pojo.ChatMsg;
import com.wb.newcode._02mi.pojo.MsgType;
import com.wb.newcode._02mi.pojo.User;
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

public class Client {
    //客户端与用户绑定
    private volatile User user;
    private Channel channel;
    public Client(){
    }
    public Client(User user) {
        this.user = user;
    }

    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChildChannelHandler());

            ChannelFuture f = b.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("链接成功");
                    }
                }
            });

            //类似于发一条登录消息
            f.channel().writeAndFlush(JSON.toJSONString(this.user));
            this.channel = f.channel();
            new Thread(new Runnable() {
                @Override
                public void run() {
                   sendChatMsg();
                }
            }).start();
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

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {
            //out编码
            ch.pipeline().addLast(new LengthFieldPrepender(4));
            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            //in解码
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new JsonMsgDecoder());
            ch.pipeline().addLast(new ClientBisHandler());
            //异常处理
            ch.pipeline().addLast(new ExceptionHandler());
        }
    }

    public void onStart() throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        this.connect(host, port);
    }


    //死循环监听客户端发消息
   public void sendChatMsg(){
       ChatMsg chatMsg = new ChatMsg();
       chatMsg.setFromId(this.user.getId());
       chatMsg.setMsgType(MsgType.MSG_TYPE_CHATMSG);
       Scanner sc = new Scanner(System.in);
       System.out.print("请输入对方的uid：");
       String uid = sc.nextLine();
       chatMsg.setToId(Integer.parseInt(uid));
       while (sc.hasNextLine()){
           String line = sc.nextLine();
           chatMsg.setText(line);
           this.channel.writeAndFlush(JSON.toJSONString(chatMsg));
           if("exit".equals(line) || "quit".equals(line)){
               channel.close().addListener(new GenericFutureListener<Future<? super Void>>() {
                   @Override
                   public void operationComplete(Future<? super Void> future) throws Exception {
                       System.out.println("channel close");
                   }
               });
           }
       }
   }
}
