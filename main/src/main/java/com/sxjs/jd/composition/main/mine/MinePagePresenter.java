package com.sxjs.jd.composition.main.mine;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.UnReadMessageResponse;
import com.sxjs.jd.entities.UserInfoResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:53
 * @Description:
 */
public class MinePagePresenter extends BasePresenter implements MinePageContract.Presenter {

    private MainDataManager mDataManager;

    private MinePageContract.View mContractView;
    private static  final String  TAG = "BeforePagePresenter";
    @Inject
    public MinePagePresenter(MainDataManager mDataManager, MinePageContract.View view) {
        this.mDataManager = mDataManager;
        this.mContractView = view;

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
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getUserInfoData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    UserInfoResponse userInfoResponse = gson.fromJson(response, UserInfoResponse.class);

                    mContractView.setResponseData(userInfoResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mContractView.hiddenProgressDialogView();
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mContractView.hiddenProgressDialogView();
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

    @Override
    public void getUnreadMessageRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getNoReadMessageData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    UnReadMessageResponse unReadMessageResponse = gson.fromJson(response, UnReadMessageResponse.class);

                    mContractView.setUnreadMessageResponseData(unReadMessageResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mContractView.hiddenProgressDialogView();
            }


            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mContractView.hiddenProgressDialogView();
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

    @Override
    public void getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getLoginData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);

                    mContractView.setLoginData(loginResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mContractView.hiddenProgressDialogView();
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

}
