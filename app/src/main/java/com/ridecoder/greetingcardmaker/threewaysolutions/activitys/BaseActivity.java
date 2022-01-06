package com.ridecoder.greetingcardmaker.threewaysolutions.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;

public class BaseActivity extends AppCompatActivity {
    public void changeStatusBarColor(Activity activity){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.my_statusbar_color));
    }
    ProgressBar progressBar ;

    public void showProgress(Context context){
        progressBar=findViewById(R.id.progress);
            progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgress(){
            progressBar.setVisibility(View.GONE);
    }

    protected void showSnackbar(@NonNull String message) {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
    public void SetToolbar(Boolean isBackbutton, Boolean isTitle, String Title, Boolean isDownloadButton){
        Toolbar toolbar=findViewById(R.id.activityToolbar);
        ImageView backIV=toolbar.findViewById(R.id.backArrowIV);
        ImageView downloadIV=toolbar.findViewById(R.id.downloadIV);

        if(isBackbutton){
            backIV.setVisibility(View.VISIBLE);
        }
        else {
            backIV.setVisibility(View.GONE);
        }
        if(isDownloadButton){
            downloadIV.setVisibility(View.VISIBLE);
        }
        else {
            downloadIV.setVisibility(View.GONE);
        }
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        downloadIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveimage();
            }
        });
    }
    public boolean requestPermission(String permission) {
        boolean isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{permission},
                    READ_WRITE_STORAGE);
        }
        return isGranted;
    }
    public static final int READ_WRITE_STORAGE = 52;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_WRITE_STORAGE:
                isPermissionGranted(grantResults[0] == PackageManager.PERMISSION_GRANTED, permissions[0]);
                break;
        }
    }
    public void isPermissionGranted(boolean isGranted, String permission) {

    }
    public void saveimage(){

    }
}
