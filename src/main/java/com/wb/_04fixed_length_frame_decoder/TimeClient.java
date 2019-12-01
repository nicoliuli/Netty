package com.wb._04fixed_length_frame_decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
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
            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(20));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeClientHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port  = 8080;
        new TimeClient().connect(host,port);
    }
}