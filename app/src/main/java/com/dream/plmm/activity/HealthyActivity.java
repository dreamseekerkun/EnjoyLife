package com.dream.plmm.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.dream.plmm.R;
import com.dream.plmm.adapter.BaseFragmentStatePagerAdapter;
import com.dream.plmm.bean.ClassifyHealthyEntity;
import com.dream.plmm.fragment.HealthyFragment;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by likun on 16/9/2.
 */
public class HealthyActivity extends BaseActivity {


    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind((R.id.viewpager))
    ViewPager mViewPager;
    private List<ClassifyHealthyEntity.TngouEntity> healthyType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        Call<ClassifyHealthyEntity> call = ServiceFactory.getHealthyService().getHealthyClassify();
        call.enqueue(new Callback<ClassifyHealthyEntity>() {
            @Override
            public void onResponse(Call<ClassifyHealthyEntity> call, Response<ClassifyHealthyEntity> response) {
                if (response.isSuccessful()) {
                    DialogUtil.close();
                    healthyType = response.body().getTngou();
                    initViewPager();
                }
            }

            @Override
            public void onFailure(Call<ClassifyHealthyEntity> call, Throwable t) {
                DialogUtil.close();
            }
        });
    }

    private void initViewPager() {

        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setAdapter(new listPagerAdapter(getSupportFragmentManager(), healthyType));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs) {
            @Override
            public void onPageSelected(int position) {
            }
        });
        mTabs.setupWithViewPager(mViewPager);
    }

    private class listPagerAdapter extends BaseFragmentStatePagerAdapter {

        public listPagerAdapter(FragmentManager fm, List<ClassifyHealthyEntity.TngouEntity> healthyType) {
            super(fm,healthyType);
        }

        @Override
        public Fragment getItem(int position) {
            return HealthyFragment.getInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return healthyType.get(position).getTitle();
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
