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
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.ViewAllItemClick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.activitys.ViewAllActivity;

import java.util.ArrayList;

public class ViewAllImageAdapter extends RecyclerView.Adapter<ViewAllImageAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<ImageData> allDataList;
    ViewAllItemClick viewAllItemClick;
    public ViewAllImageAdapter(Context mContext, ArrayList<ImageData> allDataList, ViewAllItemClick viewAllItemClick) {
        this.mContext = mContext;
        this.allDataList = allDataList;
        this.viewAllItemClick = viewAllItemClick;
    }

    @NonNull
    @Override
    public ViewAllImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.upcomming_item, parent, false);
        return new ViewAllImageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllImageAdapter.MyViewHolder holder, final int position) {
        Glide.with(mContext).load(allDataList.get(position).getPhoto()).into(holder.upcomingIV);
        holder.upcomingTitleTV.setText(allDataList.get(position).getPhoto_title());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllItemClick.viewAllItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allDataList.size();
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
