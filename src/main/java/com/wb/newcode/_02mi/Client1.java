package com.wb.newcode._02mi;

import com.alibaba.fastjson.JSON;
import com.wb.newcode._02mi.handler.ClientBisHandler;
import com.wb.newcode._02mi.handler.ExceptionHandler;
import com.wb.newcode._02mi.handler.JsonMsgDecoder;
import com.wb.newcode._02mi.pojo.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class Client1 {
    public void connect(String host,int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChildChannelHandler());

            ChannelFuture f = b.connect(host,port).sync();
            f.channel().writeAndFlush(JSON.toJSONString(new User(2,"李四",21)));
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        protected void initChannel(SocketChannel ch) throws Exception {
            //out编码
            ch.pipeline().addLast(new LengthFieldPrepender(4));
            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            //in 解码
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new JsonMsgDecoder());
            ch.pipeline().addLast(new ClientBisHandler());
            //异常处理
            ch.pipeline().addLast(new ExceptionHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port  = 8080;
        new Client1().connect(host,port);
    }
}
