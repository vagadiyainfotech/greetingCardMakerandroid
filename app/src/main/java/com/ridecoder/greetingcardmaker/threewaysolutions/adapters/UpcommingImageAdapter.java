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
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.UpcomingItemClick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;

import java.util.ArrayList;

public class UpcommingImageAdapter extends RecyclerView.Adapter<UpcommingImageAdapter.MyViewHolder> {
    ArrayList<ImageData> upcommingList;
    Context mContext;
    UpcomingItemClick upcomingItemClick;

    public UpcommingImageAdapter(Context mContext, ArrayList<ImageData> upcomingImageList,UpcomingItemClick upcomingItemClick) {
        this.mContext = mContext;
        this.upcommingList = upcomingImageList;
        this.upcomingItemClick = upcomingItemClick;
    }

    @NonNull
    @Override
    public UpcommingImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.upcomming_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcommingImageAdapter.MyViewHolder holder, final int position) {
        if (position == 5) {
           holder.upcomingIV.setImageResource(R.drawable.more);
           holder.upcomingIV.setPadding(60,60,60,60);
            holder.upcomingTitleTV.setText(R.string.view_all);
        } else {
            Glide.with(mContext).load(upcommingList.get(position).getPhoto()).into(holder.upcomingIV);
            holder.upcomingTitleTV.setText(upcommingList.get(position).getPhoto_title());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upcomingItemClick.upcomingItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView upcomingIV;
        TextView upcomingTitleTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            upcomingIV = itemView.findViewById(R.id.upcomingIV);
            upcomingTitleTV = itemView.findViewById(R.id.upcomingTitleTV);
        }
    }
}
