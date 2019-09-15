package com.sxjs.jd.composition.login.forget;

import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;
import com.sxjs.jd.entities.RegisterResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:34
 * @Description:
 */
public interface ForgetPasswordContract {
    interface View {


        void setResponseData(ForgetPasswordResponse registerResponse);
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
