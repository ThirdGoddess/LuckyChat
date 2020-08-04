package com.example.socketto;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.security.MessageDigest;

public class ServerThread implements Runnable {

    private BufferedReader bufferedReader;
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String userId = null;

    @Override
    public void run() {

        userId = getUserId();

        String content;
        while (null != (content = readFromClient())) {

            System.out.println("对方发来消息:" + content);

            //向指定用户发送消息
            try {
                ContentBean contentBean = new Gson().fromJson(content, ContentBean.class);
                UserBean userBean = Server.sockets.get(contentBean.getToId());
                Socket socket = userBean.getSocket();
                MessageBean messageBean = new MessageBean(2, contentBean.getFaceId(), contentBean.getName(), contentBean.getContent());
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                printStream.println(new Gson().toJson(messageBean));
                System.out.println("转发成功");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private String readFromClient() {
        try {
            String s = bufferedReader.readLine();
            return s;
        } catch (Throwable e) {
            e.printStackTrace();
            Server.sockets.remove(userId);
        }

        return null;
    }

    private String getMd5(String text) {
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] btInput = text.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private int flag = 100;

    private String getUserId() {

        flag++;

        if (flag == 999) {
            flag = 100;
        }

        String randomStr = ((int) (Math.random() * 999999)) + "";

        return getMd5(System.currentTimeMillis() + randomStr) + flag;
    }

    class ContentBean {

        /**
         * content : 就到家
         * toId : af5484ade61722c4f54855bc0d188b6f102
         */

        private String content;
        private String name;
        private int faceId;
        private String toId;

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
}
