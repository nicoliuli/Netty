package com.wb.newcode._02mi;

import io.netty.channel.Channel;

public class ClientManager {
    public static void main(String[] args) throws Exception{
        Client client = new Client();
        new Thread(new StartThread(client)).start();
        Thread.sleep(5000);
        new Thread(new ChatThread(client)).start();
    }

    static class StartThread implements Runnable{
        private Client client;
        public StartThread(Client client){
            this.client = client;
        }
        @Override
        public void run() {
            try {
                client.onStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ChatThread implements Runnable{
        private Client client;
        public ChatThread(Client client){
            this.client = client;
        }
        @Override
        public void run() {
            Channel channel = client.getChannel();
            if(channel != null){
                System.out.println("channel is not null");
            }else{
                System.out.println("channel is null");
            }
        }
    }
}
