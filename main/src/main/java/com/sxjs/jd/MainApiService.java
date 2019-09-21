package com.sxjs.jd;

import com.sxjs.common.model.http.BaseApiService;

import java.net.URL;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author：xiepeng on 2018/4/20 18:26.
 */

public interface MainApiService extends BaseApiService{

    @FormUrlEncoded
    @POST("userRegister/login")
    Observable<ResponseBody> login(@Field("mobile")String mobile,@Field("code")String code);


}
