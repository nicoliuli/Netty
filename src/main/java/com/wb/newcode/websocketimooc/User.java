package com.wb.newcode.websocketimooc;

public class User {
    private int id;
    private String name;


    public int getId() {
        System.out.println("getId");
        return id;
    }

    public void setId(int id) {
        System.out.println("setId");
        this.id = id;
    }

    public String getName() {
        System.out.println("getName");
        return name;
    }

    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
    }
}
