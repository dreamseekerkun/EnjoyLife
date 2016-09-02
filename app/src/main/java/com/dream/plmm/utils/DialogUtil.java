package com.dream.plmm.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by likun on 16/7/29.
 */
public class DialogUtil {

    private static ProgressDialog mprogressDialog;
    private static boolean isShowing = false;

    public static void show(Context context) {
        // 提示框已经显示
        if (isShowing) {
            return;
        }
        if (context != null) {
            {
                mprogressDialog = new ProgressDialog(context);
                mprogressDialog.setTitle("提示");
                mprogressDialog.setMessage("正在加载数据");
                mprogressDialog.setCancelable(true);
                mprogressDialog.show();
            }
        }
    }

    public static void close() {

        if (mprogressDialog != null) {
            if (mprogressDialog.isShowing()) {
                try {
                    mprogressDialog.dismiss();
                    isShowing = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
