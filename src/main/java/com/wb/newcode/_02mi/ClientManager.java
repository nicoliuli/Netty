package com.wb.newcode._02mi;

import com.wb.newcode._02mi.dao.UserDao;

public class ClientManager {
    public static void main(String[] args) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Client(UserDao.getUserById(1)).onStart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Client(UserDao.getUserById(2)).onStart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
