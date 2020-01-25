package com.wb.newcode.micluster.session;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServerSessionMap {
    private static ConcurrentHashMap<String, ServerSession> sessionMap = new ConcurrentHashMap();

    public static void add(String sessionId, ServerSession session){
        sessionMap.put(sessionId,session);
    }

    public static void removeSession(String sessionId){
        sessionMap.remove(sessionId);
    }

    public static ServerSession getSession(String sessionId){
        return sessionMap.get(sessionId);
    }

    /**
     * 在线总人数
     */
    public static int getTotalLogin(){
        return sessionMap.size();
    }

    public static List<ServerSession> getSessionByUid(final int uid){
        List<ServerSession> list = sessionMap.values().stream().filter(s -> s.getUser().getId() == uid).collect(Collectors.toList());
        return list;
    }
}
