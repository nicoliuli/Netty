package com.wb.newcode._02_test.pojo;

public class User {
    public  Integer id = 1;
    public String name = "aaa";

    @Override
    public String toString() {
       return id+"=="+name;
    }
}