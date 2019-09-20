package com.sxjs.jd.composition.main.unused.quicklyfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.ForgetPasswordResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description:
 */
public class QuicklyFragment extends BaseFragment implements QuicklyFragmentContract.View, PtrHandler {


    @Inject
    QuicklyFragmentPresenter mPresenter;

    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    private Handler mHandler;

    private static final String TAG = "MessageActivity";
    private              String mSession_id;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_quickly, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        Bundle arguments = getArguments();
//
//        initView();
//        initData();
//
//        return view;
//
//    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quickly, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {

        Bundle arguments = getArguments();

        initView();
        initData();

    }
    @Override
    public void onLazyLoad() {

    }
    public static QuicklyFragment newInstance() {
        QuicklyFragment quicklyFragment = new QuicklyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        quicklyFragment.setArguments(bundle);
        return quicklyFragment;
    }

    public void initView() {

        mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerQuicklyFragmentComponent.builder()
                .appComponent(getAppComponent())
                .quicklyFragmentModule(new QuicklyFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        //                findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        //                adapter = new FindsAdapter(R.layout.item_finds_recyclerview);
        //                adapter.setOnLoadMoreListener(this);
        //                adapter.setEnableLoadMore(true);
        //                findRecyclerview.setAdapter(adapter);

        mHandler = new Handler();

    }

    public void initData() {
        showJDLoadingDialog();
        //                mPresenter.getFindData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                hideJDLoadingDialog();
            }
        }, 2000);
    }


    @Override
    public void setResponseData(ForgetPasswordResponse loginResponse) {
        try {
            String code = loginResponse.getCode();
            String msg = loginResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + loginResponse.getData());


                //                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
                //                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext, msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext, "解析数据失败");
        }
    }

    @Override
    public void showProgressDialogView() {
        showJDLoadingDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hideJDLoadingDialog();
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
