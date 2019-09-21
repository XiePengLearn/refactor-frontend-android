package com.sxjs.jd.composition.kpimine.authentication;

import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.UserAuthenticationResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface AuthenticationContract {

    interface View {


        void setResponseData(UserAuthenticationResponse userAuthenticationResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
