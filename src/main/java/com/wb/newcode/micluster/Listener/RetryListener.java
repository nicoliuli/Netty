package com.wb.newcode.micluster.Listener;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class RetryListener implements GenericFutureListener {
   private String serverId;

    public RetryListener(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public void operationComplete(Future future) throws Exception {
        if(future.isSuccess()){
            System.out.println(serverId+"链接成功");
        }
    }
}
