package com.companybt.imsocket.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.companybt.imsocket.bean.FaceBean;
import com.companybt.imsocket.R;
import com.companybt.imsocket.adapter.FaceRecyclerViewAdapter;
import com.companybt.imsocket.base.BaseActivity;
import com.companybt.imsocket.utils.DemoPublic;
import com.companybt.imsocket.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class FaceActivity extends BaseActivity implements FaceRecyclerViewAdapter.onClick {

    private RecyclerView faceRecyclerView;
    private TextView enter;
    private LinearLayout root;
    private Toolbar toolbar;
    private ImageView finger;

    private ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        initView();

        initData();
    }

    private void initData() {

        StatusBarUtil.setPaddingSmart(this, root);

        faceRecyclerView = findViewById(R.id.faceRecyclerView);
        faceRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));

        FaceRecyclerViewAdapter faceRecyclerViewAdapter = new FaceRecyclerViewAdapter(this);

        faceRecyclerViewAdapter.setOnItemOnClickListener(this);

        faceRecyclerView.setAdapter(faceRecyclerViewAdapter);

        List<FaceBean> faceList = new ArrayList<>();
        faceList.add(new FaceBean(1, R.mipmap.so1));
        faceList.add(new FaceBean(2, R.mipmap.so2));
        faceList.add(new FaceBean(3, R.mipmap.so3));
        faceList.add(new FaceBean(4, R.mipmap.so4));
        faceList.add(new FaceBean(5, R.mipmap.so5));
        faceList.add(new FaceBean(6, R.mipmap.so6));
        faceList.add(new FaceBean(7, R.mipmap.so7));
        faceList.add(new FaceBean(8, R.mipmap.so8));
        faceList.add(new FaceBean(9, R.mipmap.so9));
        faceList.add(new FaceBean(10, R.mipmap.so10));
        faceList.add(new FaceBean(11, R.mipmap.so11));
        faceList.add(new FaceBean(12, R.mipmap.so12));
        faceList.add(new FaceBean(13, R.mipmap.so13));
        faceList.add(new FaceBean(14, R.mipmap.so14));
        faceList.add(new FaceBean(15, R.mipmap.so15));
        faceList.add(new FaceBean(20, R.mipmap.so20));
        faceList.add(new FaceBean(16, R.mipmap.so16));
        faceList.add(new FaceBean(17, R.mipmap.so17));
        faceList.add(new FaceBean(18, R.mipmap.so18));
        faceList.add(new FaceBean(19, R.mipmap.so19));

        faceRecyclerViewAdapter.addFaceData(faceList);

    }

    private void initView() {
        enter = (TextView) findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickNext()) {

                    Log.d("mylog-facer", DemoPublic.faceId + "");

                    if (-1 != DemoPublic.faceId) {
                        Intent intent = new Intent(FaceActivity.this, NumberActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FaceActivity.this, "选择一个头像吧，不然别人看不见你", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        root = (LinearLayout) findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickNext()) {
                    DemoPublic.faceId = -1;
                    finish();
                }
            }
        });

        finger = findViewById(R.id.finger);

        animator();
    }

    private void animator() {

        int startTime = 600;

        objectAnimator = ObjectAnimator.ofFloat(finger, "scaleX", 2f);
        objectAnimator.setDuration(startTime);
        objectAnimator.start();

        objectAnimator = ObjectAnimator.ofFloat(finger, "scaleY", 2f);
        objectAnimator.setDuration(startTime);
        objectAnimator.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator = ObjectAnimator.ofFloat(finger, "translationY", 0f, -140);
                objectAnimator.setDuration(400);
                objectAnimator.start();
            }
        }, startTime);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator = ObjectAnimator.ofFloat(finger, "translationX", 0f, -600);
                objectAnimator.setDuration(800);
                objectAnimator.start();
            }
        }, startTime += 700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator = ObjectAnimator.ofFloat(finger, "alpha", 1f, 0f);
                objectAnimator.setDuration(800);
                objectAnimator.start();
            }
        }, startTime += 800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finger.setVisibility(View.GONE);
            }
        }, startTime + 900);


    }

    /**
     * 选择头像监听
     *
     * @param data
     */
    @Override
    public void click(FaceBean data) {
        DemoPublic.faceId = data.getFaceId();
        DemoPublic.face = data.getFace();
    }
}
