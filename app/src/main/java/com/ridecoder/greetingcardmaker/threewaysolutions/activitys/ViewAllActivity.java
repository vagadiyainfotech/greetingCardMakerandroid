package com.ridecoder.greetingcardmaker.threewaysolutions.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.ViewAllItemClick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.UserData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.adapters.Complitedimageadapter;
import com.ridecoder.greetingcardmaker.threewaysolutions.adapters.ViewAllImageAdapter;

import java.util.ArrayList;

public class ViewAllActivity extends BaseActivity implements ViewAllItemClick {
    Context mContext;
    Activity mActivity;
    RecyclerView viewAllImageRV;
    TextView toolBarTV;
    ImageView backIV;
    ArrayList<ImageData> imageDetail;
    ArrayList<UserData> userDetail;
    String categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        mContext = this;
        mActivity = this;
        changeStatusBarColor(mActivity);
        Intent intent = getIntent();
        categories = intent.getStringExtra(getString(R.string.categories));
        userDetail= (ArrayList<UserData>) intent.getSerializableExtra(getString(R.string.user_detail));
        imageDetail= (ArrayList<ImageData>) intent.getSerializableExtra(getString(R.string.image_data));

        init();
    }

    private void init() {
        viewAllImageRV = findViewById(R.id.viewAllImageRV);
        backIV = findViewById(R.id.backIV);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolBarTV = findViewById(R.id.toolBarTV);
        ViewAllImageAdapter viewAllImageAdapter = new ViewAllImageAdapter(mContext, imageDetail, this);
        viewAllImageRV.setLayoutManager(new GridLayoutManager(this, 3));
        viewAllImageRV.setAdapter(viewAllImageAdapter);
        toolBarTV.setText(categories.toString());

    }

    @Override
    public void viewAllItemClick(int position) {
        if(imageDetail.size()>0&&userDetail.size()>0) {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra(getString(R.string.image_data), imageDetail);
            intent.putExtra(getString(R.string.user_detail), userDetail);
            intent.putExtra(getString(R.string.position), position);
            startActivity(intent);
        }
    }
}