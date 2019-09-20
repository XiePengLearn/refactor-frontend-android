package com.sxjs.jd.composition.nationexam.DetailsPage;

import com.sxjs.jd.entities.MessageNotificationResponse;
import com.sxjs.jd.entities.NewsListResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface NewsItemDetailsFragmentContract {

    interface View {


        void setResponseData(NewsListResponse newsListResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
        void setMoreData(NewsListResponse newsListResponse);
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
