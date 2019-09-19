package com.sxjs.jd.composition.message.notification;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
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
import com.sxjs.jd.composition.main.unused.findfragment.FindsAdapter;
import com.sxjs.jd.entities.FindsBean;
import com.sxjs.jd.entities.MessageNotificationResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description:
 */
public class NotificationFragment extends BaseFragment implements NotificationFragmentContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {


    @Inject
    NotificationFragmentPresenter mPresenter;

    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    @BindView(R2.id.find_recyclerview)
    RecyclerView findRecyclerview;
    private Handler mHandler;

    private static final String                      TAG = "MessageActivity";
    private              String                      mSession_id;
    private              MessageNotificationResponse messageNotificationResponse;
    private              Dialog                      dialog;
    private              NotificationAdapter                adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();

        initView();

        if (!mHasLoadedOnce && messageNotificationResponse == null) {
            Log.i("TestData", "FoundFragment 加载请求网络数据");
            //TO-DO 执行网络数据请求
            mHasLoadedOnce = true;

            initData();
        }


        return view;

    }

    private boolean mHasLoadedOnce = false;// 页面为false没有加载过 ,页面为true已经加载过

    //    @Override
    //    public void setUserVisibleHint(boolean isVisibleToUser) {
    //        super.setUserVisibleHint(isVisibleToUser);
    //        if (isVisibleToUser && !mHasLoadedOnce && messageNitificationResponse == null) {
    //            Log.i("TestData", "FoundFragment 加载请求网络数据");
    //            //TO-DO 执行网络数据请求
    //            mHasLoadedOnce = true;
    //            mHandler = new Handler();
    //            initData();
    //        }
    //    }

    public static NotificationFragment newInstance() {
        NotificationFragment quicklyFragment = new NotificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        quicklyFragment.setArguments(bundle);
        return quicklyFragment;
    }

    public void initView() {
        mHandler = new Handler();
        mSession_id = PrefUtils.readSESSION_ID(mContext);

        DaggerNotificationFragmentComponent.builder()
                .appComponent(getAppComponent())
                .notificationFragmentModule(new NotificationFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new NotificationAdapter(R.layout.item_notification_recyclerview);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(true);
        findRecyclerview.setAdapter(adapter);


    }

    public void initData() {



        showJDLoadingDialog();
        String session_id = PrefUtils.readSESSION_ID(mContext);

        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("MESSAGE_TYPE", "1");

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I001");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }


    @Override
    public void setResponseData(MessageNotificationResponse messageNotificationResponse) {

        this.messageNotificationResponse = messageNotificationResponse;
        try {
            String code = messageNotificationResponse.getCode();
            String msg = messageNotificationResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + messageNotificationResponse.getData());
                adapter.addData(messageNotificationResponse.getData());

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
    public void setMoreData(MessageNotificationResponse moreDate) {

        try {
            String code = moreDate.getCode();
            String msg = moreDate.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<MessageNotificationResponse.DataBean> data = moreDate.getData();
                for (int i = 0; i < data.size(); i++) {
                    adapter.getData().add(data.get(i));
                }

                adapter.loadMoreComplete();
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


    @Override
    public void onLoadMoreRequested() {

    }
}
