package com.dream.plmm.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import com.dream.plmm.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by likun on 16/9/2.
 */
public class StatusBarUtil {

    // TODO:适配4.4
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarTranslucent(Activity activity) {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT))
//                !(this instanceof NewsDetailActivity || this instanceof PhotoActivity
//                        || this instanceof PhotoDetailActivity))
//                || (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
//                && this instanceof NewsDetailActivity))
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.whole_background);
        }
    }
}
