package com.sxjs.jd.composition.login.forget;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:51
 * @Description:
 */
public class ForgetPasswordPresenter extends BasePresenter implements ForgetPasswordContract.Presenter {

    private              MainDataManager             mDataManager;
    private              ForgetPasswordContract.View mLoginView;
    private static final String                      TAG = "ForgetPasswordPresenter";

    @Inject
    public ForgetPasswordPresenter(MainDataManager mDataManager, ForgetPasswordContract.View view) {
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
        Disposable disposable = mDataManager.getForgetPasswordData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    ForgetPasswordResponse forgetPasswordResponse = gson.fromJson(response, ForgetPasswordResponse.class);

                    mLoginView.setResponseData(forgetPasswordResponse);
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

    @Override
    public void getCodeRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mLoginView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getRegisretCodeData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    RegisterCodeResponse registerCodeResponse = gson.fromJson(response, RegisterCodeResponse.class);

                    mLoginView.setCodeResponseData(registerCodeResponse);
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
