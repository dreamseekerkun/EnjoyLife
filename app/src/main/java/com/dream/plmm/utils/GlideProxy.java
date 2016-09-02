package com.dream.plmm.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dream.plmm.R;
import com.dream.plmm.widget.GlideCircleTransform;

import java.io.File;

/**
 * Created by likun on 16/4/20.
 * 加载图片工具类
 */
public class GlideProxy {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static class ImageLoaderHolder {
        private static final GlideProxy INSTANCE = new GlideProxy();

    }

    private GlideProxy() {
    }

    public static final GlideProxy getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }


    //直接加载网络图片
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .placeholder(R.drawable.loading)//缺省的占位图片，一般可以设置成一个加载中的进度GIF图
                .error(R.drawable.meinv)//加载失败默认图片
                .into(imageView);
    }


    //加载SD卡图片
    public void displayImage(Context context, File file, ImageView imageView) {
        Glide
                .with(context)
                .load(file)
                .centerCrop()
                .into(imageView);

    }

    //加载SD卡图片并设置大小
    public void displayImage(Context context, File file, ImageView imageView, int width, int height) {
        Glide
                .with(context)
                .load(file)
                .override(width, height)
                .centerCrop()
                .into(imageView);

    }

    //加载网络图片并设置大小
    public void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .override(width, height)
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片
    public void displayImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片显示为圆形图片
    public void displayCricleImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    //加载网络图片显示为圆形图片
    public void displayCricleImage(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .into(imageView);
    }

    //加载SD卡图片显示为圆形图片
    public void displayCricleImage(Context context, File file, ImageView imageView) {
        Glide
                .with(context)
                .load(file)
                .centerCrop()
                .transform(new GlideCircleTransform(context))
                .into(imageView);

    }

    //将资源ID转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

}
