package com.companybt.imsocket.bean;

/**
 * 聊天消息实体类
 *
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-07-27 00:24
 */
public class MessageBean {
    private int code = 0;
    private int type;
    private int face;
    private String name;
    private String message;

    public MessageBean() {
    }

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
