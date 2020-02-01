package com.wb.newcode.mi.dao;

import com.wb.newcode.mi.pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 用集合模拟数据库
 */
public class UserDao {
    private static Map<Integer, User> users = new HashMap<>();

    static {
        for (int i = 1; i <= 1000; i++) {
            User u = new User(i, "姓名" + i, i);
            users.put(i, u);
        }
    }

    public static User getUserById(Integer id) {
        return users.get(id);
    }
}
