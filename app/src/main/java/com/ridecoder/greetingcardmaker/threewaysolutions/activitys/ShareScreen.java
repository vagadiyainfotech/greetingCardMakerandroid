package com.ridecoder.greetingcardmaker.threewaysolutions.activitys;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.ridecoder.greetingcardmaker.threewaysolutions.R;
import com.ridecoder.greetingcardmaker.threewaysolutions.databinding.ActivityShareScreenBinding;

public class ShareScreen extends BaseActivity implements View.OnClickListener {
    ActivityShareScreenBinding shareScreenBinding;
    Context mContext;
    Activity mActivity;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareScreenBinding= DataBindingUtil.setContentView(this,R.layout.activity_share_screen);
        mContext=this;
        mActivity=this;
        changeStatusBarColor(mActivity);
        byte[] bytes = getIntent().getByteArrayExtra("bitmapbytes");
        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.i("bitmap",""+bmp);
        init();
    }

    private void init() {
        shareScreenBinding.backIV.setOnClickListener(this);
        shareScreenBinding.homeIV.setOnClickListener(this);

        shareScreenBinding.postBitmapIV.setScaleType(ImageView.ScaleType.FIT_XY);
        shareScreenBinding.postBitmapIV.setAdjustViewBounds(true);
        shareScreenBinding.postBitmapIV.setImageBitmap(bmp);

        shareScreenBinding.facebookIV.setScaleType(ImageView.ScaleType.FIT_XY);
        shareScreenBinding.facebookIV.setAdjustViewBounds(true);
        shareScreenBinding.facebookIV.setOnClickListener(this);

        shareScreenBinding.whatsappIV.setScaleType(ImageView.ScaleType.FIT_XY);
        shareScreenBinding.whatsappIV.setAdjustViewBounds(true);
        shareScreenBinding.whatsappIV.setOnClickListener(this);

        shareScreenBinding.instaIV.setScaleType(ImageView.ScaleType.FIT_XY);
        shareScreenBinding.instaIV.setAdjustViewBounds(true);
        shareScreenBinding.instaIV.setOnClickListener(this);

        shareScreenBinding.moreIV.setScaleType(ImageView.ScaleType.FIT_XY);
        shareScreenBinding.moreIV.setAdjustViewBounds(true);
        shareScreenBinding.moreIV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backIV:
                onBackPressed();
                break;
            case R.id.facebookIV:
                String sharefacebook = "Download awesome festival post using this app!\nClick here to install \n\nhttps://play.google.com/store/apps/details?id=com.ridecoder.greetingcardmaker.threewaysolutions";
                String pathfacebook= MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "Image Description", "Download awesome Festival Post using this app!");
                Uri urifacebook = Uri.parse(pathfacebook);
                Intent sharefacebook2 = new Intent(Intent.ACTION_SEND);
                sharefacebook2.setType("image/jpeg");
                sharefacebook2.setPackage("com.facebook.katana");
                sharefacebook2.putExtra(Intent.EXTRA_TEXT, sharefacebook);
                sharefacebook2.putExtra(Intent.EXTRA_STREAM, urifacebook);
                startActivity(Intent.createChooser(sharefacebook2, "Share Image"));

                break;
            case R.id.whatsappIV:
                String sharewhatsapp1 = "Download awesome festival post using this app!\nClick here to install \n\nhttps://play.google.com/store/apps/details?id=com.ridecoder.greetingcardmaker.threewaysolutions";
                String pathwhatsapp = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "Image Description", "Download awesome Festival Post using this app!");
                Uri uriwhatsapp = Uri.parse(pathwhatsapp);
                Intent sharewhatsapp2 = new Intent(Intent.ACTION_SEND);
                sharewhatsapp2.setType("image/jpeg");
                sharewhatsapp2.setPackage("com.whatsapp");
                sharewhatsapp2.putExtra(Intent.EXTRA_TEXT, sharewhatsapp1);
                sharewhatsapp2.putExtra(Intent.EXTRA_STREAM, uriwhatsapp);
                startActivity(Intent.createChooser(sharewhatsapp2, "Share Image"));

                break;
            case R.id.instaIV:
                String shareinstagram = "Download awesome festival post using this app!\nClick here to install \n\nhttps://play.google.com/store/apps/details?id=com.ridecoder.greetingcardmaker.threewaysolutions";
                String pathinstagram = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "Image Description", "Download awesome Festival Post using this app!");
                Uri uriinstagram = Uri.parse(pathinstagram);
                Intent shareinstagram2 = new Intent(Intent.ACTION_SEND);
                shareinstagram2.setType("image/jpeg");
                shareinstagram2.setPackage("com.instagram.android");
                shareinstagram2.putExtra(Intent.EXTRA_TEXT, shareinstagram);
                shareinstagram2.putExtra(Intent.EXTRA_STREAM, uriinstagram);
                startActivity(Intent.createChooser(shareinstagram2, "Share Image"));

                break;
            case R.id.moreIV:
                String shareBody1 = "Download awesome festival post using this app!\nClick here to install \n\nhttps://play.google.com/store/apps/details?id=com.ridecoder.greetingcardmaker.threewaysolutions";
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "Image Description", "Download awesome Festival Post using this app!");
                Uri uri = Uri.parse(path);
                Intent intentshare = new Intent(Intent.ACTION_SEND);
                intentshare.setType("image/jpeg");
                intentshare.putExtra(Intent.EXTRA_TEXT, shareBody1);
                intentshare.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intentshare, "Share Image"));
                break;
            case R.id.homeIV:
                Intent intent=new Intent(mContext,Drawer.class);
                startActivity(intent);
                ShareScreen.this.finish();
                break;
            default:break;
        }

    }
}