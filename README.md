

### 效果图

先来看一下效果图！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200805005448136.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODgxNjgw,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/202008050056119.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODgxNjgw,size_16,color_FFFFFF,t_70#pic_center)

---


### 服务端


Server类：这个类的作用是负责与客户端建立连接、匹配、为用户分配ID等主要作用


```java
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

```

---

ServerThread类：每一个用户都有着自己的线程，当收到这个消息，就会转发到对应用户的Socket，达成消息转发！

```java
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

```

最后还剩下三个Bean类，分别是：消息转发的内容、用户的基本信息、用户信息交互类

消息转发的内容（MessageBean）

```java
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

```

---

用户的基本信息（UserBean）

```java
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

```


---


用户信息交互类（UserMessageBean）

```java
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

```


结构就是这样的(我可能要加东西，所以就用了SpringBoot)：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200805011317437.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODgxNjgw,size_16,color_FFFFFF,t_70)

---


### 客户端（移动端）
因为这一切涉及到耗时操作，所以均在子线程中完成，然后与主线程通讯完成更新UI操作，列出主要代码，完整版已提交Github

```java
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

```



