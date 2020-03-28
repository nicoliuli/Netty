package com.wb.imnet.idle;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class ClientHandlersInitializer extends ChannelInitializer<SocketChannel> {

   // private ReconnectHandler reconnectHandler;
    private EchoHandler echoHandler;

    public ClientHandlersInitializer(TcpClient tcpClient) {
    //    this.reconnectHandler = new ReconnectHandler(tcpClient);
        this.echoHandler = new EchoHandler();
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 2, 0));
        pipeline.addLast(new ClientIdleStateTrigger());
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new Pinger());
    }
}
