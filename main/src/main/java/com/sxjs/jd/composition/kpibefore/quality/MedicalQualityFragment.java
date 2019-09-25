package com.sxjs.jd.composition.kpibefore.quality;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.sxjs.jd.entities.MedicalQualityResponse;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description:
 */
public class MedicalQualityFragment extends BaseFragment implements MedicalQualityFragmentContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {


    @Inject
    MedicalQualityFragmentPresenter mPresenter;
    @BindView(R2.id.tv_text1)
    TextView                        tvText1;
    @BindView(R2.id.tv_date)
    TextView                        tvDate;
    @BindView(R2.id.tv_more)
    TextView                        tvMore;
    @BindView(R2.id.rl_top_data)
    RelativeLayout                  rlTopData;
    @BindView(R2.id.medical_recycler_view)
    RecyclerView                    findRecyclerview;
    @BindView(R2.id.medical_pull_refresh_header)
    JDHeaderView                    findPullRefreshHeader;
    @BindView(R2.id.no_data_img)
    ImageView                       noDataImg;
    @BindView(R2.id.rl_no_data)
    RelativeLayout                  rlNoData;
    Unbinder unbinder;

    private Handler mHandler;

    private static final String                 TAG = "NationExamActivity";
    private              Dialog                 dialog;
    private              MedicalQualityAdapter  adapter;
    private              View                   mView;
    private              MedicalQualityResponse medicalQualityResponse;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_medical_quality, container, false);
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


    public static MedicalQualityFragment newInstance() {
        MedicalQualityFragment quicklyFragment = new MedicalQualityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        quicklyFragment.setArguments(bundle);
        return quicklyFragment;
    }

    public void initView() {
        mHandler = new Handler();
        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerMedicalQualityFragmentComponent.builder()
                .appComponent(getAppComponent())
                .medicalQualityFragmentModule(new MedicalQualityFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new MedicalQualityAdapter(R.layout.item_medical_recyclerview);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(false);
        adapter.loadMoreComplete();
        findRecyclerview.setAdapter(adapter);


    }

    public void initData() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String m = month + 1 + "";
        if (month + 1 < 10) {
            m = "0" + (month + 1);
        }
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("MONTHLY", year + m);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "Y003");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void setResponseData(MedicalQualityResponse medicalQualityResponse) {

        this.medicalQualityResponse = medicalQualityResponse;
        try {
            String code = medicalQualityResponse.getCode();
            String msg = medicalQualityResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                MedicalQualityResponse.DataBean messageDate = medicalQualityResponse.getData();
                if (messageDate != null) {
                    String data_end_date = messageDate.getDATA_END_DATE();
                    if (!TextUtils.isEmpty(data_end_date)) {
                        tvDate.setText("截至" + data_end_date);
                    } else {
                        tvDate.setText("");
                    }
                    List<MedicalQualityResponse.DataBean.CLASSIFYBean> classify = messageDate.getCLASSIFY();
                    if (classify != null) {
                        if (classify.size() > 0) {
                            List<MedicalQualityResponse.DataBean.CLASSIFYBean> data = adapter.getData();
                            data.clear();
                            adapter.addData(classify);
                            rlNoData.setVisibility(View.GONE);
                        } else {
                            rlNoData.setVisibility(View.VISIBLE);
                        }

                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                    }

                } else {
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
    public void setMoreData(MedicalQualityResponse moreDate) {

        try {
            String code = moreDate.getCode();
            String msg = moreDate.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                MedicalQualityResponse.DataBean data = moreDate.getData();
                if (data != null) {
                    List<MedicalQualityResponse.DataBean.CLASSIFYBean> classify = data.getCLASSIFY();
                    for (int i = 0; i < classify.size(); i++) {
                        adapter.getData().add(classify.get(i));
                    }
                }


                adapter.loadMoreComplete();
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
                initData();
                frame.refreshComplete();
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

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
                //                mapParameters.put("MESSAGE_TYPE", "1");
                //
                //                Map<String, String> mapHeaders = new HashMap<>(2);
                //                mapHeaders.put("ACTION", "I001");
                //                mapHeaders.put("SESSION_ID", session_id);
                //
                //                mPresenter.getMoreFindData(mapHeaders, mapParameters);
            }
        }, 500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R2.id.tv_text1, R2.id.tv_more})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_text1) {

        } else if (i == R.id.tv_more) {


        }
    }
}
