package com.sxjs.jd.composition.kpimine.authentication;

import com.sxjs.jd.entities.AuthenticationDataResponse;
import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.UploadImageResponse;
import com.sxjs.jd.entities.UserAuthenticationResponse;

import java.io.File;
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
        void setUploadImage(UploadImageResponse uploadImageResponse);
        void setCommitAuthenticationResponseData(AuthenticationDataResponse authenticationDataResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getUploadImage(Map<String, String> headers, File file_name);
        void getCommitAuthenticationToServerData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
