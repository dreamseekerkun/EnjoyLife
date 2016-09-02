package com.dream.plmm.activity;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.dream.plmm.R;

/**
 * Created by likun on 16/8/19.
 */
public class SharePopupWindow extends PopupWindow {

    private View mainview;

    public SharePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mainview=inflater.inflate(R.layout.activity_share, null);
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setContentView(mainview);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
}
