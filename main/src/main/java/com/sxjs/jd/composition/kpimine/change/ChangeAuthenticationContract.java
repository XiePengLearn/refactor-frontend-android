package com.sxjs.jd.composition.kpimine.change;

import com.sxjs.jd.entities.AuthenticationDataResponse;
import com.sxjs.jd.entities.UploadImageResponse;
import com.sxjs.jd.entities.UserAuthenticationResponse;
import com.sxjs.jd.entities.UserChangeAuthenticationResponse;

import java.io.File;
import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface ChangeAuthenticationContract {

    interface View {


        void setResponseData(UserChangeAuthenticationResponse userChangeAuthenticationResponse);
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
