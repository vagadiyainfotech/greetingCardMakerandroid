package com.ridecoder.greetingcardmaker.threewaysolutions.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.UserData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.databinding.ActivityUserDetailBinding;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.ApiConstant;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.ApiRestClient;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.Constant;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.CustomProgressbar;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.MyBounceInterpolator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity mActivity;
    Context mContext;
    ActivityUserDetailBinding detailBinding;
    int PICK_IMAGE = 99;
    File file;
    ArrayList<UserData> UserDataList;
    String ClickScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
        mActivity = this;
        mContext = this;
        changeStatusBarColor(mActivity);
        init();

    }

    private void init() {

        Intent i = getIntent();
        UserDataList = (ArrayList<UserData>) i.getSerializableExtra("DataList");
        ClickScreen = i.getStringExtra(getString(R.string.clickscreen));
        if (ClickScreen.equalsIgnoreCase(getString(R.string.bydrawer))) {
            if (UserDataList.size() > 0) {
                setDataIntoField();
            }
        }
        if (ClickScreen.equalsIgnoreCase(getString(R.string.bydrawer))) {
            detailBinding.saveBTN.setText(R.string.update);
        } else if (ClickScreen.equalsIgnoreCase(getString(R.string.bysplashscreen))) {
            detailBinding.saveBTN.setText(R.string.save);
        }
        detailBinding.saveBTN.setOnClickListener(this);
        detailBinding.uploadLogoRL.setOnClickListener(this);
        detailBinding.shimmerText.setMaskWidth(0.7f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            detailBinding.shimmerText.setShimmerColor(getColor(R.color.shimmercolor));
        }
        detailBinding.shimmerText.setShimmerAnimationDuration(700);
        detailBinding.shimmerText.startShimmerAnimation();

    }

    private void setDataIntoField() {
        detailBinding.companyAddressET.setText(UserDataList.get(0).getAddress());
        detailBinding.companyNameET.setText(UserDataList.get(0).getCompany_name());
        detailBinding.mobilenumberET2.setText(UserDataList.get(0).getMobile_number1());

        detailBinding.emailIdET.setText(UserDataList.get(0).getEmail());
        Glide.with(mContext).load(UserDataList.get(0).getCompany_logo()).into(detailBinding.LogoImage);
        file = new File(UserDataList.get(0).getCompany_logo());
    }

    private void ValidateData() {
        if (!detailBinding.companyNameET.getText().toString().isEmpty() || !detailBinding.companyNameET.getText().toString().equalsIgnoreCase("")) {
            if (!detailBinding.mobilenumberET2.getText().toString().isEmpty() || !detailBinding.mobilenumberET2.getText().toString().equalsIgnoreCase("") || (detailBinding.mobilenumberET2.getText().toString().length() == 10)) {
                if (!detailBinding.emailIdET.getText().toString().isEmpty() || !detailBinding.emailIdET.getText().toString().equalsIgnoreCase("") || detailBinding.emailIdET.getText().toString().contains("@gmail.com")) {
                    if (!detailBinding.companyAddressET.getText().toString().isEmpty() || !detailBinding.companyAddressET.getText().toString().equalsIgnoreCase("")) {
                        if (!(file.length() == 0)) {
                            CreateUser();
                        } else {
                            Toast.makeText(mActivity, "Please Select a CompanyLogo", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        detailBinding.companyAddressET.setError("Please Enter Valid Company Address");
                    }
                } else {
                    detailBinding.emailIdET.setError("Please Select Valid Email Address");
                }
            } else {
                detailBinding.mobilenumberET2.setError("Please Enter Valid Mobile Number");
            }
        } else {
            detailBinding.companyNameET.setError("Please Enter Valid Company Name");
        }
    }

    private void CreateUser() {

        UserData userData = new UserData();
        userData.setCompany_name(detailBinding.companyNameET.getText().toString());
        userData.setMobile_number1(detailBinding.mobilenumberET2.getText().toString());
        userData.setEmail(detailBinding.emailIdET.getText().toString());
        userData.setAddress(detailBinding.companyAddressET.getText().toString());

        if (file.length() > 0) {
            RequestParams params = new RequestParams();
            params.put(ApiConstant.company_name, userData.getCompany_name());
            params.put(ApiConstant.mobile_number1, userData.getMobile_number1());
            params.put(ApiConstant.email, userData.getEmail());
            params.put(ApiConstant.device_id, Constant.getDeviceId(mContext));
            params.put(ApiConstant.address, userData.getAddress());
            try {
                params.put(ApiConstant.company_logo, file);
            } catch (FileNotFoundException e) {

            }
            try {
                if (ClickScreen.equalsIgnoreCase(getString(R.string.bysplashscreen))) {
                    getPublicTimeline(ApiConstant.create_profile, params);
                } else if (ClickScreen.equalsIgnoreCase(getString(R.string.bydrawer))) {
                    getPublicTimeline(ApiConstant.update_profile, params);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, "Please Select a CompanyLogo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uploadLogoRL:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                } else {
                    getImage();
                }
                break;
            case R.id.saveBTN:
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.animation);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);
                detailBinding.saveBTN.startAnimation(myAnim);
                ValidateData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getImage();
                    } else {
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    private void getImage() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                detailBinding.LogoImage.setScaleType(ImageView.ScaleType.FIT_XY);
                detailBinding.LogoImage.setAdjustViewBounds(true);
                detailBinding.LogoImage.setImageBitmap(Constant.modifyOrientation(bitmap, Constant.getRealPathFromURI(mContext, uri)));
                file = new File(Constant.getRealPathFromURI(mContext, uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getPublicTimeline(String url, RequestParams params) throws JSONException {
        CustomProgressbar.showProgressBar(mContext, false);
        ApiRestClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i("NewRes", "" + response.toString());
                CustomProgressbar.hideProgressBar();
                Intent intent = new Intent(mContext, Drawer.class);
                startActivity(intent);
                UserDetailActivity.this.finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Log.i("NewRes", "" + timeline.toString());
                CustomProgressbar.hideProgressBar();

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.v("TAG", "onFailure" + responseString);
                CustomProgressbar.hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.v("TAG", "onFailure" + errorResponse);
                CustomProgressbar.hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.v("TAG", "onFailure" + errorResponse);
                CustomProgressbar.hideProgressBar();
            }
        });
    }
}
