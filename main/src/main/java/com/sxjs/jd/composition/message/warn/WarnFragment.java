package com.sxjs.jd.composition.message.warn;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
public class WarnFragment extends BaseFragment implements WarnFragmentContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {


    @Inject
    WarnFragmentPresenter mPresenter;

    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    @BindView(R2.id.find_recyclerview)
    RecyclerView findRecyclerview;

    @BindView(R2.id.rl_no_data)
    RelativeLayout rlNoData;
    private Handler mHandler;

    private static final String                      TAG = "NationExamActivity";
    private              MessageNotificationResponse messageNotificationResponse;
    private              Dialog                      dialog;
    private              WarnAdapter                 adapter;
    private              View                        mView;

    //    @Nullable
    //    @Override
    //    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //        if (null == mView) {
    //            mView = inflater.inflate(R.layout.fragment_warn, container, false);
    //            unbinder = ButterKnife.bind(this, mView);
    //            Bundle arguments = getArguments();
    //
    //            initView();
    //            initData();
    //        }else {
    //            unbinder = ButterKnife.bind(this, mView);
    //        }
    //
    //        return mView;
    //
    //    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_warn, container, false);
        unbinder = ButterKnife.bind(this, mView);
        Bundle arguments = getArguments();


        return mView;
    }

    @Override
    public void initEvent() {


    }

    @Override
    public void onLazyLoad() {
        initView();
        initData();
    }


    public static WarnFragment newInstance() {
        WarnFragment quicklyFragment = new WarnFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        quicklyFragment.setArguments(bundle);
        return quicklyFragment;
    }

    public void initView() {
        mHandler = new Handler();
        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerWarnFragmentComponent.builder()
                .appComponent(getAppComponent())
                .warnFragmentModule(new WarnFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new WarnAdapter(R.layout.item_notification_recyclerview);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(false);
        adapter.loadMoreComplete();
        findRecyclerview.setAdapter(adapter);


    }

    public void initData() {


        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("MESSAGE_TYPE", "2");

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

                List<MessageNotificationResponse.DataBean> messageDate = messageNotificationResponse.getData();
                if(messageDate != null && messageDate.size()>0){

                    List<MessageNotificationResponse.DataBean> data = adapter.getData();
                    data.clear();
                    adapter.addData(messageDate);
                    rlNoData.setVisibility(View.GONE);
                }else {
                    rlNoData.setVisibility(View.VISIBLE);
                }
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
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
                adapter.loadMoreComplete();
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();
            } else {
                adapter.loadMoreComplete();
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            adapter.loadMoreComplete();
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

                Map<String, Object> mapParameters = new HashMap<>(1);
                mapParameters.put("MESSAGE_TYPE", "2");

                Map<String, String> mapHeaders = new HashMap<>(2);
                mapHeaders.put("ACTION", "I001");
                mapHeaders.put("SESSION_ID", session_id);

                mPresenter.getRequestData(mapHeaders, mapParameters);
                frame.refreshComplete();
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void onLoadMoreRequested() {


        findRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.loadMoreComplete();
                //                String session_id = PrefUtils.readSESSION_ID(mContext);
                //
                //                Map<String, Object> mapParameters = new HashMap<>(1);
                //                mapParameters.put("MESSAGE_TYPE", "2");
                //
                //                Map<String, String> mapHeaders = new HashMap<>(2);
                //                mapHeaders.put("ACTION", "I001");
                //                mapHeaders.put("SESSION_ID", session_id);
                //
                //                mPresenter.getMoreFindData(mapHeaders, mapParameters);
            }
        }, 500);
    }
}
