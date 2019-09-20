package com.sxjs.jd.composition.nationexam;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.NewsItemListResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:09
 * @Description:
 */
public class NationExamPresenter extends BasePresenter implements NationExamContract.Presenter {
    private              MainDataManager         mDataManager;
    private              NationExamContract.View mLoginView;
    private static final String                  TAG = "MainPresenter";

    @Inject
    public NationExamPresenter(MainDataManager mDataManager, NationExamContract.View view) {
        this.mDataManager = mDataManager;
        this.mLoginView = view;

    }

    @Override
    public void destory() {
        if (disposables != null) {
            disposables.clear();
        }
    }

    @Override
    public void saveData() {

    }

    @Override
    public Map getData() {
        return null;
    }

    @Override
    public void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mLoginView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getNationExamNewsItemData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    NewsItemListResponse newsItemListResponse = gson.fromJson(response, NewsItemListResponse.class);

                    mLoginView.setResponseData(newsItemListResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mLoginView.hiddenProgressDialogView();
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mLoginView.hiddenProgressDialogView();
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                mLoginView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }
}
