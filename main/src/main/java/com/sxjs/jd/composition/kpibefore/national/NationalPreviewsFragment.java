package com.sxjs.jd.composition.kpibefore.national;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.NoDoubleClickUtils;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.kpihome.previews.PreviewsScheduleActivity;
import com.sxjs.jd.composition.kpihome.schedule.ExamScheduleActivity;
import com.sxjs.jd.entities.JkxYuPingResponse;
import com.sxjs.jd.entities.JkxYuPingStatusResponse;
import com.sxjs.jd.entities.MedicalQualityResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class NationalPreviewsFragment extends BaseFragment implements NationalPreviewsFragmentContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {


    @Inject
    NationalPreviewsFragmentPresenter mPresenter;

    Unbinder unbinder;
    @BindView(R2.id.national_recycler_view)
    RecyclerView   findRecyclerview;
    @BindView(R2.id.national_pull_refresh_header)
    JDHeaderView   findPullRefreshHeader;
    @BindView(R2.id.no_data_img)
    ImageView      noDataImg;
    @BindView(R2.id.rl_no_data)
    RelativeLayout rlNoData;

    private Handler mHandler;

    private static final String                  TAG = "NationExamActivity";
    private              Dialog                  dialog;
    private              NationalPreviewsAdapter adapter;
    private              View                    mView;
    private              JkxYuPingStatusResponse jkxYuPingStatusResponse;
    private              JkxYuPingResponse       jkxYuPingResponse;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_national_previews, container, false);
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
        initStatusData();
        initNationalData();
    }


    public static NationalPreviewsFragment newInstance() {
        NationalPreviewsFragment quicklyFragment = new NationalPreviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        quicklyFragment.setArguments(bundle);
        return quicklyFragment;
    }

    public void initView() {
        mHandler = new Handler();
        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerNationalPreviewsFragmentComponent.builder()
                .appComponent(getAppComponent())
                .nationalPreviewsFragmentModule(new NationalPreviewsFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new NationalPreviewsAdapter(R.layout.item_national_recyclerview);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(false);
        adapter.loadMoreComplete();
        findRecyclerview.setAdapter(adapter);


    }

    public void initStatusData() {

        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("MONTHLY", year + m);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "Y004");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestStatusData(mapHeaders, mapParameters);
    }

    public void initNationalData() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String m = month + 1 + "";
        if (month + 1 < 10) {
            m = "0" + (month + 1);
        }
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("MONTHLY", year + m);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "H004");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestNationalData(mapHeaders, mapParameters);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setResponseStatusData(JkxYuPingStatusResponse jkxYuPingStatusResponse) {

        this.jkxYuPingStatusResponse = jkxYuPingStatusResponse;
        try {
            String code = jkxYuPingStatusResponse.getCode();
            String msg = jkxYuPingStatusResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                JkxYuPingStatusResponse.DataBean data = jkxYuPingStatusResponse.getData();
                if (data != null) {
                    setPerformanceStatus(data);
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
    public void setResponseNationalData(JkxYuPingResponse jkxYuPingResponse) {

        this.jkxYuPingResponse = jkxYuPingResponse;
        try {
            String code = jkxYuPingResponse.getCode();
            String msg = jkxYuPingResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                List<JkxYuPingResponse.DataBean> messageDate = jkxYuPingResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        List<JkxYuPingResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
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
    public void setMoreData(JkxYuPingResponse moreDate) {

        try {
            String code = moreDate.getCode();
            String msg = moreDate.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<JkxYuPingResponse.DataBean> data = moreDate.getData();
                if (data != null) {

                    for (int i = 0; i < data.size(); i++) {
                        adapter.getData().add(data.get(i));
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
                initNationalData();
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


    //    @OnClick({R2.id.tv_text1, R2.id.tv_more})
    //    public void onViewClicked(View view) {
    //        int i = view.getId();
    //        if (i == R.id.tv_text1) {
    //
    //        } else if (i == R.id.tv_more) {
    //
    //
    //        }
    //    }

    private boolean isFinish;

    @SuppressLint("SetTextI18n")
    public void setPerformanceStatus(JkxYuPingStatusResponse.DataBean performanceStatusResponse) {


        View headerView = LayoutInflater.from(mContext).inflate(R.layout.jkx_yuping_hearder, null);

        TextView tv_examName = headerView.findViewById(R.id.tv_examName);
        TextView tv_statusNameAndDate = headerView.findViewById(R.id.tv_statusNameAndDate);
        TextView tv_day1 = headerView.findViewById(R.id.tv_day1);
        TextView tv_day2 = headerView.findViewById(R.id.tv_day2);
        TextView tv_hour1 = headerView.findViewById(R.id.tv_hour1);
        TextView tv_hour2 = headerView.findViewById(R.id.tv_hour2);
        LinearLayout ll_examFinsh = headerView.findViewById(R.id.ll_examFinsh);
        LinearLayout ll_examSchedule = headerView.findViewById(R.id.ll_examSchedule);
        ll_examSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    Intent intent = new Intent(mContext, PreviewsScheduleActivity.class);
                    intent.putExtra("title", "预评进展");
                    mActivity.startActivity(intent);
                }
            }
        });
        String preview_name = performanceStatusResponse.getPREVIEW_NAME();
        if (!TextUtils.isEmpty(preview_name)) {
            tv_examName.setText(preview_name);
        }

        String preview_stage_name = performanceStatusResponse.getPREVIEW_STAGE_NAME();
        String preview_stage_end_date = performanceStatusResponse.getPREVIEW_STAGE_END_DATE();

        tv_statusNameAndDate.setText(preview_stage_name + "(截止" + preview_stage_end_date + ")");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String time = "0";
        if (performanceStatusResponse.getPREVIEW_STAGE_END_DATE() != null) {
            time = performanceStatusResponse.getPREVIEW_STAGE_END_DATE();
        }
        Date endDate = TimeUtils.string2Date(time, dateFormat);
        //        if (endDate == null) {
        //            endDate = TimeUtils.getNowDate();
        //        }
        Date nowDate = TimeUtils.getNowDate();
        if (endDate == null || endDate.before(nowDate)) {
            isFinish = true;
            ll_examFinsh.setVisibility(View.VISIBLE);
            ll_examSchedule.setVisibility(View.GONE);
        } else {
            isFinish = false;
            ll_examFinsh.setVisibility(View.GONE);
            ll_examSchedule.setVisibility(View.VISIBLE);

        }
        if (!isFinish) {
            long hours = TimeUtils.getTimeSpanByNow(endDate, TimeConstants.HOUR);
            String day = (hours / 24) + "";
            String hour = (hours % 24) + "";
            if (day.length() > 1) {
                String day1 = day.substring(0, 1);
                tv_day1.setText(day1);
                String day2 = day.substring(1, 2);
                tv_day2.setText(day2);
            } else {
                tv_day1.setText("0");
                tv_day2.setText(day);
            }

            if (hour.length() > 1) {
                String hour1 = hour.substring(0, 1);
                tv_hour1.setText(hour1);
                String hour2 = hour.substring(1, 2);
                tv_hour2.setText(hour2);
            } else {
                tv_hour1.setText("0");
                tv_hour2.setText(hour);
            }

        }


        adapter.addHeaderView(headerView);

    }
}
