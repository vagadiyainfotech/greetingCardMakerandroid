package com.ridecoder.greetingcardmaker.threewaysolutions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<ImageData> bannerImageList;

    public SliderAdapter(Context mContext, ArrayList<ImageData> bannerImageList) {
        this.mContext = mContext;
        this.bannerImageList = bannerImageList;
    }

    @Override
    public int getCount() {
        return bannerImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider_child_item, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.sliderItemIV);
        Glide.with(mContext).load(bannerImageList.get(position).getPhoto()).into(img);
        container.addView(view);
        return view;
    }
}

