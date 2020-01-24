package com.wb.newcode._02mi.manager;

import com.wb.newcode._02mi.Client;
import com.wb.newcode._02mi.dao.UserDao;

public class ClientManager1 {
    public static void main(String[] args) throws Exception{
        new Client(UserDao.getUserById(2)).onStart();
        System.exit(-1);
    }


}
