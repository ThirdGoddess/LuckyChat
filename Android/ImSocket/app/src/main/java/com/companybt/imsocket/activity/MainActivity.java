package com.companybt.imsocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.companybt.imsocket.R;
import com.companybt.imsocket.base.BaseActivity;
import com.companybt.imsocket.utils.DemoPublic;
import com.companybt.imsocket.utils.StatusBarUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText account;
    private TextView enter;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        account = (EditText) findViewById(R.id.account);
        enter = (TextView) findViewById(R.id.enter);
        enter.setOnClickListener(this);
        root = (LinearLayout) findViewById(R.id.root);
        StatusBarUtil.setPaddingSmart(this, root);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter:
                if (!"".equals(account.getText().toString().trim())) {
                    DemoPublic.name = account.getText().toString();
                    Intent intent = new Intent(this, FaceActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "输入点什么吧", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}


