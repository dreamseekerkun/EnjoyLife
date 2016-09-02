package com.dream.plmm.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;

import com.dream.plmm.R;
import com.dream.plmm.bean.ClassifyHealthy;
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


    @Bind(R.id.dl_drawerlayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind((R.id.viewpager))
    ViewPager mViewPager;
    private List<ClassifyHealthy.TngouEntity> healthyType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        DialogUtil.show(this);
        Call<ClassifyHealthy> call = ServiceFactory.getHealthyService().getHealthyClassify();
        call.enqueue(new Callback<ClassifyHealthy>() {
            @Override
            public void onResponse(Call<ClassifyHealthy> call, Response<ClassifyHealthy> response) {
                if (response.isSuccessful()) {
                    DialogUtil.close();
                    healthyType = response.body().getTngou();
                    initViewPager();
                }
            }

            @Override
            public void onFailure(Call<ClassifyHealthy> call, Throwable t) {
                DialogUtil.close();
            }
        });
    }

    private void initViewPager() {
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setAdapter(new listPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs) {
            @Override
            public void onPageSelected(int position) {
            }
        });
        mTabs.setupWithViewPager(mViewPager);
    }

    private class listPagerAdapter extends FragmentStatePagerAdapter {

        public listPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HealthyFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return healthyType.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return healthyType.get(position).getTitle();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
