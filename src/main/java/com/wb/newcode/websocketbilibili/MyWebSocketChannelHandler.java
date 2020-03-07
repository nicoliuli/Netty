package com.wb.newcode.websocketbilibili;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化连接时候的各个组件
 * @author liuli
 *
 */
public class MyWebSocketChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel e) throws Exception {
		e.pipeline().addLast("http-codec", new HttpServerCodec());

		e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
		e.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
		e.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket"));
		e.pipeline().addLast("handler", new MyWebSocketHandler());
	}

}
