package com.wb.newcode.websocketbilibili;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * 接收/处理/响应客户端websocket请求的核心业务处理类
 * @author liuli
 *
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
		System.out.println("收到的消息："+textWebSocketFrame.text());
	//	ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间："+ LocalDateTime.now()));
		group.writeAndFlush(new TextWebSocketFrame("服务器时间："+ LocalDateTime.now()));//广播
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}



	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded:"+ctx.channel().id().asLongText());
		group.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved:"+ctx.channel().id().asLongText());
		group.remove(ctx.channel());
	}
}
