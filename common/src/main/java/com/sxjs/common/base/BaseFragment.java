package com.sxjs.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sxjs.common.AppComponent;
import com.sxjs.common.GlobalAppComponent;
import com.sxjs.common.model.DataManager;
import com.sxjs.common.util.DialogUtil;

import butterknife.Unbinder;

/**
 * Created by admin on 2017/3/15.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Unbinder unbinder;
    protected Context  mContext;

    protected DataManager mDataManager;
    /**
     * gif_logo进度dialog
     */
    private   Dialog      dialog;

    private boolean isFirstLoad = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = getAppComponent().getContext();
        mDataManager = getAppComponent().getDataManager();
    }

    protected void showShortToast(String message) {
        Toast.makeText(mActivity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String message) {
        Toast.makeText(mActivity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected AppComponent getAppComponent() {
        return GlobalAppComponent.getAppComponent();
    }

    protected void showJDLoadingDialog() {
        if (dialog == null)
            dialog = DialogUtil.createJDLoadingDialog(mActivity, null);
        if (dialog != null &&!dialog.isShowing()) {
            dialog.show();
        }
    }

    protected void hideJDLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
        isFirstLoad = false;//视图销毁将变量置为false
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = initView(inflater, container,savedInstanceState);//让子类实现初始化视图

        initEvent();//初始化事件

        isFirstLoad = true;//视图创建完成，将变量置为true

        if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
            onLazyLoad();
            isFirstLoad = false;
        }
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {//视图变为可见并且是第一次加载
            onLazyLoad();
            isFirstLoad = false;
        }

    }
    //数据加载接口，留给子类实现
    public abstract void onLazyLoad();

    //初始化视图接口，子类必须实现
    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState);

    //初始化事件接口，留给子类实现
    public abstract void initEvent();


}
