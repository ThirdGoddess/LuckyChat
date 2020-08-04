package com.companybt.imsocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.companybt.imsocket.bean.MessageBean;
import com.companybt.imsocket.R;
import com.companybt.imsocket.utils.DemoPublic;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-07-27 23:33
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEMONE = 1;
    private static final int ITEMTWO = 2;
    private static final int ITEMTHREE = 3;

    private Context context;

    private List<MessageBean> data;

    public MessageRecyclerViewAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void addItem(MessageBean messageBean) {
        data.add(messageBean);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case ITEMONE:
                inflate = LayoutInflater.from(context).inflate(R.layout.message_layout1, parent, false);
                holder = new RightHolder(inflate);
                break;
            case ITEMTWO:
                inflate = LayoutInflater.from(context).inflate(R.layout.message_layout2, parent, false);
                holder = new LeftHolder(inflate);
                break;
            case ITEMTHREE:
                inflate = LayoutInflater.from(context).inflate(R.layout.message_layout3, parent, false);
                holder = new SystemHolder(inflate);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LeftHolder) {
            LeftHolder leftHolder = (LeftHolder) holder;

            Glide.with(context).load(idToFace(data.get(position).getFace())).into(leftHolder.face);
            leftHolder.message.setText(data.get(position).getMessage());
            leftHolder.name.setText(data.get(position).getName());

        } else if (holder instanceof RightHolder) {
            RightHolder rightHolder = (RightHolder) holder;
            Glide.with(context).load(DemoPublic.face).into(rightHolder.face);
            rightHolder.message.setText(data.get(position).getMessage());

        } else {

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {


        MessageBean messageBean = data.get(position);

        if (DemoPublic.TYPE1 == messageBean.getType()) {
            return ITEMONE;
        } else if (DemoPublic.TYPE2 == messageBean.getType()) {
            return ITEMTWO;
        } else {
            return ITEMTHREE;
        }

    }

    class LeftHolder extends RecyclerView.ViewHolder {

        ImageView face;
        TextView name;
        TextView message;


        LeftHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            name = itemView.findViewById(R.id.name);
            face = itemView.findViewById(R.id.face);
        }
    }

    class RightHolder extends RecyclerView.ViewHolder {

        TextView message;
        ImageView face;

        RightHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            face = itemView.findViewById(R.id.face);
        }
    }

    class SystemHolder extends RecyclerView.ViewHolder {

        TextView text;

        SystemHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }


    /**
     * 根据id寻找头像
     *
     * @param faceId
     * @return
     */
    private Object idToFace(int faceId) {
        switch (faceId) {
            case 1:
                return R.mipmap.so1;
            case 2:
                return R.mipmap.so2;
            case 3:
                return R.mipmap.so3;
            case 4:
                return R.mipmap.so4;
            case 5:
                return R.mipmap.so5;
            case 6:
                return R.mipmap.so6;
            case 7:
                return R.mipmap.so7;
            case 8:
                return R.mipmap.so8;
            case 9:
                return R.mipmap.so9;
            case 10:
                return R.mipmap.so10;
            case 11:
                return R.mipmap.so11;
            case 12:
                return R.mipmap.so12;
            case 13:
                return R.mipmap.so13;
            case 14:
                return R.mipmap.so14;
            case 15:
                return R.mipmap.so15;
            case 16:
                return R.mipmap.so16;
            case 17:
                return R.mipmap.so17;
            case 18:
                return R.mipmap.so18;
            case 19:
                return R.mipmap.so19;
            default:
                return R.mipmap.so20;
        }
    }


}

