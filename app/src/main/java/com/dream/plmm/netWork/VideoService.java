package com.dream.plmm.netWork;

import com.dream.plmm.bean.ResponseVideosListEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by likun on 16/9/5.
 */
public interface VideoService {

    //获取视频信息
    @Headers("Cache-Control: public, max-age=3600")
    @GET("searches/video/by_keyword.json")
    Call<ResponseVideosListEntity> getVideoInfos(@QueryMap Map<String,String> map);
}
