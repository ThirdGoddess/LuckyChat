package com.example.socketto;

/**
 * 消息转发体
 */
public class MessageBean {
    private int type;
    private int face;
    private String name;
    private String message;

    public MessageBean(int type, int face, String name, String message) {
        this.type = type;
        this.face = face;
        this.name = name;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
