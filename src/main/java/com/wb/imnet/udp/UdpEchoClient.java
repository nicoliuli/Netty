package com.wb.imnet.udp;

import java.io.IOException;
import java.net.*;

public class UdpEchoClient {

    private String sendStr = "hello";
    private String netAddress = "127.0.0.1";
    private final int PORT = 9999;

    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    public UdpEchoClient(){
        try {
            //给客户端发消息
            datagramSocket = new DatagramSocket();
            byte[] buf = sendStr.getBytes();
            InetAddress address = InetAddress.getByName(netAddress);
            datagramPacket = new DatagramPacket(buf, buf.length, address, PORT);
            datagramSocket.send(datagramPacket);

            //接收服务端发过来的消息
            byte[] receBuf = new byte[1024];
            DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
            datagramSocket.receive(recePacket);
            String receStr = new String(recePacket.getData(), 0 , recePacket.getLength());
            System.out.println(receStr);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if(datagramSocket != null){
                datagramSocket.close();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UdpEchoClient udpEchoClient = new UdpEchoClient();
                }
            }).start();
        }
    }
}
