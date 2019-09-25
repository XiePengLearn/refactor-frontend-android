package com.sxjs.jd.composition.kpihome.previews;

import com.sxjs.jd.entities.ExamScheduleResponse;
import com.sxjs.jd.entities.PreviewsScheduleResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface PreviewsScheduleContract {

    interface View {


        void setResponseData(PreviewsScheduleResponse previewsScheduleResponse);

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
