package com.sxjs.jd.composition.kpihome.abnormal;

import com.sxjs.jd.entities.JkxYuPingResponse;
import com.sxjs.jd.entities.LoginResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface AbnormalContract {

    interface View {


        void setResponseNationalData(JkxYuPingResponse jkxYuPingResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
        void setMoreData(JkxYuPingResponse jkxYuPingResponse);
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestNationalData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
