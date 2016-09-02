package com.dream.plmm.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dream.plmm.AppManager;
import com.dream.plmm.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    public static BaseActivity activity;
    DrawerLayout drawerLayout;
    private Class mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        activity = this;
        context = this;
        int layoutid = getLayoutId();
        setContentView(layoutid);
        setStatusBarTranslucent();
        initToolBar();
        initDrawerLayout();
    }
    public abstract int getLayoutId();
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
    }

    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_drawerlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navView = (NavigationView) findViewById(R.id.id_nv_menu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (navView != null) {
            navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_mm:
                            mClass = MainActivity.class;
                            break;
                        case R.id.menu_helathy_info:
                            mClass = HealthyActivity.class;
                            break;
                        case R.id.menu_helathy_foods:
                            break;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            });
        }

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mClass != null) {
                    Intent intent = new Intent(BaseActivity.this, mClass);
                    // 此标志用于启动一个Activity的时候，若栈中存在此Activity实例，则把它调到栈顶。不创建多一个
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    mClass = null;
                }
            }
        });
    }


    // TODO:适配4.4
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT))
//                !(this instanceof NewsDetailActivity || this instanceof PhotoActivity
//                        || this instanceof PhotoDetailActivity))
//                || (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
//                && this instanceof NewsDetailActivity))
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.whole_background);
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void startActivity(Class cls) {
        startActivity(cls, null, true);
    }

    protected void startActivity(Class cls, boolean isfinish) {
        startActivity(cls, null, isfinish);
    }

    protected void startActivity(Class cls, Bundle bundle) {
        startActivity(cls, bundle, true);
    }

    protected void startActivity(Class cls, Bundle bundle, boolean isfinish) {
        if (cls == null || activity == null) return;
        Intent intent = new Intent();
        if (bundle != null)
            intent.putExtras(bundle);
        intent.setClass(activity, cls);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        if (isfinish) finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
        ButterKnife.unbind(this);
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 设置布局文件
     */
//	public abstract void setView();
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
//			overridePendingTransition(R.anim.activity_finish_in, R.anim.activity_finish_out);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}