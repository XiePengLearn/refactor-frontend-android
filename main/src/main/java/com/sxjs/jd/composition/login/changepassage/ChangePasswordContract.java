package com.sxjs.jd.composition.login.changepassage;

import com.sxjs.jd.entities.ChangePasswordResponse;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:34
 * @Description:
 */
public interface ChangePasswordContract {
    interface View {


        void setResponseData(ChangePasswordResponse changePasswordResponse);

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
