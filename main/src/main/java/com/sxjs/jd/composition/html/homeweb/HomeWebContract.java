package com.sxjs.jd.composition.html.homeweb;

import com.sxjs.jd.entities.LoginResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface HomeWebContract {

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
