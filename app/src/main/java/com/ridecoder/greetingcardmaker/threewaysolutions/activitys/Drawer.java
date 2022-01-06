package com.ridecoder.greetingcardmaker.threewaysolutions.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonIOException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rd.PageIndicatorView;
import com.ridecoder.greetingcardmaker.threewaysolutions.BuildConfig;
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.ComplitedItemClick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.DailyItemCLick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.UpcomingItemClick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Interfaces.ViewAllItemClick;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.UserData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.adapters.Complitedimageadapter;
import com.ridecoder.greetingcardmaker.threewaysolutions.adapters.DailyImageAdapter;
import com.ridecoder.greetingcardmaker.threewaysolutions.adapters.SliderAdapter;
import com.ridecoder.greetingcardmaker.threewaysolutions.adapters.UpcommingImageAdapter;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.ApiConstant;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.ApiRestClient;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.Constant;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.CustomProgressbar;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.CustomViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class Drawer extends BaseActivity implements View.OnClickListener, DailyItemCLick, ComplitedItemClick, UpcomingItemClick {
    DrawerLayout drawer_layout;
    private boolean mSlideState;
    private ActionBarDrawerToggle mDrawerToggle;
    Context mContext;
    Activity mActivity;
    ImageView drawerIV;
    CustomViewPager sliderViewPager;
    ArrayList<Integer> imageList;
    PageIndicatorView pageIndicatorView;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500; //delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;    // time in milliseconds between successive task executions.
    ArrayList<UserData> UserDataList;
    ArrayList<ImageData> dailyImageList;
    ArrayList<ImageData> bannerImageList;
    ArrayList<ImageData> upcomingImageList;
    ArrayList<ImageData> complitedImageList;
    TextView editTV, mobile1TV, companyNameTV;
    ImageView bussinessLogoIV;
    ImageData imageData;
    RecyclerView dailyImageRV;
    RecyclerView upcomingImageRV, complitedImageRV;
    DailyImageAdapter dailyImageAdapter;
    RelativeLayout rateAppRL, shareAppRL, moreAppRL;
    ShimmerLayout shimmerLayout;
    Button floatingBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        mContext = this;
        mActivity = this;
        init();
    }

    private void init() {
        dailyImageRV = findViewById(R.id.dailyImageRV);
        upcomingImageRV = findViewById(R.id.upcomingImageRV);
        complitedImageRV = findViewById(R.id.complitedImageRV);
        editTV = findViewById(R.id.editTV);
        editTV.setOnClickListener(this);
        mobile1TV = findViewById(R.id.mobile1TV);
        companyNameTV = findViewById(R.id.companyNameTV);
        bussinessLogoIV = findViewById(R.id.bussinessLogoIV);
        bussinessLogoIV.setScaleType(ImageView.ScaleType.FIT_XY);
        bussinessLogoIV.setAdjustViewBounds(true);
        sliderViewPager = findViewById(R.id.sliderViewPager);
        drawer_layout = findViewById(R.id.drawer_layout);
        mSlideState = false;
        getUserData();
        getImages();
        drawerIV = findViewById(R.id.drawerIV);
        drawerIV.setOnClickListener(this);
        changeStatusBarColor(mActivity);
        setupDrawer();
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        moreAppRL = findViewById(R.id.moreAppRL);
        moreAppRL.setOnClickListener(this);
        shareAppRL = findViewById(R.id.shareAppRL);
        shareAppRL.setOnClickListener(this);
        rateAppRL = findViewById(R.id.rateAppRL);
        rateAppRL.setOnClickListener(this);
        shimmerLayout=findViewById(R.id.shimmer_text);
        shimmerLayout.setMaskWidth(1f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           shimmerLayout.setShimmerColor(getColor(R.color.shimmercolor));
        }
        shimmerLayout.setShimmerAnimationDuration(800);
        shimmerLayout.startShimmerAnimation();

        floatingBTN=findViewById(R.id.floatingBTN);
        floatingBTN.setOnClickListener(this);
    }

    private void getImages() {
        CustomProgressbar.showProgressBar(mContext, false);
        ApiRestClient.get(ApiConstant.get_catagary, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CustomProgressbar.hideProgressBar();
                JSONObject object = null;
                dailyImageList = new ArrayList<>();
                bannerImageList = new ArrayList<>();

                imageData = new ImageData();
                try {
                    object = new JSONObject(response.toString());
                    bannerImageList = new ArrayList<>();
                    dailyImageList = new ArrayList<>();
                    upcomingImageList = new ArrayList<>();
                    complitedImageList = new ArrayList<>();
                    JSONArray banner = object.getJSONArray("banner");
                    for (int i = 0; i < banner.length(); i++) {
                        imageData = new ImageData();

                        JSONObject data = banner.getJSONObject(i);
                        imageData.setId(data.getString(ApiConstant.id));
                        imageData.setCatagary_id(data.getString(ApiConstant.catagary_id));
                        imageData.setPhoto_title(data.getString(ApiConstant.photo_title));
                        imageData.setPhoto(data.getString(ApiConstant.photo));
                        imageData.setColor(data.getString(ApiConstant.color));
                        imageData.setText_color(data.getString(ApiConstant.text_color));
                        bannerImageList.add(imageData);
                        if ((i + 1) == banner.length()) {
                            SetPagerAdapter();
                        }
                    }

                    JSONArray daily = object.getJSONArray("daily");
                    for (int i = 0; i < daily.length(); i++) {
                        imageData = new ImageData();

                        JSONObject data = daily.getJSONObject(i);
                        imageData.setId(data.getString(ApiConstant.id));
                        imageData.setCatagary_id(data.getString(ApiConstant.catagary_id));
                        imageData.setPhoto_title(data.getString(ApiConstant.photo_title));
                        imageData.setPhoto(data.getString(ApiConstant.photo));
                        imageData.setColor(data.getString(ApiConstant.color));
                        imageData.setText_color(data.getString(ApiConstant.text_color));
                        dailyImageList.add(imageData);

                        if ((i + 1) == daily.length()) {
                            setDailyAdapter();
                        }
                    }
                    JSONArray upcoming = object.getJSONArray("upcoming");
                    for (int i = 0; i < upcoming.length(); i++) {
                        imageData = new ImageData();
                        JSONObject data = daily.getJSONObject(i);
                        imageData.setId(data.getString(ApiConstant.id));
                        imageData.setCatagary_id(data.getString(ApiConstant.catagary_id));
                        imageData.setPhoto_title(data.getString(ApiConstant.photo_title));
                        imageData.setPhoto(data.getString(ApiConstant.photo));
                        imageData.setColor(data.getString(ApiConstant.color));
                        imageData.setText_color(data.getString(ApiConstant.text_color));
                        upcomingImageList.add(imageData);
                        if ((i + 1) == upcoming.length()) {
                            setupcomingAdapter();
                        }
                    }
                    JSONArray complited = object.getJSONArray("completed_festival");
                    for (int i = 0; i < complited.length(); i++) {
                        imageData = new ImageData();
                        JSONObject data = complited.getJSONObject(i);
                        imageData.setId(data.getString(ApiConstant.id));
                        imageData.setCatagary_id(data.getString(ApiConstant.catagary_id));
                        imageData.setPhoto_title(data.getString(ApiConstant.photo_title));
                        imageData.setPhoto(data.getString(ApiConstant.photo));
                        imageData.setColor(data.getString(ApiConstant.color));
                        imageData.setText_color(data.getString(ApiConstant.text_color));
                        complitedImageList.add(imageData);
                        if ((i + 1) == upcoming.length()) {
                            setcomplitedAdapter();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomProgressbar.hideProgressBar();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    private void setupcomingAdapter() {
        UpcommingImageAdapter upcommingImageAdapter = new UpcommingImageAdapter(this, upcomingImageList, this);
        upcomingImageRV.setLayoutManager(new GridLayoutManager(this, 3));
        upcomingImageRV.setAdapter(upcommingImageAdapter);
    }

    private void setcomplitedAdapter() {
        Complitedimageadapter complitedimageadapter = new Complitedimageadapter(mContext, complitedImageList, this);
        complitedImageRV.setLayoutManager(new GridLayoutManager(this, 3));
        complitedImageRV.setAdapter(complitedimageadapter);
    }

    private void setDailyAdapter() {
        dailyImageAdapter = new DailyImageAdapter(mContext, dailyImageList, this);
        dailyImageRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        dailyImageRV.setAdapter(dailyImageAdapter);
    }

    public void getUserData() throws JsonIOException {
        RequestParams params = new RequestParams();
        params.put(ApiConstant.device_id, Constant.getDeviceId(mContext));
        ApiRestClient.get(ApiConstant.get_profile, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("getUserData", response.toString());
                JSONObject object = null;
                UserDataList = new ArrayList<>();
                UserData userData;
                try {
                    object = new JSONObject(response.toString());
                    JSONArray success = object.getJSONArray("success");
                    for (int i = 0; i < success.length(); i++) {
                        JSONObject jsonObject = success.getJSONObject(i);
                        String id = jsonObject.getString(ApiConstant.id);
                        String device_id = jsonObject.getString(ApiConstant.device_id);
                        String company_name = jsonObject.getString(ApiConstant.company_name);
                        String company_logo = jsonObject.getString(ApiConstant.company_logo);
                        String address = jsonObject.getString(ApiConstant.address);
                        String mobile_number1 = jsonObject.getString(ApiConstant.mobile_number1);
                        String email = jsonObject.getString(ApiConstant.email);
                        userData = new UserData();
                        userData.setId(id);
                        userData.setDevice_id(device_id);
                        userData.setCompany_name(company_name);
                        userData.setCompany_logo(company_logo);
                        userData.setAddress(address);
                        userData.setMobile_number1(mobile_number1);
                        userData.setEmail(email);
                        UserDataList.add(userData);
                        setUserDataIntoDrawer();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("deviceID", "" + e.getMessage());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void setUserDataIntoDrawer() {
        mobile1TV.setText(" +91 " + UserDataList.get(0).getMobile_number1());
        Glide.with(this).load("" + UserDataList.get(0).getCompany_logo()).into(bussinessLogoIV);

        companyNameTV.setText(UserDataList.get(0).getCompany_name());
    }

    private void setTimer() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == (bannerImageList.size())) {
                    currentPage = 0;
                }
                sliderViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void SetPagerAdapter() {
        SliderAdapter sliderAdapter = new SliderAdapter(mContext, bannerImageList);
        sliderViewPager.setAdapter(sliderAdapter);
        sliderViewPager.setPagingEnabled(true);
        pageIndicatorView.setSelection(0);
        pageIndicatorView.setCount(bannerImageList.size());
        setViewpagerpage();
        setTimer();
    }

    private void setViewpagerpage() {
        sliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onResume() {
        super.onResume();
        if (mSlideState) {
            drawer_layout.closeDrawer(Gravity.START);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawerIV:

                if (mSlideState) {
                    drawer_layout.closeDrawer(Gravity.START);
                } else {
                    drawer_layout.openDrawer(Gravity.START);
                }
                break;
            case R.id.editTV:
                Intent intent = new Intent(mContext, UserDetailActivity.class);
                intent.putExtra(getString(R.string.clickscreen), getString(R.string.bydrawer));
                intent.putExtra("DataList", UserDataList);
                startActivityForResult(intent, 101);
                break;
            case R.id.moreAppRL:
                Uri uri4 = Uri.parse("https://play.google.com/store/apps/developer?id=Ridecoder");
                Intent intent8 = new Intent(Intent.ACTION_VIEW, uri4);
                startActivity(intent8);
                break;
            case R.id.rateAppRL:
                Uri urirate = Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName());
                Intent intentrate = new Intent(Intent.ACTION_VIEW, urirate);
                startActivity(intentrate);

                break;
            case R.id.shareAppRL:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "PostMaker");
                    String shareMessage= "\nLet me recommend you this application\nDownload awesome festival post using this app!\nCreate your festival post with your company logo and company details from this application\nClick here to install\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;

            case R.id.floatingBTN:
                Intent intent1=new Intent(mContext,ImageEdit.class);
                startActivity(intent1);
                break;

            default:
                break;
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mSlideState = true;//is Opened
                drawerView.setEnabled(true);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                view.setEnabled(false);
                mSlideState = false;
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer_layout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void dailyItemClick(int position) {
        if (dailyImageList.size() > 0 && UserDataList.size() > 0) {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra(getString(R.string.image_data), dailyImageList);
            intent.putExtra(getString(R.string.user_detail), UserDataList);
            intent.putExtra(getString(R.string.position), position);
            startActivity(intent);
        }
    }

    @Override
    public void complitedItemClick(int position) {
        if (position == 5) {
            Intent intent = new Intent(mContext, ViewAllActivity.class);
            intent.putExtra(getString(R.string.categories), getString(R.string.complited_festival));
            intent.putExtra(getString(R.string.image_data), complitedImageList);
            intent.putExtra(getString(R.string.user_detail), UserDataList);
            startActivity(intent);
        } else {
            if (dailyImageList.size() > 0 && UserDataList.size() > 0) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(getString(R.string.image_data), dailyImageList);
                intent.putExtra(getString(R.string.user_detail), UserDataList);
                intent.putExtra(getString(R.string.position), position);
                startActivity(intent);
            }
        }
    }

    @Override
    public void upcomingItemClick(int position) {
        if (position == 5) {
            Intent intent = new Intent(mContext, ViewAllActivity.class);
            intent.putExtra(getString(R.string.categories), getString(R.string.upcoming_festival));
            intent.putExtra(getString(R.string.image_data), upcomingImageList);
            intent.putExtra(getString(R.string.user_detail), UserDataList);
            startActivity(intent);
        } else {
            if (dailyImageList.size() > 0 && UserDataList.size() > 0) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(getString(R.string.image_data), dailyImageList);
                intent.putExtra(getString(R.string.user_detail), UserDataList);
                intent.putExtra(getString(R.string.position), position);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101 && requestCode == RESULT_OK) {
            getUserData();
        }
    }
}