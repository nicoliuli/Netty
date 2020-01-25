package com.wb.newcode.micluster.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录客户端链接在哪台机器上
 */
public class UserLoginMap {
    private static ConcurrentHashMap<String,ClusterSession> userLoginMap = new ConcurrentHashMap();

    public static void add(String uid,ClusterSession clusterSession){
        userLoginMap.put(uid,clusterSession);
    }

    public static ClusterSession get(String uid){
        return userLoginMap.get(uid);
    }

    public static void remove(String uid){
        userLoginMap.remove(uid);
    }
}
