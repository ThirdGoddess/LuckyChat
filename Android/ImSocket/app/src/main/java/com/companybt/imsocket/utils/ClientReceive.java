package com.companybt.imsocket.utils;

import com.companybt.imsocket.bean.MessageBean;
import com.google.gson.Gson;

import java.io.BufferedReader;

import de.greenrobot.event.EventBus;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-08-03 00:12
 */
public class ClientReceive implements Runnable {

    private BufferedReader bufferedReader;

    ClientReceive(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run() {
        String content;

        try {
            while (null != (content = bufferedReader.readLine())) {
                MessageBean messageBean = new Gson().fromJson(content, MessageBean.class);
                messageBean.setCode(DemoPublic.RECEIVE_CODE);
                //收到消息
                EventBus.getDefault().post(messageBean);

            }
        } catch (Exception ignored) {

        }

    }
}
