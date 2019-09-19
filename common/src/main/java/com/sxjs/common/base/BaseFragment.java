package com.sxjs.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.sxjs.common.AppComponent;
import com.sxjs.common.GlobalAppComponent;
import com.sxjs.common.model.DataManager;
import com.sxjs.common.util.DialogUtil;

import butterknife.Unbinder;

/**
 * Created by admin on 2017/3/15.
 */

public class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Unbinder unbinder;
    protected Context  mContext;

    protected DataManager mDataManager;
    /**
     * gif_logo进度dialog
     */
    private   Dialog      dialog;

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
    }


//    /**
//     * Fragment当前状态是否可见
//     */
//    protected boolean isVisible;
//
//    //setUserVisibleHint  adapter中的每个fragment切换的时候都会被调用，如果是切换到当前页，那么isVisibleToUser==true，否则为false
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }
//
//
//    /**
//     * 可见
//     */
//    protected void onVisible() {
//        lazyLoad();
//    }
//
//
//    /**
//     * 不可见
//     */
//    protected void onInvisible() {
//
//
//    }
//
//    /**
//     * 延迟加载
//     * 子类必须重写此方法
//     */
//    protected void lazyLoad() {
//
//    }

}
