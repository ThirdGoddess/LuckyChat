package com.companybt.imsocket.bean;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-07-31 19:29
 */
public class UserBean {
    private int number;
    private String name;
    private int face;

    public UserBean(int number, String name, int face) {
        this.number = number;
        this.name = name;
        this.face = face;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }
}
