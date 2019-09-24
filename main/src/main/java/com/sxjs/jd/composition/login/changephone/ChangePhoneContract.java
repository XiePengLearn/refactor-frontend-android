package com.sxjs.jd.composition.login.changephone;

import com.sxjs.jd.entities.ChangePhoneResponse;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:34
 * @Description:
 */
public interface ChangePhoneContract {
    interface View {


        void setResponseData(ChangePhoneResponse changePhoneResponse);
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
