package com.sxjs.jd.composition.nationexam;

import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.NewsItemListResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface NationExamContract {

    interface View {

        void setResponseData(NewsItemListResponse newsItemListResponse);

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
