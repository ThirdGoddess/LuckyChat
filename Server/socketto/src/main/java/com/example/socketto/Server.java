package com.example.socketto;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Server {

    private ServerSocket serverSocket = null;
    static Map<String, UserBean> sockets = Collections.synchronizedMap(new HashMap<>());

    /**
     * 启动服务
     */
    public void startServer() {

        System.out.println("服务准备启动");

        try {
            serverSocket = new ServerSocket(8892);

            while (true) {
                System.out.println("等待客户端连接");
                Socket socket = serverSocket.accept();


                //此时连接需要用户发过来他的基本信息
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String content = bufferedReader.readLine();


                UserBean userBean = new Gson().fromJson(content, UserBean.class);

                String userId = getUserId();
                userBean.setId(userId);
                userBean.setSocket(socket);
                sockets.put(userId, userBean);

                System.out.println(userBean.toString() + " 当前在线数量：" + sockets.size());


                //寻找同一个幸运数字的人
                for (Map.Entry<String, UserBean> entry : Server.sockets.entrySet()) {

                    //筛选条件：同一个幸运数字 并且 没有匹配过的人 并且 不是自己
                    if (userBean.getNumber() == entry.getValue().getNumber() && !entry.getValue().isMatching() && !userBean.getId().equals(entry.getValue().getId())) {
                        //匹配成功

                        //对方信息
                        UserBean value = entry.getValue();

                        //将双方匹配状态都改为true
                        changeStatus(userBean.getId());
                        changeStatus(value.getId());

                        UserMessageBean userMessageBean1 = new UserMessageBean(value.getId(), value.getNumber(), value.getName(), value.getFace());
                        UserMessageBean userMessageBean2 = new UserMessageBean(userBean.getId(), userBean.getNumber(), userBean.getName(), userBean.getFace());

                        //汇报双方id
                        PrintStream printStream1 = new PrintStream(socket.getOutputStream());
                        printStream1.println(new Gson().toJson(userMessageBean1));
                        PrintStream printStream2 = new PrintStream(value.getSocket().getOutputStream());
                        printStream2.println(new Gson().toJson(userMessageBean2));

                        //匹配成功
                        System.out.println("匹配成功");

                        break;
                    }
                }

                System.out.println("为用户创建消息收发流");
                //为用户创建消息收发流
                new Thread(new ServerThread(socket)).start();

            }
        } catch (
                IOException e) {
            e.printStackTrace();
            System.out.println("服务异常");
        }


    }

    private void changeStatus(String id) {
        UserBean userBean = sockets.get(id);
        userBean.setMatching(true);
        sockets.put(id, userBean);
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


}
