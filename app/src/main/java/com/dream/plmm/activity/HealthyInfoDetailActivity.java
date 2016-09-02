package com.dream.plmm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.plmm.R;
import com.dream.plmm.bean.HealthyInfoDetail;
import com.dream.plmm.config.HostURL;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.BitmapUtils;
import com.dream.plmm.utils.DateUtils;
import com.dream.plmm.utils.DialogUtil;
import com.dream.plmm.utils.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by likun on 16/9/2.
 */

public class HealthyInfoDetailActivity extends Activity implements View.OnClickListener {

    int id;
    @Bind(R.id.news_detail_photo_iv)
    ImageView newsDetailPhotoIv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.news_detail_timestap)
    TextView newsDetailTimeStap;
    @Bind(R.id.news_detail_body)
    TextView newsDetailBody;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthyinfo_detail);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarTranslucent(this);
        id = getIntent().getIntExtra("id", 0);
        initData();
        toolbar.setOnClickListener(this);
    }

    HealthyInfoDetail healthyInfoDetail;

    private void initData() {
        DialogUtil.show(this);
        Call<HealthyInfoDetail> call = ServiceFactory.getHealthyService().getHealthyDetail(id);
        call.enqueue(new Callback<HealthyInfoDetail>() {
            @Override
            public void onResponse(Call<HealthyInfoDetail> call, Response<HealthyInfoDetail> response) {
                if (response.isSuccessful()) {
                    DialogUtil.close();
                    healthyInfoDetail = response.body();
                    newsDetailTimeStap.setText(DateUtils.ConvertTime(healthyInfoDetail.getTime()));
                    newsDetailBody.setText(Html.fromHtml(healthyInfoDetail.getMessage()));
                    setToolBarLayout(healthyInfoDetail.getTitle());
                    BitmapUtils.display(HealthyInfoDetailActivity.this, HostURL.PicDetail + healthyInfoDetail.getImg(), newsDetailPhotoIv);
                }
            }

            @Override
            public void onFailure(Call<HealthyInfoDetail> call, Throwable t) {
                DialogUtil.close();
            }
        });
    }

    private void setToolBarLayout(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
