package com.companybt.imsocket.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.companybt.imsocket.R;
import com.companybt.imsocket.base.BaseActivity;
import com.companybt.imsocket.utils.Client;
import com.companybt.imsocket.utils.DemoPublic;
import com.companybt.imsocket.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class NumberActivity extends BaseActivity implements View.OnClickListener {

    private TextView b1;
    private TextView b2;
    private TextView b3;
    private TextView b4;
    private TextView b5;
    private TextView b6;
    private TextView b7;
    private TextView b8;
    private TextView b9;
    private TextView b10;
    private LinearLayout root;
    private TextView star;

    private ObjectAnimator objectAnimator;

    List<TextView> numberList = new ArrayList<>();
    private TextView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        initView();
        initData();
    }

    private void initData() {

    }


    private void initView() {
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b10 = findViewById(R.id.b10);
        back = findViewById(R.id.back);
        star = findViewById(R.id.star);
        numberList.add(b1);
        numberList.add(b2);
        numberList.add(b3);
        numberList.add(b4);
        numberList.add(b5);
        numberList.add(b6);
        numberList.add(b7);
        numberList.add(b8);
        numberList.add(b9);
        numberList.add(b10);


        b1.setBackgroundResource(R.drawable.number_style1);
        b2.setBackgroundResource(R.drawable.number_style2);
        b3.setBackgroundResource(R.drawable.number_style3);
        b4.setBackgroundResource(R.drawable.number_style4);
        b5.setBackgroundResource(R.drawable.number_style5);
        b6.setBackgroundResource(R.drawable.number_style6);
        b7.setBackgroundResource(R.drawable.number_style7);
        b8.setBackgroundResource(R.drawable.number_style8);
        b9.setBackgroundResource(R.drawable.number_style9);
        b10.setBackgroundResource(R.drawable.number_style10);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);
        back.setOnClickListener(this);
        star.setOnClickListener(this);
        root = findViewById(R.id.root);

        StatusBarUtil.setPaddingSmart(this, root);


        for (int i = 0; i < numberList.size(); i++) {

            objectAnimator = ObjectAnimator.ofFloat(numberList.get(i), "alpha", 0f, 1f);
            objectAnimator.setDuration(1000);
            objectAnimator.start();

            final int finalI = i;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    objectAnimator = ObjectAnimator.ofFloat(numberList.get(finalI), "scaleX", 1f, 1.1f, 1f);
                    objectAnimator.setDuration(400);
                    objectAnimator.start();

                    objectAnimator = ObjectAnimator.ofFloat(numberList.get(finalI), "scaleY", 1f, 1.1f, 1f);
                    objectAnimator.setDuration(400);
                    objectAnimator.start();
                }
            }, i * 40);


        }
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (clickNext()) {
            switch (v.getId()) {
                case R.id.b1:
                    matching(1);
                    break;
                case R.id.b2:
                    matching(2);
                    break;
                case R.id.b3:
                    matching(3);
                    break;
                case R.id.b4:
                    matching(4);
                    break;
                case R.id.b5:
                    matching(5);
                    break;
                case R.id.b6:
                    matching(6);
                    break;
                case R.id.b7:
                    matching(7);
                    break;
                case R.id.b8:
                    matching(8);
                    break;
                case R.id.b9:
                    matching(9);
                    break;
                case R.id.b10:
                    matching(0);
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.star:
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("https://github.com/ThirdGoddess/LuckyChat");
                    intent.setData(content_url);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void matching(int i) {

        Client client = new Client(this, i, DemoPublic.faceId, DemoPublic.name);
        client.start();

    }
}
