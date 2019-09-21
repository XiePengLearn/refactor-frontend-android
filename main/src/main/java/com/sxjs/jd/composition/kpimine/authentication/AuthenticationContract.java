package com.sxjs.jd.composition.kpimine.authentication;

import com.sxjs.jd.entities.AuthenticationDataResponse;
import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.UploadImageResponse;
import com.sxjs.jd.entities.UserAuthenticationResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface AuthenticationContract {

    interface View {


        void setResponseData(UserAuthenticationResponse userAuthenticationResponse);
        void setUploadImageToOssData(UploadImageResponse uploadImageResponse);
        void setUploadImage(UploadImageResponse uploadImageResponse);
        void setAuthenticationData(AuthenticationDataResponse authenticationDataResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getUploadImageToOssData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getUploadImage(Map<String, String> headers, List<MultipartBody.Part> partList);
        void getAuthenticationData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
