package com.wb.newcode.micluster.server;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.pojo.ChatMsg;
import com.wb.newcode.micluster.Listener.RetryListener;
import com.wb.newcode.micluster.handler.JsonMsgDecoderHandler;
import com.wb.newcode.micluster.handler.LoginHandler;
import com.wb.newcode.micluster.handler.RouteHandler;
import com.wb.newcode.micluster.session.Redis;
import com.wb.newcode.micluster.session.ServerSession;
import com.wb.newcode.micluster.session.ServerSessionMap;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.List;

public class Server2 {
    private static Server2 server2 = new Server2();
    private static String serverId = "2";

    public void bind(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ServerChildChannelHandler());
            ChannelFuture f = b.bind(port).sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (true){
                                    ChatMsg chatMsg = Redis.q2.take();
                                    Integer toId = chatMsg.getToId();
                                    List<ServerSession> sessions = ServerSessionMap.getSessionByUid(toId);
                                    if(sessions!=null && sessions.size()>0){
                                        for(ServerSession session:sessions){
                                            Channel channel = session.getChannel();
                                            channel.writeAndFlush(JSON.toJSONString(chatMsg));
                                        }
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ServerChildChannelHandler extends ChannelInitializer<SocketChannel>{

        protected void initChannel(SocketChannel ch) throws Exception {
            //in解码
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new JsonMsgDecoderHandler());
            ch.pipeline().addLast(new LoginHandler(serverId));
            ch.pipeline().addLast(new RouteHandler(serverId));
            //out编码
            ch.pipeline().addLast(new LengthFieldPrepender(4));
            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
        }
    }

    public static void onStartServer() throws Exception {
        int port = 8081;
        server2.bind(port);
    }


    public  void open() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    onStartServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
