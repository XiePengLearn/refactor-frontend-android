package com.sxjs.jd.composition.main.home;

import com.sxjs.jd.entities.AppUpdateResponse;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.HomePageResponse;
import com.sxjs.jd.entities.PolicyElucidationResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;
import com.sxjs.jd.entities.UserResearchResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface HomePageContract {

    interface View {


        void setResponseData(HomePageResponse homePageResponse);
        void setResponseUpdateData(AppUpdateResponse appUpdateResponse);
        void setResponsePolicyElucidationData(PolicyElucidationResponse policyElucidationResponse);
        void setUserResearchData(UserResearchResponse userResearchResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getRequestUpdateData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getRequestPolicyElucidationData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getUserResearchData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

    }
}
