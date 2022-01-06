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
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.ComplitedItemClick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.DailyItemCLick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.activitys.Drawer;

import java.util.ArrayList;

public class Complitedimageadapter extends RecyclerView.Adapter<Complitedimageadapter.MyViewHolder> {
    Context mContext;
    ArrayList<ImageData> complitedImageData;
    ComplitedItemClick complitedItemClick;

    public Complitedimageadapter(Context mContext, ArrayList<ImageData> complitedImageData,ComplitedItemClick complitedItemClick) {
        this.mContext = mContext;
        this.complitedImageData = complitedImageData;
        this.complitedItemClick = complitedItemClick;
    }

    @NonNull
    @Override
    public Complitedimageadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.upcomming_item, parent, false);
        return new Complitedimageadapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Complitedimageadapter.MyViewHolder holder, final int position) {
        if (position == 5) {
            holder.upcomingIV.setImageResource(R.drawable.more);
            holder.upcomingIV.setPadding(60,60,60,60);
            holder.upcomingTitleTV.setText(R.string.view_all);
        } else {
            Glide.with(mContext).load(complitedImageData.get(position).getPhoto()).into(holder.upcomingIV);
            holder.upcomingTitleTV.setText(complitedImageData.get(position).getPhoto_title());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complitedItemClick.complitedItemClick(position);
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
