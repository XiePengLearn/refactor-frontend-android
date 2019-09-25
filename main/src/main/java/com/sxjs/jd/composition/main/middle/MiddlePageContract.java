package com.sxjs.jd.composition.main.middle;

import com.sxjs.jd.entities.ExamMiddleKpiReportResponse;
import com.sxjs.jd.entities.ExamMiddleResponse;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.UnReadMessageResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface MiddlePageContract {

    interface View {


        void setResponseData(UnReadMessageResponse unReadMessageResponse);

        void setExamMiddleData(ExamMiddleResponse unReadMessageResponse);

        void setExamMiddleKpiReportData(ExamMiddleKpiReportResponse examMiddleKpiReportResponse);

        void setLoginData(LoginResponse loginResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getExamMiddleData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getExamMiddleKpiReportData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

    }
}
