package com.dream.plmm.netWork;

import com.dream.plmm.bean.ClassifyMM;
import com.dream.plmm.bean.MMDetail;
import com.dream.plmm.bean.TypeMM;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by likun on 16/8/17.
 * 图片网络接口
 */
public interface MMService  {

    //获取美女类别
    @Headers("Cache-Control: public, max-age=36000")
    @GET("tnfs/api/classify")
    Call<ClassifyMM> getClassifyMM();

    //获取具体分类美女列表
    @Headers("Cache-Control: public, max-age=36000")
    @GET("tnfs/api/list")
    Call<TypeMM> getTypeMM(@Query("id") int id, @Query("page") int page, @Query("rows") int rows);

    //获取美女详情
    @Headers("Cache-Control: public, max-age=36000")
    @GET("tnfs/api/show")
    Call<MMDetail> getMMDetail(@Query("id") int id);

}
