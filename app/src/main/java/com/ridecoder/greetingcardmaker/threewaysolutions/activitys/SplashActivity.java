package com.ridecoder.greetingcardmaker.threewaysolutions.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.ApiConstant;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.ApiRestClient;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends BaseActivity {
    Activity mActivity;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mActivity = this;
        mContext = this;
        init();
    }

    private void init() {
        changeStatusBarColor(mActivity);
        ImageView imageView= findViewById(R.id.splashIV);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        RequestParams params = new RequestParams();
        params.put(ApiConstant.device_id, Constant.getDeviceId(mContext));
        try {
            getuser(ApiConstant.user_login, params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getuser(String url, RequestParams params) throws JSONException {
        ApiRestClient.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("NewRes", "" + response.toString());
                JSONObject object = null;
                try {
                    object = new JSONObject(String.valueOf(response));
                    String success = (String) object.get("success");
                    if (success.equals("1")) {
                        Intent intent = new Intent(mContext, Drawer.class);
                        startActivity(intent);
                        SplashActivity.this.finish();

                    } else if (success.equals("0")) {
                        Intent intent = new Intent(mContext, UserDetailActivity.class);
                        intent.putExtra( getString(R.string.clickscreen),getString(R.string.bysplashscreen));
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("NewRes", "" + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}