package com.sxjs.jd.composition.kpihome.schedule;

import com.sxjs.jd.entities.ExamScheduleResponse;
import com.sxjs.jd.entities.LoginResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface ExamScheduleContract {

    interface View {


        void setResponseData(ExamScheduleResponse examScheduleResponse);

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
