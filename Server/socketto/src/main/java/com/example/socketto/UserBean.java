package com.example.socketto;

import java.net.Socket;

public class UserBean {

    private String id;
    private Socket socket;
    private int number;
    private String name;
    private int face;
    private boolean isMatching;

    public UserBean(String id, Socket socket, int number, String name, int face) {
        this.id = id;
        this.socket = socket;
        this.number = number;
        this.name = name;
        this.face = face;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
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

    public boolean isMatching() {
        return isMatching;
    }

    public void setMatching(boolean matching) {
        isMatching = matching;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", socket=" + socket +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", face=" + face +
                '}';
    }
}
