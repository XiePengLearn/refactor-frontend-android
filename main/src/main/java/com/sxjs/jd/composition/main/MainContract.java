package com.sxjs.jd.composition.main;

import com.sxjs.jd.entities.LoginResponse;

import java.util.Map;

/**
 * Created by admin on 2017/3/12.
 */

public interface MainContract {
    interface View {
        void setLoginData(LoginResponse loginResponse);


        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {

        void destory();

        void saveData();

        Map getData();
        void getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

    }

}