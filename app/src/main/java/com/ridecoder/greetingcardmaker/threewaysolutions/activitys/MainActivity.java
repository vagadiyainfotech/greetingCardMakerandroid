package com.ridecoder.greetingcardmaker.threewaysolutions.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.ImageData;
import com.ridecoder.greetingcardmaker.threewaysolutions.Models.UserData;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.Constant;
import com.ridecoder.greetingcardmaker.threewaysolutions.utils.CustomProgressbar;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener, Serializable {
    Bitmap bm = null;
    FrameLayout frameLayout = null;
    Context mContext;
    Activity mActivity;
    ArrayList<UserData> userDetail;
    ArrayList<ImageData> imageDetail;
    ImageView companyLogoIV, backIMG, locationIV, mailIV, phone1IV;
    RelativeLayout locationRL, mailRL, phone1RV;
    TextView locationTV, phone1TV, mailTV;
    int position;
    TextView toolBarTV;
    ImageView backIV, DownloadIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;
        Intent intent = getIntent();
        userDetail = (ArrayList<UserData>) intent.getSerializableExtra(getString(R.string.user_detail));
        imageDetail = (ArrayList<ImageData>) intent.getSerializableExtra(getString(R.string.image_data));
        position = intent.getIntExtra(getString(R.string.position), 0);
        Log.i("ImageDetailSize", "" + position);
        init();
    }

    private void init() {
        changeStatusBarColor(mActivity);
        ////////////////////////Toolbar///////////////////////////
        toolBarTV = findViewById(R.id.toolBarTV);
        DownloadIV = findViewById(R.id.DownloadIV);
        DownloadIV.setOnClickListener(this);
        backIV = findViewById(R.id.backIV);
        backIV.setOnClickListener(this);
        toolBarTV.setText(imageDetail.get(position).getPhoto_title());

        companyLogoIV = findViewById(R.id.companyLogoIV);
        companyLogoIV.setScaleType(ImageView.ScaleType.FIT_XY);
        companyLogoIV.setAdjustViewBounds(true);
        final Bitmap CompanyLogo;
        Glide.with(mContext)
                .asBitmap()
                .load(userDetail.get(0).getCompany_logo())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        companyLogoIV.setImageBitmap(Constant.tintImage(resource, Color.parseColor(imageDetail.get(position).getText_color())));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        backIMG = findViewById(R.id.backIMG);
        backIMG.setScaleType(ImageView.ScaleType.FIT_XY);
        backIMG.setAdjustViewBounds(true);
        AsyncTask asyncTask = new DownloadImage().execute(stringToURL(imageDetail.get(position).getPhoto()));
        //  Glide.with(mContext).load(imageDetail.get(position).getPhoto()).into(backIMG);

        locationRL = findViewById(R.id.locationRL);
        locationRL.setBackgroundColor(Color.parseColor(imageDetail.get(position).getText_color()));

        locationIV = findViewById(R.id.locationIV);
        locationIV.setScaleType(ImageView.ScaleType.FIT_XY);
        locationIV.setAdjustViewBounds(true);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(mContext, R.drawable.ic_location);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(imageDetail.get(position).getColor()));
        locationIV.setImageDrawable(wrappedDrawable);
        // locationIV.setColorFilter(Color.parseColor(imageDetail.get(position).getText_color()));

        locationTV = findViewById(R.id.locationTV);
        locationTV.setTextColor(Color.parseColor(imageDetail.get(position).getColor()));

        mailRL = findViewById(R.id.mailRL);


        mailTV = findViewById(R.id.mailTV);
        mailTV.setTextColor(Color.parseColor(imageDetail.get(position).getText_color()));
        mailTV.setText(userDetail.get(0).getEmail());


        mailIV = findViewById(R.id.mailIV);
        mailIV.setScaleType(ImageView.ScaleType.FIT_XY);
        mailIV.setAdjustViewBounds(true);
        Drawable unwrappedDeawablemail = AppCompatResources.getDrawable(mContext, R.drawable.ic_mail);
        Drawable wrappedDrawablemail = DrawableCompat.wrap(unwrappedDeawablemail);
        DrawableCompat.setTint(wrappedDrawablemail, Color.parseColor(imageDetail.get(position).getText_color()));
        mailIV.setImageDrawable(wrappedDrawablemail);


        phone1RV = findViewById(R.id.phone1RV);
        phone1IV = findViewById(R.id.phone1IV);
        phone1IV.setScaleType(ImageView.ScaleType.FIT_XY);
        phone1IV.setAdjustViewBounds(true);
        Drawable unwrappedDrawablephone = AppCompatResources.getDrawable(mContext, R.drawable.ic_phone);
        Drawable wrappedDrawablephone = DrawableCompat.wrap(unwrappedDrawablephone);
        DrawableCompat.setTint(wrappedDrawablephone, Color.parseColor(imageDetail.get(position).getText_color()));
        phone1IV.setImageDrawable(wrappedDrawablephone);

        phone1TV = findViewById(R.id.phone1TV);
        phone1TV.setTextColor(Color.parseColor(imageDetail.get(position).getText_color()));
        phone1TV.setText("+91 " + userDetail.get(0).getMobile_number1());
        frameLayout = findViewById(R.id.mainView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                onBackPressed();
                break;
            case R.id.DownloadIV:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
                } else {
                    CreateBitmapAndDownload();
                }

                break;
            default:
                break;
        }
    }

    protected URL stringToURL(String uri) {
        try {
            URL url = new URL(uri);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class DownloadImage extends AsyncTask<URL, Void, Bitmap> {
        protected void onPreExecute() {
            CustomProgressbar.showProgressBar(mContext, false);

        }

        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                return BitmapFactory.decodeStream(bufferedInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                CustomProgressbar.hideProgressBar();
                backIMG.setImageBitmap(result);
            } else {
                // Notify user that an error occurred while downloading image
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 102:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // check whether storage permission granted or not.
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        CreateBitmapAndDownload();
                    }
                }
                break;
        }
    }

    public void CreateBitmapAndDownload() {
        Bitmap b = Bitmap.createBitmap(frameLayout.getWidth(), frameLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        frameLayout.layout(frameLayout.getLeft(), frameLayout.getTop(), frameLayout.getRight(), frameLayout.getBottom());
        frameLayout.draw(c);
        Log.i("bitmap", "" + b);
        try {
            Constant.saveBitmap(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, ShareScreen.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        intent.putExtra("bitmapbytes", bytes);
        startActivity(intent);
    }

}