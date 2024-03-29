package com.sxjs.jd.composition.message.warn;

import com.sxjs.jd.entities.MessageNotificationResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface WarnFragmentContract {

    interface View {


        void setResponseData(MessageNotificationResponse messageNotificationResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
        void setMoreData(MessageNotificationResponse messageNotificationResponse);
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
