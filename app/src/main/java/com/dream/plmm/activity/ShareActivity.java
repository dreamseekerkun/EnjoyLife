package com.dream.plmm.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dream.plmm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by likun on 16/8/19.
 */
public class ShareActivity extends BaseActivity {
    @Bind(R.id.share_wechat)
    ImageButton shareWechat;
    @Bind(R.id.share_wechatmoments)
    ImageButton shareWechatmoments;
    @Bind(R.id.share_qq)
    ImageButton shareQq;
    @Bind(R.id.share_qzone)
    ImageButton shareQzone;
    @Bind(R.id.share_cancel_btn)
    Button shareCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

}
