package com.dream.plmm;

import android.app.Application;
import android.content.Context;

import com.dream.plmm.crash.CrashExceptionHandler;
import com.dream.plmm.crash.SimpleCrashReporter;
import com.umeng.socialize.PlatformConfig;


/**
 * Created by likun on 16/4/19.
 */
public class BasicApplication extends Application {

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx429c461724b1e524", "f0c181279b6e585d8b1b8524b0d827fd");
        PlatformConfig.setSinaWeibo("720696056", "a2482148dd048ca1833fa7de7f1766ee");
        PlatformConfig.setQQZone("1105635056", "nDBcY2GljdnWhhth");
    }

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

    }

    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, APP_MAIN_FOLDER_NAME, CRASH_FOLDER_NAME);
        CrashExceptionHandler.CrashExceptionRemoteReport remoteReport = new SimpleCrashReporter();
        crashExceptionHandler.configRemoteReport(remoteReport); //设置友盟统计报错日志回传到远程服务器上
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }

}
