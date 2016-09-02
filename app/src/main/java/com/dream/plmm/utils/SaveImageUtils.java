package com.dream.plmm.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.dream.plmm.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by likun on 16/3/18.
 */
public class SaveImageUtils extends AsyncTask<Bitmap, Void, String> {
    Activity mActivity;
    ImageView mImageView;

    public SaveImageUtils(Activity activity, ImageView imageView) {
        this.mImageView = imageView;
        this.mActivity = activity;
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        String result = mActivity.getResources().getString(R.string.save_picture_failed);
        try {
            String sdcard = Environment.getExternalStorageDirectory().toString();
            File file = new File(sdcard + "/MeiZi");
            if (!file.exists()) {
                file.mkdirs();
            }
            File imageFile = new File(file.getAbsolutePath(), "命名" + ".jpg");
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(imageFile);
            Bitmap image = params[0];
            image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            result = mActivity.getResources().getString(R.string.save_picture_success, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
        mImageView.setDrawingCacheEnabled(false);
    }
}