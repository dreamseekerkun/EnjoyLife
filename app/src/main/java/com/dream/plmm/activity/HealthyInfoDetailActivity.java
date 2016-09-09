package com.dream.plmm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.plmm.R;
import com.dream.plmm.bean.HealthyInfoDetailEntity;
import com.dream.plmm.config.HostURL;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.BitmapUtils;
import com.dream.plmm.utils.DateUtils;
import com.dream.plmm.utils.DialogUtil;
import com.dream.plmm.utils.ShareUtil;
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
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthyinfo_detail);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarTranslucent(this);
        id = getIntent().getIntExtra("id", 0);
        initData();
        fab.setOnClickListener(this);
    }

    HealthyInfoDetailEntity healthyInfoDetailEntity;

    private void initData() {
        DialogUtil.show(this);
        Call<HealthyInfoDetailEntity> call = ServiceFactory.getHealthyService().getHealthyDetail(id);
        call.enqueue(new Callback<HealthyInfoDetailEntity>() {
            @Override
            public void onResponse(Call<HealthyInfoDetailEntity> call, Response<HealthyInfoDetailEntity> response) {
                if (response.isSuccessful()) {
                    DialogUtil.close();
                    healthyInfoDetailEntity = response.body();
                    newsDetailTimeStap.setText(DateUtils.ConvertTime(healthyInfoDetailEntity.getTime()));
                    newsDetailBody.setText(Html.fromHtml(healthyInfoDetailEntity.getMessage()));
                    setToolBarLayout(healthyInfoDetailEntity.getTitle());
                    BitmapUtils.display(HealthyInfoDetailActivity.this, HostURL.PicDetail + healthyInfoDetailEntity.getImg(), newsDetailPhotoIv);
                }
            }

            @Override
            public void onFailure(Call<HealthyInfoDetailEntity> call, Throwable t) {
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
        switch (v.getId()){
            case R.id.fab:
                ShareUtil.getInstance(this).share("xxx", "title", "https://www.baidu.com");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }
}
