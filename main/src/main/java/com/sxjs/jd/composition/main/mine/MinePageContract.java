package com.sxjs.jd.composition.main.mine;

import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.UserInfoResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public interface MinePageContract {

    interface View {


        void setResponseData(UserInfoResponse userInfoResponse);

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
