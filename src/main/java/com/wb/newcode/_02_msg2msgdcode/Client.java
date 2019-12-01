package com.wb.newcode._02_msg2msgdcode;

import com.wb.newcode._01.ClientHandler2;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public void connect(String host,int port) throws Exception{
        //1
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            //2
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChildChannelHandler());

            //3
            ChannelFuture f = b.connect(host,port).sync();
            //4
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new Msg2MsgEncoder());
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port  = 8080;
        new Client().connect(host,port);
    }
}
