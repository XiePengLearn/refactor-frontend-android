package com.sxjs.jd.composition.main;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.LoginResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2017/03/12
 */

public class MainPresenter extends BasePresenter implements MainContract.Presenter {
    private MainDataManager mDataManager;

    private              MainContract.View mMainView;
    private static final String            TAG = "MainPresenter";

    @Inject
    public MainPresenter(MainDataManager mDataManager, MainContract.View view) {
        this.mDataManager = mDataManager;
        this.mMainView = view;

    }


    //    @Override
    //    public void getText() {
    //        mMainView.showProgressDialogView();
    //        Disposable disposable = mDataManager.getMainData(0, 10, new ErrorDisposableObserver<ResponseBody>() {
    //            @Override
    //            public void onNext(ResponseBody responseBody) {
    //                try {
    //                    mMainView.setText(responseBody.string());
    //                } catch (IOException e) {
    //                    e.printStackTrace();
    //                }
    //            }
    //            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
    //            @Override
    //            public void onError(Throwable e) {
    //                super.onError(e);
    //                mMainView.hiddenProgressDialogView();
    //            }
    //
    //            @Override
    //            public void onComplete() {
    //                Log.e(TAG, "onComplete: " );
    //                mMainView.hiddenProgressDialogView();
    //            }
    //        });
    //        addDisposabe(disposable);
    //
    //    }

    @Override
    public void destory() {
        if (disposables != null) {
            disposables.clear();
        }
    }

    @Override
    public void saveData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "xiaoming");
        map.put("password", "123456");
        mDataManager.saveSPMapData(map);
    }

    @Override
    public Map<String, String> getData() {
        return mDataManager.getSPMapData();
    }

    @Override
    public void getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mMainView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getLoginData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);

                    mMainView.setLoginData(loginResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mMainView.hiddenProgressDialogView();
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                mMainView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

}