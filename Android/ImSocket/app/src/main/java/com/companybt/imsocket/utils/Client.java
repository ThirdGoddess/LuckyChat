package com.companybt.imsocket.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.companybt.imsocket.activity.MessageActivity;
import com.companybt.imsocket.bean.OtherPartyBean;
import com.companybt.imsocket.bean.SendMessageBean;
import com.companybt.imsocket.bean.UserBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-07-30 02:17
 */
public class Client {

    private int number;
    private int face;
    private String name;
    private Context context;
    public static Socket socket = null;
    private static OtherPartyBean otherPartyBean = null;

    //加载框变量
    private ProgressDialog progressDialog;

    public Client(Context context, int number, int face, String name) {
        this.context = context;
        this.number = number;
        this.face = face;
        this.name = name;
    }

    public void start() {
        //弹出加载框
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                showProgressDialog("正在为你匹配");
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("bytedemo.com", 8892);
                    //向服务器上报基本信息
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    printStream.println(new Gson().toJson(new UserBean(number, name, face)));
                    printStream.flush();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final String otherPartyStr = bufferedReader.readLine();
                    otherPartyBean = new Gson().fromJson(otherPartyStr, OtherPartyBean.class);

                    //对方信息
                    //Switch to main thread
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(context, "匹配成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MessageActivity.class);
                            intent.putExtra("otherParty", otherPartyStr);
                            context.startActivity(intent);
                        }
                    });

                    //开启接收消息线程
                    new Thread(new ClientReceive(bufferedReader)).start();

                } catch (IOException e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "与服务器连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void showProgressDialog(String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);//设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();

        //设置超时自动消失
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //取消加载框

                if (dismissProgressDialog()) {
                    //超时处理
                    Toast.makeText(context, "匹配失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, 60000);//超时时间60秒
    }


    public Boolean dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }

    /**
     * 向对方发送文本内容
     *
     * @param content
     */
    public static void sendMessage(final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != socket && null != otherPartyBean) {
                        PrintStream printStream = new PrintStream(socket.getOutputStream());
                        printStream.println(new Gson().toJson(new SendMessageBean(content, DemoPublic.name, DemoPublic.faceId, otherPartyBean.getUserId())));
                        printStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}