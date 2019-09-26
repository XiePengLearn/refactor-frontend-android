package com.sxjs.jd.composition.kpihome.indexranking;

import com.sxjs.jd.entities.IndexRankingResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface IndexRankingContract {

    interface View {


        void setResponseNationalData(IndexRankingResponse indexRankingResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
        void setMoreData(IndexRankingResponse indexRankingResponse);
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestNationalData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
