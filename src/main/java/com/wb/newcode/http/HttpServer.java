package com.wb.newcode.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServer {
    public void bind(int port) throws Exception{
        //1
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //2
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,2048)
                    .childHandler(new ChildChannelHandler());
            //3
            ChannelFuture f = b.bind(port).sync();
            //4
            f.channel().closeFuture().sync();
        }finally {
            //5
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        protected void initChannel(SocketChannel ch) throws Exception {

            ch.pipeline().addLast("codec",new HttpServerCodec());
            ch.pipeline().addLast("aggregator",new HttpObjectAggregator(512*1024));
            ch.pipeline().addLast(new HttpServerHandler());

        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        //
        new HttpServer().bind(port);
    }
}
