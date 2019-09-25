package com.sxjs.jd.composition.kpibefore.moreindicators;

import com.sxjs.jd.entities.AttentionIndicatorsResponse;
import com.sxjs.jd.entities.JkxYuPingResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface AttentionIndicatorsContract {

    interface View {


        void setResponseNationalData(AttentionIndicatorsResponse attentionIndicatorsResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();

        void setMoreData(AttentionIndicatorsResponse attentionIndicatorsResponse);
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestNationalData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
