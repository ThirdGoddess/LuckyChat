package com.companybt.imsocket.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.companybt.imsocket.bean.MessageBean;
import com.companybt.imsocket.R;
import com.companybt.imsocket.SoftKeyBoardListener;
import com.companybt.imsocket.adapter.MessageRecyclerViewAdapter;
import com.companybt.imsocket.utils.Client;
import com.companybt.imsocket.utils.DemoPublic;
import com.companybt.imsocket.view.ChatRecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ChatRecyclerView messageRecyclerView;
    private EditText input;
    private Button send;
    private Toolbar messageToolbar;

    private MessageRecyclerViewAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        EventBus.getDefault().register(this);
        initView();
    }


    private void initView() {

        //改变状态栏字体颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        adapter = new MessageRecyclerViewAdapter(this);
        messageRecyclerView = (ChatRecyclerView) findViewById(R.id.messageRecyclerView);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        messageRecyclerView.setAdapter(adapter);


        messageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(this);
        messageToolbar = (Toolbar) findViewById(R.id.messageToolbar);
        messageToolbar.setOnClickListener(this);

        messageToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewToEnd(false);

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //键盘显示
                recyclerViewToEnd(true);

            }

            @Override
            public void keyBoardHide(int height) {
                //键盘隐藏
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:

                String inputStr = input.getText().toString();

                if (!inputStr.trim().equals("")) {

                    //向对方发送消息
                    Client.sendMessage(inputStr);
                    input.setText("");

                    //适配器添加数据
                    adapter.addItem(new MessageBean(1, DemoPublic.faceId, DemoPublic.name, inputStr));

                    //RecyclerView滚动到底部
                    recyclerViewToEnd(true);

                }

                break;
        }
    }

    /**
     * 列表滚动至底部
     *
     * @param isAnimation
     */
    private void recyclerViewToEnd(boolean isAnimation) {
        if (0 < adapter.getItemCount()) {
            if (isAnimation) {
                messageRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            } else {
                messageRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        }
    }

    /**
     * 收到对方发来消息
     *
     * @param messageBean
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(MessageBean messageBean) {
        if (DemoPublic.RECEIVE_CODE == messageBean.getCode()) {
            adapter.addItem(messageBean);
            recyclerViewToEnd(true);
        }
    }
}
