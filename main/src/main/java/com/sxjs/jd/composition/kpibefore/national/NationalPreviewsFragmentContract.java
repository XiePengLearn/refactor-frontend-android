package com.sxjs.jd.composition.kpibefore.national;

import com.sxjs.jd.entities.MedicalQualityResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface NationalPreviewsFragmentContract {

    interface View {


        void setResponseData(MedicalQualityResponse medicalQualityResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();

        void setMoreData(MedicalQualityResponse medicalQualityResponse);
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
