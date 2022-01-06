package com.ridecoder.greetingcardmaker.threewaysolutions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.DailyItemCLick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;

import java.util.ArrayList;

public class DailyImageAdapter extends RecyclerView.Adapter<DailyImageAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<ImageData> dailyImageList;
    DailyItemCLick dailyItemCLick;
    public DailyImageAdapter(Context mContext, ArrayList<ImageData> dailyImageList,DailyItemCLick dailyItemCLick) {
        this.mContext=mContext;
        this.dailyImageList=dailyImageList;
        this.dailyItemCLick=dailyItemCLick;

    }

    @NonNull
    @Override
    public DailyImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.dailyimageitems,parent,false);
           return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyImageAdapter.MyViewHolder holder, final int position) {
        Glide.with(mContext).load(dailyImageList.get(position).getPhoto()).into(holder.dailyImageIV);
        holder.postTitle.setText(dailyImageList.get(position).getPhoto_title());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyItemCLick.dailyItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyImageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView dailyImageIV;
        TextView postTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dailyImageIV=itemView.findViewById(R.id.dailyImageIV);
            postTitle=itemView.findViewById(R.id.postTitle);
        }
    }
}
