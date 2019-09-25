package com.sxjs.jd.composition.kpibefore.national;

import com.sxjs.jd.entities.JkxYuPingResponse;
import com.sxjs.jd.entities.JkxYuPingStatusResponse;
import com.sxjs.jd.entities.MedicalQualityResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface NationalPreviewsFragmentContract {

    interface View {


        void setResponseStatusData(JkxYuPingStatusResponse jkxYuPingStatusResponse);
        void setResponseNationalData(JkxYuPingResponse jkxYuPingResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();

        void setMoreData(JkxYuPingResponse jkxYuPingResponse);
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestStatusData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getRequestNationalData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
