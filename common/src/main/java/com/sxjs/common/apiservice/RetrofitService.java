package com.sxjs.common.apiservice;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by $wu on 2017-08-03 下午 2:57.
 * Retrofit请求的接口
 */

public interface RetrofitService {


    //apk的下载
    @GET
    @Streaming
    Observable<ResponseBody> apk_download(@Url String url);

    //单文件上传头像（方式一）
    @Multipart
    @POST("cm/v1")
    Observable<ResponseBody> upload_avatar(
            @HeaderMap Map<String, String> headers,
            @Part MultipartBody.Part part,
            @Part("IMAGE_TYPE") RequestBody paramsBody);

    //单文件上传头像（方式二）
    @POST("api.php?con=user&act=updateavatar")
    Observable<ResponseBody> upload_avatar(@Body RequestBody body);

    //上传司机认证的消息
    @Multipart
    @POST("api.php?con=user&act=drvierCertify")
    Observable<ResponseBody> driver_auth(@Part List<MultipartBody.Part> parts, @PartMap Map<String, RequestBody> params);

    //上传司机认证的消息
    @POST("api.php?con=user&act=drvierCertify")
    Observable<ResponseBody> driver_auth(@Body RequestBody body);


}

