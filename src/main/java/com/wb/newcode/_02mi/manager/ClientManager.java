package com.wb.newcode._02mi.manager;

import com.wb.newcode._02mi.Client;
import com.wb.newcode._02mi.dao.UserDao;

public class ClientManager {
    public static void main(String[] args) throws Exception{
        new Client(UserDao.getUserById(1)).onStart();
        System.exit(-1);
    }
}
