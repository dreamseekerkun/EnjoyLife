package com.dream.plmm.utils;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by likun on 16/3/14.
 */
public class BitmapUtils {

    public static void display(Context context, String url, ImageView imageView) {
//        Picasso.with(context).load(url).placeholder(R.drawable.loading).error(R.drawable.meinv).into(imageView);
        GlideProxy.getInstance().displayImage(context,url,imageView);
    }
}
