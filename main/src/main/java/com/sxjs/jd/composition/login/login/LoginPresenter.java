package com.sxjs.jd.composition.login.login;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.LoginResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:09
 * @Description:
 */
public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
    private              MainDataManager    mDataManager;
    private              LoginContract.View mLoginView;
    private static final String             TAG = "MainPresenter";

    @Inject
    public LoginPresenter(MainDataManager mDataManager, LoginContract.View view) {
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
    public void getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mLoginView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getLoginData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);

                    mLoginView.setLoginData(loginResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
