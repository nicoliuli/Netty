package com.wb.newcode.micluster.session;

import java.util.concurrent.ConcurrentHashMap;

public class ClusterSessionMap {
    private static ConcurrentHashMap<String,ClusterSession> clusterSessionMap = new ConcurrentHashMap<>();

    public static void addClusterSession(ClusterSession clusterSession){
        clusterSessionMap.put(clusterSession.getSessionId(),clusterSession);
    }

    public static ClusterSession getClustorSession(String sessionId){
        return clusterSessionMap.get(sessionId);
    }

    public static void removeSession(String sessionId){
        clusterSessionMap.remove(sessionId);
    }
}
