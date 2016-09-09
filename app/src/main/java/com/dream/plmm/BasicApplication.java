package com.dream.plmm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.dream.plmm.config.Contants;
import com.dream.plmm.crash.CrashExceptionHandler;
import com.dream.plmm.crash.SimpleCrashReporter;
import com.umeng.socialize.PlatformConfig;
import com.youku.player.YoukuPlayerBaseConfiguration;


/**
 * Created by likun on 16/4/19.
 */
public class BasicApplication extends Application {

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin(Contants.WECHAT_ID, Contants.WECHAT_SECRET);
        PlatformConfig.setSinaWeibo(Contants.WEIBO_ID, Contants.WEIBO_SECRET);
        PlatformConfig.setQQZone(Contants.QQ_ID, Contants.QQ_SECRET);
        // TODO: 16/9/7 to open
    }

    public static YoukuPlayerBaseConfiguration configuration;

    /**
     * app在sd卡的主目录
     */
    private final static String APP_MAIN_FOLDER_NAME = "APP_DEMO";
    /**
     * 本地存放闪退日志的目录
     */
    private final static String CRASH_FOLDER_NAME = "crash";


    private static BasicApplication ourInstance = new BasicApplication();
    private static Context mContext;

    public static BasicApplication getInstance() {
        return ourInstance;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        mContext = getApplicationContext();
        configCollectCrashInfo();
        configuration = new YoukuPlayerBaseConfiguration(this) {
            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                return null;
            }

            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                return null;
            }

            @Override
            public String configDownloadPath() {
                return null;
            }
        };

    }

    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, APP_MAIN_FOLDER_NAME, CRASH_FOLDER_NAME);
        CrashExceptionHandler.CrashExceptionRemoteReport remoteReport = new SimpleCrashReporter();
        crashExceptionHandler.configRemoteReport(remoteReport); //设置友盟统计报错日志回传到远程服务器上
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }

}
