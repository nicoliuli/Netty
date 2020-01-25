package com.wb.newcode.micluster.server;

public class ServerManager {
    public static void main(String[] args) {
        Server1 s1 = new Server1();
        Server2 s2 = new Server2();
        s1.open();
        s2.open();
    }
}
