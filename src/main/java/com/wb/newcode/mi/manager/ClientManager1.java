package com.wb.newcode.mi.manager;

import com.wb.newcode.mi.Client;
import com.wb.newcode.mi.dao.UserDao;

public class ClientManager1 {
    public static void main(String[] args) throws Exception{
        new Client(UserDao.getUserById(2)).onStart();
        System.exit(-1);
    }


}
