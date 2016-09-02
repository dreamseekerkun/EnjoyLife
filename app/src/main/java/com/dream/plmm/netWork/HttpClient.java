package com.dream.plmm.netWork;


import com.dream.plmm.BasicApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by likun on 16/4/19.
 */
public class HttpClient {
    private static HttpClient mInstance;
    private Retrofit retrofit;

    public static HttpClient getIns(String base_url) {
        if (mInstance == null) {
            synchronized (HttpClient.class) {
                if (mInstance == null) mInstance = new HttpClient(base_url);
            }
        }
        return mInstance;
    }


    public HttpClient(String BASE_URL) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//log 日志打印

        try {
            File cacheFile = new File(BasicApplication.getContext().getCacheDir(), "android");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(7676, TimeUnit.MILLISECONDS)
                    .connectTimeout(7676, TimeUnit.MILLISECONDS)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new HttpCacheInterceptor())
                    .cache(cache)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }
}
