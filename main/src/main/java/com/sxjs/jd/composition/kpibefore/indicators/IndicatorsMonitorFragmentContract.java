package com.sxjs.jd.composition.kpibefore.indicators;

import com.sxjs.jd.entities.BeforeIndicatorsResponse;
import com.sxjs.jd.entities.ForgetPasswordResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface IndicatorsMonitorFragmentContract {

    interface View {


        void setResponseData(BeforeIndicatorsResponse beforeIndicatorsResponse);

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
