package com.sxjs.jd.composition.kpibefore.indicators;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.BeforeIndicatorsResponse;

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
 * @Date: 2019/9/24 16:50
 * @Description: 快速开发Fragment
 */
public class IndicatorsMonitorFragment extends BaseFragment implements IndicatorsMonitorFragmentContract.View, PtrHandler {
    @Inject
    IndicatorsMonitorFragmentPresenter mPresenter;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView                       findPullRefreshHeader;
    @BindView(R2.id.ll_indicator)
    LinearLayout                       llIndicator;
    @BindView(R2.id.tv_time)
    TextView                           tvTime;
    @BindView(R2.id.tv_followMore)
    TextView                           tvFollowMore;
    @BindView(R2.id.ll_follow)
    LinearLayout                       llFollow;
    Unbinder unbinder;
    private Handler mHandler;

    private static final String TAG = "IndicatorsMonitorFragment";

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indicators_monitor, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onLazyLoad() {
        initView();
        initData();
    }

    public static IndicatorsMonitorFragment newInstance() {
        IndicatorsMonitorFragment quicklyFragment = new IndicatorsMonitorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        quicklyFragment.setArguments(bundle);
        return quicklyFragment;
    }

    public void initView() {

       String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerIndicatorsMonitorFragmentComponent.builder()
                .appComponent(getAppComponent())
                .indicatorsMonitorFragmentModule(new IndicatorsMonitorFragmentModule(this, MainDataManager.getInstance(mDataManager)))
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
        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "Y001");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }


    @Override
    public void setResponseData(BeforeIndicatorsResponse beforeIndicatorsResponse) {
        try {
            String code = beforeIndicatorsResponse.getCode();
            String msg = beforeIndicatorsResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                BeforeIndicatorsResponse.DataBean data = beforeIndicatorsResponse.getData();
                if (data != null) {
                    notifyWatch(data);
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


    @OnClick(R2.id.tv_followMore)
    public void onViewClicked() {


    }

    @SuppressLint("SetTextI18n")
    public void notifyWatch(BeforeIndicatorsResponse.DataBean data) {
        //            tv_followMore.setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View view) {
        //                    mEventCallBack.EventClick(JkxBeforeExamFragment.EVENT_MORE, null);
        //                }
        //            });
        String follow_indicators_end_date = data.getFOLLOW_INDICATORS_END_DATE();
        if (!TextUtils.isEmpty(follow_indicators_end_date)) {
            tvTime.setText("关注指标（截至" + follow_indicators_end_date + "）");
        } else {
            tvTime.setText("关注指标");
        }
        llIndicator.removeAllViews();
        llFollow.removeAllViews();

        List<BeforeIndicatorsResponse.DataBean.ABNORMALINDICATORSBean> abnormal_indicators = data.getABNORMAL_INDICATORS();
        if (abnormal_indicators != null) {
            for (int i = 0; i < abnormal_indicators.size(); i++) {
                View abnormalView = LayoutInflater.from(mContext).inflate(R.layout.jkx_zhibiao_item, null);
                TextView tv_name = abnormalView.findViewById(R.id.tv_name);
                TextView tv_value = abnormalView.findViewById(R.id.tv_value);
                TextView tv_content = abnormalView.findViewById(R.id.tv_content);
                ImageView iv_type = abnormalView.findViewById(R.id.iv_type);

                String type = abnormal_indicators.get(i).getSIGN_TYPE();
                if ("0".equals(type)) {
                    tv_value.setTextColor(mContext.getResources().getColor(R.color.black));
                    tv_content.setTextColor(mContext.getResources().getColor(R.color.black));
                    iv_type.setVisibility(View.GONE);
                } else if ("1".equals(type)) {
                    tv_value.setTextColor(mContext.getResources().getColor(R.color.indicator));
                    tv_content.setTextColor(mContext.getResources().getColor(R.color.indicator));
                    iv_type.setImageResource(R.drawable.bj_leidian);
                    iv_type.setVisibility(View.VISIBLE);
                } else if ("2".equals(type)) {
                    tv_value.setTextColor(mContext.getResources().getColor(R.color.exception));
                    tv_content.setTextColor(mContext.getResources().getColor(R.color.exception));
                    iv_type.setImageResource(R.drawable.bj_warn);
                    iv_type.setVisibility(View.VISIBLE);
                }

                tv_name.setText(abnormal_indicators.get(i).getINDICATOR_NAME());
                tv_value.setText(abnormal_indicators.get(i).getINDICATOR_VALUE());
                if (abnormal_indicators.get(i).getINDICATOR_CONTENT() != null) {
                    tv_content.setVisibility(View.VISIBLE);
                    tv_content.setText(abnormal_indicators.get(i).getINDICATOR_CONTENT());
                } else {
                    tv_content.setVisibility(View.GONE);
                }
                llIndicator.addView(abnormalView);
            }
        }

        List<BeforeIndicatorsResponse.DataBean.FOLLOWINDICATORSBean> follow_indicators = data.getFOLLOW_INDICATORS();
        if (follow_indicators != null) {
            for (int i = 0; i < follow_indicators.size(); i++) {
                View followView = LayoutInflater.from(mContext).inflate(R.layout.jkx_zhibiao_item, null);
                TextView tv_name = followView.findViewById(R.id.tv_name);
                TextView tv_value = followView.findViewById(R.id.tv_value);
                TextView tv_content = followView.findViewById(R.id.tv_content);
                tv_content.setVisibility(View.GONE);
                ImageView iv_type = followView.findViewById(R.id.iv_type);
                iv_type.setVisibility(View.GONE);
                tv_name.setText(follow_indicators.get(i).getINDICATOR_NAME());
                tv_value.setText(follow_indicators.get(i).getINDICATOR_VALUE());
                llFollow.addView(followView);
            }
        }

    }
}
