package com.companybt.imsocket.bean;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-08-02 23:06
 */
public class SendMessageBean {

    private String content;
    private String name;
    private int faceId;
    private String toId;

    public SendMessageBean(String content, String name, int faceId, String toId) {
        this.content = content;
        this.name = name;
        this.faceId = faceId;
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
