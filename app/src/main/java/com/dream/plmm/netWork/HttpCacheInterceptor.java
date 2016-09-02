package com.dream.plmm.netWork;

import android.util.Log;

import com.dream.plmm.BasicApplication;
import com.dream.plmm.utils.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by likun on 16/4/19.
 * 设置缓存的拦截器
 */
public class HttpCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetWorkUtil.isNetConnected(BasicApplication.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.d("Okhttp", "no network");
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtil.isNetConnected(BasicApplication.getContext())) {
            Log.e("network ", "wifi ok");
            //有网的时候读接口上的@Headers里的配置，也可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            Log.e("network ", "wifi no");
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
