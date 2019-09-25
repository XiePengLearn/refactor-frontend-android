package com.sxjs.jd.composition.main.befor;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.antiless.support.widget.TabLayout;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.NoDoubleClickUtils;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.kpibefore.indicators.IndicatorsMonitorFragment;
import com.sxjs.jd.composition.kpibefore.national.NationalPreviewsFragment;
import com.sxjs.jd.composition.kpibefore.quality.MedicalQualityFragment;
import com.sxjs.jd.composition.message.MessageActivity;
import com.sxjs.jd.entities.LoginResponse;
import com.sxjs.jd.entities.UnReadMessageResponse;
import com.sxjs.jd.entities.UserInfoResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class BeforePageFragment extends BaseFragment implements BeforePageContract.View {


    @Inject
    BeforePagePresenter mPresenter;


    Unbinder unbinder;
    @BindView(R2.id.fake_status_bar)
    View           fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView       jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button         jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView       jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView       jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView       newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView       jkxTitleRight;
    @BindView(R2.id.tabLayout_before)
    TabLayout      tabLayout;
    @BindView(R2.id.jkx_viewpager_before)
    ViewPager      jkxViewpager;
    @BindView(R2.id.before_normal_display)
    LinearLayout   normalDisplay;
    @BindView(R2.id.img)
    ImageView      img;
    @BindView(R2.id.tip)
    TextView       tip;
    @BindView(R2.id.before_empty_tip)
    RelativeLayout emptyTip;
    @BindView(R2.id.no_data_img)
    ImageView      noDataImg;
    @BindView(R2.id.before_rl_no_data)
    RelativeLayout rlNoData;

    private static final String TAG = "BeforePageFragment";
    Unbinder unbinder1;

    private Handler               mHandler;
    private UnReadMessageResponse unReadMessageResponse;
    private Intent                mIntent;
    private int                   mTz = 0;
    private int                   mTx = 0;
    private int                   mGz = 0;

    private List<Fragment>    fragments;
    private BeforePageAdapter adapter;

    private       List<String> mMTabTitle;
    private final String       MESSAGE_ACTION   = "com.jkx.message"; // 消息通知的广播名称
    private       String       mAuthenticate_status;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void initEvent() {

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        initTitle();
        initView();
        initData();
        //初始化用户认证状态
        initUserStatusData();
        mMTabTitle = new ArrayList<>();
        mMTabTitle.add("指标监控");
        mMTabTitle.add("病案质量");
        mMTabTitle.add("国考预评");
        initDataFragment(mMTabTitle);

        registerMessageBroadcast();

    }

    @Override
    public void onLazyLoad() {

    }

    /**
     * 注册消息广播
     */
    private void registerMessageBroadcast() {
        IntentFilter filter = new IntentFilter(MESSAGE_ACTION);
        mActivity.registerReceiver(mSystemMessageReceiver, filter);// 注册广播
    }

    private BroadcastReceiver mSystemMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MESSAGE_ACTION.equals(action)) {
                initMoreState();
            }
        }

    };

    private void initMoreState() {

        LogUtil.e(TAG, "-----BeforePage收到信鸽的服务推送消息-----");


        initLogin();

    }
    private void initLogin() {
        String lAccount = PrefUtils.readUserNameDefault(mContext.getApplicationContext());
        String lPassword = PrefUtils.readPasswordDefault(mContext.getApplicationContext());

        String mXinGeToken = PrefUtils.readXinGeToken(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("MOBILE", lAccount);
        mapParameters.put("PASSWORD", lPassword);
        mapParameters.put("SIGNIN_TYPE", "1");
        mapParameters.put("USER_TYPE", "1");
        mapParameters.put("MOBILE_TYPE", "1");
        mapParameters.put("XINGE_TOKEN", mXinGeToken);
        LogUtil.e(TAG, "-------mXinGeToken-------" + mXinGeToken);

        Map<String, String> mapHeaders = new HashMap<>(1);
        mapHeaders.put("ACTION", "S002");
        //        mapHeaders.put("SESSION_ID", TaskManager.SESSION_ID);

        mPresenter.getLoginData(mapHeaders, mapParameters);
    }
    private void initDataFragment(List<String> mTabTitle) {
        fragments = new ArrayList<>();
        fragments.clear();
        IndicatorsMonitorFragment indicatorsMonitorFragment = IndicatorsMonitorFragment.newInstance();
        MedicalQualityFragment medicalQualityFragment = MedicalQualityFragment.newInstance();
        NationalPreviewsFragment nationalPreviewsFragment = NationalPreviewsFragment.newInstance();
        fragments.add(indicatorsMonitorFragment);
        fragments.add(medicalQualityFragment);
        fragments.add(nationalPreviewsFragment);
        adapter = new BeforePageAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), fragments, mTabTitle);
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

    public static BeforePageFragment newInstance() {
        return new BeforePageFragment();
    }

    public void initView() {

        DaggerBeforePageComponent.builder()
                .appComponent(getAppComponent())
                .beforePageModule(new BeforePageModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //


        //                findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        //                adapter = new FindsAdapter(R.layout.item_finds_recyclerview);
        //                adapter.setOnLoadMoreListener(this);
        //                adapter.setEnableLoadMore(true);
        //                findRecyclerview.setAdapter(adapter);

        mHandler = new Handler();


    }

    //未读消息
    public void initData() {
        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I002");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);


    }
    public void initUserStatusData() {
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("MESSAGE_TYPE", "3");

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "S004");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getUserStatusData(mapHeaders, mapParameters);
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
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败:");
            LogUtil.e(TAG, "解析数据失败:" + e.getMessage());
        }
    }

    @Override
    public void setUserStatusData(UserInfoResponse userInfoResponse) {
        try {
            String code = userInfoResponse.getCode();
            String msg = userInfoResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                //初始化 用户信息
                initUserInfo(userInfoResponse);
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
    public void setLoginData(LoginResponse loginResponse) {
        try {
            String code = loginResponse.getCode();
            String msg = loginResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "----------BeforeFragment用户默认登录了-------------: ");

                String SESSION_ID = loginResponse.getData();
                PrefUtils.writeSESSION_ID(SESSION_ID, mContext.getApplicationContext());

                //初始化消息数据
                initData();
                //初始化用户认证状态
                initUserStatusData();
                //初始化考前数据
                initDataFragment(mMTabTitle);
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        }
    }

    /**
     * 初始化用户信息
     */
    public void initUserInfo(UserInfoResponse userInfoResponse) {

        if (userInfoResponse == null) {
            ToastUtil.showToast(getActivity(), mActivity.getResources().getString(R.string.init_userdata_failed), Toast.LENGTH_SHORT);
            return;
        }

        /**
         * “NAME”: “姓名”,
         * 		“HEAD_PORTRAIT”: “头像url”,
         * 		“AUTHENTICATE_STATUS”: “认证状态”,
         * 		“VIP_STATUS”: “VIP状态（ 医院）”
         */

        mAuthenticate_status = userInfoResponse.getData().getAUTHENTICATE_STATUS();

        if (!TextUtils.isEmpty(mAuthenticate_status)) {
            //"TYPE": "0:未认证，1:认证审核中，2:变更审核中，3:已认证",

            if ("1".equals(mAuthenticate_status)) {
                //1:认证审核中
                tip.setText("认证审核中");
                normalDisplay.setVisibility(View.GONE);
                emptyTip.setVisibility(View.VISIBLE);
            } else if ("0".equals(mAuthenticate_status)) {
                //0:未认证
                normalDisplay.setVisibility(View.GONE);
                emptyTip.setVisibility(View.VISIBLE);
                tip.setText("请去“我的”页面进行认证");

            } else {
                //2:变更审核中
                //3:已认证
                normalDisplay.setVisibility(View.VISIBLE);
                emptyTip.setVisibility(View.GONE);

            }

        } else {
            //0:未认证
            normalDisplay.setVisibility(View.GONE);
            emptyTip.setVisibility(View.VISIBLE);
            tip.setText("请去“我的”页面进行认证");
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


    PopupWindow popupWindow;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mSystemMessageReceiver != null) {
            mActivity.unregisterReceiver(mSystemMessageReceiver);
        }
    }


    @OnClick({R2.id.jkx_title_left_btn, R2.id.jkx_title_right_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left_btn) {
            //开启扫一扫


        } else if (i == R.id.jkx_title_right_btn) {

            if (!NoDoubleClickUtils.isDoubleClick()) {
                //我的消息
                mIntent = new Intent(mActivity, MessageActivity.class);

                mIntent.putExtra("tz", mTz);
                mIntent.putExtra("tx", mTx);
                mIntent.putExtra("gz", mGz);
                startActivity(mIntent);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
