package com.sxjs.jd.composition.main.befor;

import com.sxjs.jd.entities.ExamMiddleKpiReportResponse;
import com.sxjs.jd.entities.ExamMiddleResponse;
import com.sxjs.jd.entities.UnReadMessageResponse;
import com.sxjs.jd.entities.UserInfoResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface BeforePageContract {

    interface View {


        void setResponseData(UnReadMessageResponse unReadMessageResponse);

        void setUserStatusData(UserInfoResponse userInfoResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getUserStatusData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
