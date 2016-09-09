package com.dream.plmm.netWork;

import com.dream.plmm.bean.ClassifyHealthy;
import com.dream.plmm.bean.HealthyInfoDetailEntity;
import com.dream.plmm.bean.HealthyInfoListEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by likun on 16/8/19.
 * 健康相关网络接口
 */
public interface HealthyService  {

    //获取健康知识分类
    @Headers("Cache-Control: public, max-age=36000")
    @GET("api/lore/classify")
    Call<ClassifyHealthy> getHealthyClassify();

    //获取健康知识列表
    @Headers("Cache-Control: public, max-age=36000")
    @GET("api/lore/list")
    Call<HealthyInfoListEntity> getHealthyInfoList(@Query("id") int id, @Query("rows") int rows);

    //获取健康知识详情
    @Headers("Cache-Control: public, max-age=36000")
    @GET("api/lore/show")
    Call<HealthyInfoDetailEntity> getHealthyDetail(@Query("id") int id);
}
