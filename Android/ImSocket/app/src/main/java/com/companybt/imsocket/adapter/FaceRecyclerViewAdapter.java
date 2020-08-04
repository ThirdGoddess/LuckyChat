package com.companybt.imsocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.companybt.imsocket.bean.FaceBean;
import com.companybt.imsocket.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-07-27 23:58
 */
public class FaceRecyclerViewAdapter extends RecyclerView.Adapter<FaceRecyclerViewAdapter.FaceHolder> {

    private Context context;
    private List<FaceBean> data;

    public FaceRecyclerViewAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public FaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaceHolder(LayoutInflater.from(context).inflate(R.layout.item_face, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaceHolder holder, final int position) {

        final FaceBean faceBean = data.get(position);

        //头像
        Glide.with(context).load(faceBean.getFace()).into(holder.face);
        if (faceBean.isSelect()) {
            holder.select.setVisibility(View.VISIBLE);
        } else {
            holder.select.setVisibility(View.GONE);
        }

        holder.face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labeling(position);
                onClick.click(faceBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addFaceData(List<FaceBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 贴标签选中
     *
     * @param index
     */
    public void labeling(int index) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setSelect(false);
        }
        data.get(index).setSelect(true);
        notifyDataSetChanged();
    }


    //==============================================================================================

    private onClick onClick;

    /**
     * 监听事件
     */
    public void setOnItemOnClickListener(onClick onClick) {
        this.onClick = onClick;
    }

    public interface onClick {
        void click(FaceBean data);
    }

    class FaceHolder extends RecyclerView.ViewHolder {

        ImageView face;
        ImageView select;

        FaceHolder(@NonNull View itemView) {
            super(itemView);
            face = itemView.findViewById(R.id.face);
            select = itemView.findViewById(R.id.select);
        }
    }
}
