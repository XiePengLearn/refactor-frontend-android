package com.sxjs.jd.composition.main.home;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.AppUpdateResponse;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.HomePageResponse;
import com.sxjs.jd.entities.PolicyElucidationResponse;
import com.sxjs.jd.entities.UserResearchResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:53
 * @Description:
 */
public class HomePagePresenter extends BasePresenter implements HomePageContract.Presenter {

    private MainDataManager mDataManager;

    private HomePageContract.View mContractView;
    private static  final String  TAG = "MiddlePagePresenter";
    @Inject
    public HomePagePresenter(MainDataManager mDataManager, HomePageContract.View view) {
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
        Disposable disposable = mDataManager.getHomePageData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    HomePageResponse homePageResponse = gson.fromJson(response, HomePageResponse.class);

                    mContractView.setResponseData(homePageResponse);
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
    public void getRequestUpdateData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getAppUpdateData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    AppUpdateResponse appUpdateResponse = gson.fromJson(response, AppUpdateResponse.class);

                    mContractView.setResponseUpdateData(appUpdateResponse);
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
    public void getRequestPolicyElucidationData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getPolicyElucidationData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    PolicyElucidationResponse policyElucidationResponse = gson.fromJson(response, PolicyElucidationResponse.class);

                    mContractView.setResponsePolicyElucidationData(policyElucidationResponse);
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
    public void getUserResearchData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getUserResearchData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    UserResearchResponse userResearchResponse = gson.fromJson(response, UserResearchResponse.class);

                    mContractView.setUserResearchData(userResearchResponse);
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

}
