package com.sxjs.jd.composition.message.warn;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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
import com.sxjs.jd.composition.main.unused.quicklyfragment.DaggerQuicklyFragmentComponent;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.MessageWarnResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description:
 */
public class WarnFragment extends BaseFragment implements WarnFragmentContract.View, PtrHandler {


    @Inject
    WarnFragmentPresenter mPresenter;

    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    private Handler mHandler;

    private static final String              TAG = "MessageActivity";
    private              String              mSession_id;
    private              MessageWarnResponse messageWarnResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warn, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();

        initView();
        if ( !mHasLoadedOnce && messageWarnResponse == null) {
            Log.i("TestData", "FoundFragment 加载请求网络数据");
            //TO-DO 执行网络数据请求
            mHasLoadedOnce = true;
            mHandler = new Handler();
            initData();
        }

        return view;

    }

    private boolean mHasLoadedOnce = false;// 页面为false没有加载过 ,页面为true已经加载过

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && !mHasLoadedOnce && messageWarnResponse == null) {
//            Log.i("TestData", "FoundFragment 加载请求网络数据");
//            //TO-DO 执行网络数据请求
//            mHasLoadedOnce = true;
//
//            initData();
//        }
//    }

    public static WarnFragment newInstance() {
        WarnFragment quicklyFragment = new WarnFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        quicklyFragment.setArguments(bundle);
        return quicklyFragment;
    }

    public void initView() {
        mHandler = new Handler();
        mSession_id = PrefUtils.readSESSION_ID(mContext);

        DaggerWarnFragmentComponent.builder()
                .appComponent(getAppComponent())
                .warnFragmentModule(new WarnFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        //                findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        //                adapter = new FindsAdapter(R.layout.item_finds_recyclerview);
        //                adapter.setOnLoadMoreListener(this);
        //                adapter.setEnableLoadMore(true);
        //                findRecyclerview.setAdapter(adapter);


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
    public void setResponseData(MessageWarnResponse messageWarnResponse) {
        this.messageWarnResponse = messageWarnResponse;
        try {
            String code = messageWarnResponse.getCode();
            String msg = messageWarnResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + messageWarnResponse.getData());


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
