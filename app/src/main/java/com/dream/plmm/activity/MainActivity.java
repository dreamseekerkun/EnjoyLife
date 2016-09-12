package com.dream.plmm.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import com.dream.plmm.AppManager;
import com.dream.plmm.R;
import com.dream.plmm.adapter.BaseFragmentStatePagerAdapter;
import com.dream.plmm.bean.ClassifyMMEntity;
import com.dream.plmm.fragment.MMListFragment;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.DialogUtil;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @Bind(R.id.dl_drawerlayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind((R.id.viewpager))
    ViewPager mViewPager;
    private List<ClassifyMMEntity.TngouEntity> MMType = new ArrayList<>();
    private static long DOUBLE_CLICK_TIME = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initViewPager() {
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setAdapter(new listPagerAdapter(getSupportFragmentManager(), MMType));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs) {
            @Override
            public void onPageSelected(int position) {
            }
        });
        mTabs.setupWithViewPager(mViewPager);
    }

    private class listPagerAdapter extends BaseFragmentStatePagerAdapter {

        public listPagerAdapter(FragmentManager supportFragmentManager, List<ClassifyMMEntity.TngouEntity> mmType) {
            super(supportFragmentManager,mmType);
        }

        @Override
        public Fragment getItem(int position) {
            return MMListFragment.getInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MMType.get(position).getDescription();
        }

    }


    protected void initData() {
        Call<ClassifyMMEntity> classsifyMM = ServiceFactory.getMmService().getClassifyMM();
        classsifyMM.enqueue(new Callback<ClassifyMMEntity>() {
            @Override
            public void onResponse(Call<ClassifyMMEntity> call, Response<ClassifyMMEntity> response) {
                if (response.isSuccessful()) {
                    DialogUtil.close();
                    MMType = response.body().getTngou();
                    initViewPager();
                }
            }

            @Override
            public void onFailure(Call<ClassifyMMEntity> call, Throwable t) {
                DialogUtil.close();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                    Snackbar.make(getWindow().getDecorView(), "再按一次退出", Snackbar.LENGTH_SHORT).show();
                    DOUBLE_CLICK_TIME = System.currentTimeMillis();
                } else {
                    AppManager.getInstance().AppExit(context);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
