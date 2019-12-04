package com.wb.newcode._02_msg2msgdcode;

import com.wb.newcode._01.ServerHandler1;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

public class Server {
    public void bind(int port) throws Exception{
        //1
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //2
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
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

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new Msg2MsgDecoder());
        }
    }

    public static void main(String[] args) throws Exception {
        /*int port = 8080;
        //
        new Server().bind(port);*/

        long a = new Date().getTime() - 1573704952000L;

        System.out.println(a/3600/24/1000);
        System.out.println(new Date().getTime());
    }
}
