package com.sxjs.jd.composition.message.notification;

import com.sxjs.jd.entities.ForgetPasswordResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface NotificationFragmentContract {

    interface View {


        void setResponseData(ForgetPasswordResponse registerResponse);

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
