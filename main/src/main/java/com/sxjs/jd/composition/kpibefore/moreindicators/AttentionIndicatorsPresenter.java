package com.sxjs.jd.composition.kpibefore.moreindicators;

import com.google.gson.Gson;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.AttentionIndicatorsResponse;
import com.sxjs.jd.entities.JkxYuPingResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:09
 * @Description: ChangeAuthenticationPresenter
 */
public class AttentionIndicatorsPresenter extends BasePresenter implements AttentionIndicatorsContract.Presenter {
    private              MainDataManager                  mDataManager;
    private              AttentionIndicatorsContract.View mContractView;
    private static final String                           TAG = "ChangeAuthenticationPresenter";

    @Inject
    public AttentionIndicatorsPresenter(MainDataManager mDataManager, AttentionIndicatorsContract.View view) {
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
    public void getRequestNationalData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getMoreAttentionIndicatorsData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    AttentionIndicatorsResponse attentionIndicatorsResponse = gson.fromJson(response, AttentionIndicatorsResponse.class);

                    mContractView.setResponseNationalData(attentionIndicatorsResponse);
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
        Disposable disposable = mDataManager.getMoreAttentionIndicatorsData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    AttentionIndicatorsResponse attentionIndicatorsResponse = gson.fromJson(response, AttentionIndicatorsResponse.class);

                    mContractView.setMoreData(attentionIndicatorsResponse);
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
