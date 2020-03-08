package com.wb.newcode.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 基于zero cppy的客户端
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8888));
        socketChannel.configureBlocking(true);
        String filePath = "/Users/liuli/Downloads/soft/WPS_Office_1.9.1(2994).dmg";
        FileChannel fileChannel = new FileInputStream(filePath).getChannel();
        long start = System.currentTimeMillis();
        long transferToCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的字节数："+transferToCount+"，时间："+(System.currentTimeMillis()-start));
        fileChannel.close();
        socketChannel.close();
    }
}
