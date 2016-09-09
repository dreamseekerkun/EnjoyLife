package com.dream.plmm.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dream.plmm.R;
import com.dream.plmm.config.HostURL;
import com.dream.plmm.utils.BitmapUtils;
import com.dream.plmm.utils.SaveImageUtils;
import com.dream.plmm.utils.ShareUtil;
import com.umeng.socialize.UMShareAPI;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by likun on 16/3/16.
 */
public class PhotoDetailActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {

    String imgURL;
    String imgTitle;
    @Bind(R.id.iv_detail)
    ImageView ivDetail;
    @Bind(R.id.fab)
    FloatingActionButton fabShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        imgURL = bundle.getString("imgUrl", "");
        imgTitle = bundle.getString("imgTitle", "");
        if (!TextUtils.isEmpty(imgURL)) {
            BitmapUtils.display(this, HostURL.PicDetail + imgURL, ivDetail);
        } else {
            Toast.makeText(this, "imgURL is null ", Toast.LENGTH_SHORT).show();
        }
        ivDetail.setOnClickListener(this);
        ivDetail.setOnLongClickListener(this);
        fabShare.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                ShareUtil.getInstance(this).shareImage(HostURL.PicDetail + imgURL);
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
                ivDetail.setDrawingCacheEnabled(true);
                Bitmap imageBitmap = ivDetail.getDrawingCache();
                if (imageBitmap != null) {
                    new SaveImageUtils(PhotoDetailActivity.this, ivDetail).execute(imageBitmap);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
