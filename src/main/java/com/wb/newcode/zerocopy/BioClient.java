package com.wb.newcode.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * 基于传统bio的客户端
 */
public class BioClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",8888);
        String filePath = "/Users/liuli/Downloads/soft/WPS_Office_1.9.1(2994).dmg";
        InputStream inputStream = new FileInputStream(filePath);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte [] buffer = new byte[4096];
        long readCount;
        long total = 0;
        long start = System.currentTimeMillis();
        while ((readCount = inputStream.read(buffer)) >=0){
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送的字节数："+total+"，时间："+(System.currentTimeMillis()-start));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
