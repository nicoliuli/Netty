package com.wb.newcode.micluster.Listener;

import io.netty.bootstrap.Bootstrap;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class RetryListener implements GenericFutureListener {
    private Bootstrap b;
    private String host;
    private int port;

    public RetryListener() {
    }

    public RetryListener(Bootstrap b, String host, int port) {
        this.b = b;
        this.host = host;
        this.port = port;
    }

    @Override
    public void operationComplete(Future future) throws Exception {
        if(future.isSuccess()){
            System.out.println("链接成功");
        }else{
       //     ChannelFuture f = b.connect(host,port).addListener(this);
        }
    }
}
