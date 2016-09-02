package com.dream.plmm.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dream.plmm.R;
import com.dream.plmm.config.HostURL;
import com.dream.plmm.utils.BitmapUtils;
import com.dream.plmm.utils.SaveImageUtils;
import com.dream.plmm.utils.ShareUtil;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by likun on 16/3/16.
 */
public class PhotoDetailActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {

    ImageView imageView;
    Button btnShare;
    //分享平台

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);
        imageView = (ImageView) findViewById(R.id.iv_detail);
        btnShare = (Button) findViewById(R.id.btn_share);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String imgURL = bundle.getString("imgUrl", "");
        if (!TextUtils.isEmpty(imgURL)) {
            BitmapUtils.display(this, HostURL.PicDetail + imgURL, imageView);
        } else {
            Toast.makeText(this, "imgURL is null ", Toast.LENGTH_SHORT).show();
        }
        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
        btnShare.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                ShareUtil.getInstance(this).share("sssss","title","http://www.baidu.com");
                break;
            case R.id.iv_detail:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoDetailActivity.this);
        builder.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imageView.setDrawingCacheEnabled(true);
                Bitmap imageBitmap = imageView.getDrawingCache();
                if (imageBitmap != null) {
                    new SaveImageUtils(PhotoDetailActivity.this, imageView).execute(imageBitmap);
                }
            }
        });
        builder.show();
        return true;//返回 true 不再调用onClick 方法
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(PhotoDetailActivity.this).onActivityResult(requestCode, resultCode, data);
    }
}
