package com.companybt.imsocket.bean;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-07-28 01:08
 */
public class FaceBean {
    private int faceId;
    private Object face;
    private boolean isSelect;

    public FaceBean(int faceId, Object face) {
        this.faceId = faceId;
        this.face = face;
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public Object getFace() {
        return face;
    }

    public void setFace(Object face) {
        this.face = face;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
