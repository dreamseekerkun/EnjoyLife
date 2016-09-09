package com.dream.plmm.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.dream.plmm.R;
import com.dream.plmm.adapter.BaseFragmentStatePagerAdapter;
import com.dream.plmm.bean.ResponseVideosListEntity;
import com.dream.plmm.bean.VideosListEntity;
import com.dream.plmm.config.Contants;
import com.dream.plmm.fragment.VideoListFragment;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by likun on 16/9/5.
 */
public class VideoActivity extends BaseActivity {

    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind((R.id.viewpager))
    ViewPager mViewPager;
    Map<String,String> queryMap;
    private List<VideosListEntity> videoType = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTabs();
        initData();
    }


    protected void initData() {
        queryMap = new HashMap<>();
        queryMap.put("keyword","热门");
        queryMap.put("page","1");
        queryMap.put("count","20");
        queryMap.put("public_type","all");
        queryMap.put("paid","0");
        queryMap.put("orderby","published");
        queryMap.put("client_id", Contants.CLIENT_ID);
        Call<ResponseVideosListEntity> call = ServiceFactory.getVideoService().getVideoInfos(queryMap);
        call.enqueue(new Callback<ResponseVideosListEntity>() {
            @Override
            public void onResponse(Call<ResponseVideosListEntity> call, Response<ResponseVideosListEntity> response) {
                if(response.isSuccessful()){
                    DialogUtil.close();
                    videoType = response.body().getVideos();
                    initViewPager();
                }
            }

            @Override
            public void onFailure(Call<ResponseVideosListEntity> call, Throwable t) {
                t.printStackTrace();
                DialogUtil.close();
            }
        });
    }

    private void initViewPager() {

        mTabs.setTabMode(TabLayout.MODE_FIXED);
        mViewPager.setAdapter(new listPagerAdapter(getSupportFragmentManager(),titleList));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs) {
            @Override
            public void onPageSelected(int position) {
            }
        });
        mTabs.setupWithViewPager(mViewPager);
    }

    private void initTabs() {
        titleList.add("热门");
        titleList.add("搞笑");
        titleList.add("动漫");
        titleList.add("综艺");
    }

    private class listPagerAdapter extends BaseFragmentStatePagerAdapter {

        public listPagerAdapter(FragmentManager fm, List<String> titleList) {
            super(fm,titleList);
        }

        @Override
        public Fragment getItem(int position) {
            return VideoListFragment.getInstance(position);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
