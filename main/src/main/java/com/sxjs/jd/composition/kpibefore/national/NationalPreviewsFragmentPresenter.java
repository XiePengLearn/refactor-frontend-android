package com.sxjs.jd.composition.kpibefore.national;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.JkxYuPingResponse;
import com.sxjs.jd.entities.JkxYuPingStatusResponse;
import com.sxjs.jd.entities.MedicalQualityResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:53
 * @Description:
 */
public class NationalPreviewsFragmentPresenter extends BasePresenter implements NationalPreviewsFragmentContract.Presenter {

    private MainDataManager mDataManager;

    private              NationalPreviewsFragmentContract.View mContractView;
    private static final String                                TAG = "NationalPreviewsFragmentPresenter";

    @Inject
    public NationalPreviewsFragmentPresenter(MainDataManager mDataManager, NationalPreviewsFragmentContract.View view) {
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
    public void getRequestStatusData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getNationalStatusData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    JkxYuPingStatusResponse jkxYuPingStatusResponse = gson.fromJson(response, JkxYuPingStatusResponse.class);

                    mContractView.setResponseStatusData(jkxYuPingStatusResponse);
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
    public void getRequestNationalData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getNationalData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    JkxYuPingResponse jkxYuPingResponse = gson.fromJson(response, JkxYuPingResponse.class);

                    mContractView.setResponseNationalData(jkxYuPingResponse);
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
    public void getMoreFindData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getBeforeMediacalData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    JkxYuPingResponse jkxYuPingResponse = gson.fromJson(response, JkxYuPingResponse.class);

                    mContractView.setMoreData(jkxYuPingResponse);
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