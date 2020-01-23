package com.wb.newcode._02mi;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.dao.UserDao;
import com.wb.newcode._02mi.handler.ClientBisHandler;
import com.wb.newcode._02mi.handler.ExceptionHandler;
import com.wb.newcode._02mi.handler.JsonMsgDecoder;
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

public class Client {
    private Channel channel = null;
    public void connect(String host, int port) throws Exception {
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
            this.channel = f.channel();
            //类似于发一条登录消息
            f.channel().writeAndFlush(JSON.toJSONString(UserDao.getUserById(1)));
            f.channel().closeFuture().sync();
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
        new Client().connect(host, port);
    }

    public Channel getChannel() {
        return this.channel;
    }
}
