package com.sxjs.jd.composition.main.middle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.antiless.support.widget.TabLayout;
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
import com.sxjs.jd.composition.html.exammiddle.ExamMiddleFragment;
import com.sxjs.jd.composition.message.MessageActivity;
import com.sxjs.jd.composition.message.MessageAdapter;
import com.sxjs.jd.composition.message.attention.AttentionFragment;
import com.sxjs.jd.composition.message.notification.NotificationFragment;
import com.sxjs.jd.composition.message.warn.WarnFragment;
import com.sxjs.jd.entities.ExamMiddleResponse;
import com.sxjs.jd.entities.HomePageResponse;
import com.sxjs.jd.entities.UnReadMessageResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description:
 */
public class MiddlePageFragment extends BaseFragment implements MiddlePageContract.View, PtrHandler {


    @Inject
    MiddlePagePresenter mPresenter;
    @BindView(R2.id.fake_status_bar)
    View                fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView            jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button              jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView            jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView            jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView            newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout      rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView            jkxTitleRight;
    @BindView(R2.id.img)
    ImageView           img;
    @BindView(R2.id.tip)
    TextView            tip;
    @BindView(R2.id.empty_tip)
    RelativeLayout      emptyTip;
    @BindView(R2.id.year)
    TextView            year;
    @BindView(R2.id.report)
    TextView            report;
    @BindView(R2.id.tabLayout)
    TabLayout           tabLayout;
    @BindView(R2.id.jkx_viewpager)
    ViewPager           jkxViewpager;
    @BindView(R2.id.normal_display)
    LinearLayout        normalDisplay;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView        findPullRefreshHeader;
    private static final String TAG = "MiddlePageFragment";

    private Handler               mHandler;
    private UnReadMessageResponse unReadMessageResponse;
    private Intent                mIntent;
    private int                   mTz = 0;
    private int                   mTx = 0;
    private int                   mGz = 0;

    List<Fragment> fragments;
    private MiddlePageAdapter adapter;

    private ExamMiddleResponse examMiddleResponse;
    private List<String>       mTabTitle;
    private List<String>       mTabUrl;
    private String             mSession_id;
    private String             mCurrenYear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_middle_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        mCurrenYear = simpleDateFormat.format(date);

        initTitle();
        initView();
        initData();
        initExamMiddleData(mCurrenYear);
        return view;

    }

    private void initDataFragment(List<String> mTabTitle,List<String>       mTabUrl) {

        fragments = new ArrayList<>();
        for (int i = 0; i < mTabUrl.size(); i++) {
            ExamMiddleFragment examMiddleFragment = ExamMiddleFragment.newInstance(mTabUrl.get(i));
            fragments.add(examMiddleFragment);
        }
        adapter = new MiddlePageAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), fragments, mTabTitle);
        jkxViewpager.setAdapter(adapter);
        jkxViewpager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(jkxViewpager);
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //扫一扫
        jkxTitleLeftBtn.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText("考中");

        //消息
        jkxTitleRightBtn.setVisibility(View.VISIBLE);


    }

    public static MiddlePageFragment newInstance() {
        return new MiddlePageFragment();
    }

    public void initView() {

        DaggerMiddlePageComponent.builder()
                .appComponent(getAppComponent())
                .middlePageModule(new MiddlePageModule(this, MainDataManager.getInstance(mDataManager)))
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


        Map<String, Object> mapParameters = new HashMap<>(1);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I002");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);


    }

    public void initExamMiddleData(String mCurrenYear) {


        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("YEAR", mCurrenYear);
        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "X003");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getExamMiddleData(mapHeaders, mapParameters);


    }

    @Override
    public void setResponseData(UnReadMessageResponse unReadMessageResponse) {
        this.unReadMessageResponse = unReadMessageResponse;

        try {
            String code = unReadMessageResponse.getCode();
            String msg = unReadMessageResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {


                //{"code":"200200","data":{"YJ":1,"TX":2,"WD":1},"msg":null}
                UnReadMessageResponse.DataBean data = unReadMessageResponse.getData();
                if (data != null) {
                    mTz = data.getWD();
                    mTx = data.getTX();
                    mGz = data.getYJ();
                    setUnReadMessage(data);

                } else {

                }


                //                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
                //                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();

            } else {


                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext, msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext, "解析数据失败:");
            LogUtil.e(TAG, "解析数据失败:" + e.getMessage());
        }
    }

    @Override
    public void setExamMiddleData(ExamMiddleResponse examMiddleResponse) {
        this.examMiddleResponse = examMiddleResponse;

        try {
            String code = examMiddleResponse.getCode();
            String msg = examMiddleResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                emptyTip.setVisibility(View.GONE);
                normalDisplay.setVisibility(View.VISIBLE);

                List<ExamMiddleResponse.DataBean> data = examMiddleResponse.getData();
                mTabTitle = new ArrayList<>();
                mTabUrl = new ArrayList<>();
                if (data != null) {
                    for (int i = 0; i < data.size(); i++) {
                        String name = data.get(i).getNAME();
                        mTabTitle.add(name);
                        String uri = data.get(i).getURI() + "&sessionId=" + mSession_id;
                        mTabUrl.add(uri);
                    }
                }

                initDataFragment(mTabTitle,mTabUrl);


            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();

            } else {
                emptyTip.setVisibility(View.VISIBLE);
                normalDisplay.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext, msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext, "解析数据失败:");
            LogUtil.e(TAG, "解析数据失败:" + e.getMessage());
        }
    }

    /**
     * 未读消息设置
     *
     * @param unReadMessage 未读消息响应数据
     */
    @SuppressLint("SetTextI18n")
    public void setUnReadMessage(UnReadMessageResponse.DataBean unReadMessage) {
        if (unReadMessage == null)
            return;
        int tz = unReadMessage.getWD();
        int tx = unReadMessage.getTX();
        int gz = unReadMessage.getYJ();
        if (tz + tx + gz != 0) {
            rlNewMessage.setVisibility(View.VISIBLE);
            newMessage.setText(tz + tx + gz + "");
            if (tz + tx + gz > 99) {
                newMessage.setText("99+");
            }
        } else {
            rlNewMessage.setVisibility(View.GONE);
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

                Map<String, Object> mapParameters = new HashMap<>(1);
                mapParameters.put("YEAR", mCurrenYear);
                Map<String, String> mapHeaders = new HashMap<>(2);
                mapHeaders.put("ACTION", "X003");
                mapHeaders.put("SESSION_ID", mSession_id);

                mPresenter.getExamMiddleData(mapHeaders, mapParameters);



                frame.refreshComplete();
            }
        }, 2000);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @OnClick({R2.id.jkx_title_left_btn, R2.id.jkx_title_right_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left_btn) {
            //开启扫一扫


        } else if (i == R.id.jkx_title_right_btn) {


            //我的消息


            mIntent = new Intent(mActivity, MessageActivity.class);

            mIntent.putExtra("tz", mTz);
            mIntent.putExtra("tx", mTx);
            mIntent.putExtra("gz", mGz);


            startActivity(mIntent);
        }
    }
}
