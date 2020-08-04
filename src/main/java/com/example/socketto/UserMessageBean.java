package com.example.socketto;

/**
 * 用户信息交互类
 */
public class UserMessageBean {

    private String userId;
    private int number;
    private String name;
    private int face;

    public UserMessageBean(String userId, int number, String name, int face) {
        this.userId = userId;
        this.number = number;
        this.name = name;
        this.face = face;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
