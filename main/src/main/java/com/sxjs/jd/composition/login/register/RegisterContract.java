package com.sxjs.jd.composition.login.register;

import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;
import com.sxjs.jd.entities.RegisterResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/14 23:30
 * @Description:
 */
public interface RegisterContract {
    interface View {


        void setResponseData(RegisterResponse registerResponse);
        void setCodeResponseData(RegisterCodeResponse registerResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getCodeRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
