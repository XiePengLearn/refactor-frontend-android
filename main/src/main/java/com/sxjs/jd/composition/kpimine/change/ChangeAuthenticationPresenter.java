package com.sxjs.jd.composition.kpimine.change;

import com.google.gson.Gson;
import com.sxjs.common.apiservice.RetrofitService;
import com.sxjs.common.apiservice.RetrofitServiceUtil;
import com.sxjs.common.base.rxjava.ErrorDisposableObserver;
import com.sxjs.common.util.LogUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.BasePresenter;
import com.sxjs.jd.entities.AuthenticationDataResponse;
import com.sxjs.jd.entities.UploadImageResponse;
import com.sxjs.jd.entities.UserAuthenticationResponse;
import com.sxjs.jd.entities.UserChangeAuthenticationResponse;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:09
 * @Description: ChangeAuthenticationPresenter
 */
public class ChangeAuthenticationPresenter extends BasePresenter implements ChangeAuthenticationContract.Presenter {
    private              MainDataManager                   mDataManager;
    private              ChangeAuthenticationContract.View mContractView;
    private static final String                            TAG = "ChangeAuthenticationPresenter";

    @Inject
    public ChangeAuthenticationPresenter(MainDataManager mDataManager, ChangeAuthenticationContract.View view) {
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
        Disposable disposable = mDataManager.getChangeUserAuthenticationData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    UserChangeAuthenticationResponse userChangeAuthenticationResponse = gson.fromJson(response, UserChangeAuthenticationResponse.class);

                    mContractView.setResponseData(userChangeAuthenticationResponse);
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
    public void getUploadImage(Map<String, String> mapHeaders, File file_name) {


        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        RetrofitService retrofitService = RetrofitServiceUtil.getRetrofitService();
        RequestBody params = RequestBody.create(MediaType.parse("text/plain"), "png");
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file_name);
        MultipartBody.Part part = MultipartBody.Part.createFormData("IMAGE", file_name.getName(), requestBody);
        retrofitService.upload_avatar(mapHeaders, part, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String response = responseBody.string();
                            LogUtil.e(TAG, "=======response:=======" + response);
                            Gson gson = new Gson();
                            UploadImageResponse uploadImageResponse = gson.fromJson(response, UploadImageResponse.class);

                            mContractView.setUploadImage(uploadImageResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mContractView.hiddenProgressDialogView();
                    }

                    @Override
                    public void onComplete() {
                        long completeRequestTime = System.currentTimeMillis();
                        long useTime = completeRequestTime - beforeRequestTime;
                        LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");

                    }
                });
    }

    @Override
    public void getCommitAuthenticationToServerData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getUploadAuthenticationData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    AuthenticationDataResponse authenticationDataResponse = gson.fromJson(response, AuthenticationDataResponse.class);

                    mContractView.setCommitAuthenticationResponseData(authenticationDataResponse);
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
