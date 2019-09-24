package com.sxjs.jd.composition.kpimine.feedback;

import com.sxjs.jd.entities.FeedBackResponse;
import com.sxjs.jd.entities.LoginResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface FeedBackContract {

    interface View {


        void setResponseData(FeedBackResponse feedBackResponse);

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
