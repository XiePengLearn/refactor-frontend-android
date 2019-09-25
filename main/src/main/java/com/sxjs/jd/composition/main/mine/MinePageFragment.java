package com.sxjs.jd.composition.main.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseFragment;
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
import com.sxjs.jd.composition.kpimine.aboutme.AboutMeActivity;
import com.sxjs.jd.composition.kpimine.authentication.AuthenticationActivity;
import com.sxjs.jd.composition.kpimine.change.ChangeAuthenticationActivity;
import com.sxjs.jd.composition.kpimine.feedback.FeedBackActivity;
import com.sxjs.jd.composition.login.changepassage.ChangePasswordActivity;
import com.sxjs.jd.composition.login.changephone.ChangePhoneActivity;
import com.sxjs.jd.composition.message.MessageActivity;
import com.sxjs.jd.entities.FeedBackResponse;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.MessageNotificationResponse;
import com.sxjs.jd.entities.UnReadMessageResponse;
import com.sxjs.jd.entities.UserInfoResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.sxjs.common.constant.Constant.CONTENT_TITLE;

/**
 * @Auther: xp
 * @Date: 2019/9/21 16:50
 * @Description: 我的模块
 */
public class MinePageFragment extends BaseFragment implements MinePageContract.View, PtrHandler {


    @Inject
    MinePagePresenter mPresenter;
    @BindView(R2.id.fake_status_bar)
    View              fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView          jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button            jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView          jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView          jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView          newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout    rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView          jkxTitleRight;
    @BindView(R2.id.iv_userPic)
    ImageView         ivUserPic;
    @BindView(R2.id.tv_name)
    TextView          tvName;
    @BindView(R2.id.iv_isVip)
    ImageView         ivIsVip;
    @BindView(R2.id.renzheng_no)
    TextView          renzhengNo;
    @BindView(R2.id.renzheng_shenhe)
    TextView          renzhengShenhe;
    @BindView(R2.id.renzheng_biangeng_shenhe)
    TextView          renzhengBiangengShenhe;
    @BindView(R2.id.renzheng_already)
    TextView          renzhengAlready;
    @BindView(R2.id.tv_isAuthentation)
    TextView          tvIsAuthentation;
    @BindView(R2.id.ll_userAuthentication)
    LinearLayout      llUserAuthentication;
    @BindView(R2.id.iv_image1)
    ImageView         ivImage1;
    @BindView(R2.id.rl_myFeedBack)
    RelativeLayout    rlMyFeedBack;
    @BindView(R2.id.iv_image2)
    ImageView         ivImage2;
    @BindView(R2.id.cert_download)
    RelativeLayout    certDownload;
    @BindView(R2.id.iv_image3)
    ImageView         ivImage3;
    @BindView(R2.id.rl_findCa)
    RelativeLayout    rlFindCa;
    @BindView(R2.id.iv_image6)
    ImageView         ivImage6;
    @BindView(R2.id.rl_modifyPassword)
    RelativeLayout    rlModifyPassword;
    @BindView(R2.id.iv_image10)
    ImageView         ivImage10;
    @BindView(R2.id.hospital_change)
    RelativeLayout    hospitalChange;
    @BindView(R2.id.iv_image7)
    ImageView         ivImage7;
    @BindView(R2.id.rl_rebindPhone)
    RelativeLayout    rlRebindPhone;
    @BindView(R2.id.iv_image4)
    ImageView         ivImage4;
    @BindView(R2.id.rl_about)
    RelativeLayout    rlAbout;
    @BindView(R2.id.tv_loginOut)
    TextView          tvLoginOut;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView      findPullRefreshHeader;
    private              Handler               mHandler;
    private static final String                TAG                = "MinePageFragment";
    private              UserInfoResponse      userInfoResponse;
    private final        int                   mRequestCode       = 1;
    private final        int                   mChangeRequestCode = 2;
    private              String                mAuthenticate_status;
    private              UnReadMessageResponse unReadMessageResponse;

    private       int    mTz            = 0;
    private       int    mTx            = 0;
    private       int    mGz            = 0;
    private       Intent mIntent;
    private final String MESSAGE_ACTION = "com.jkx.message"; // 消息通知的广播名称

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {
        initTitle();
        initView();
        initData();
        initMessageData();

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

        LogUtil.e(TAG, "-----MinePage收到信鸽的服务推送消息-----");
        //初始用户信息
        initData();

        //初始化消息数据
        initMessageData();
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //扫一扫
        jkxTitleLeftBtn.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText("我的");

        //消息
        jkxTitleRightBtn.setVisibility(View.VISIBLE);


    }

    public static MinePageFragment newInstance() {
        return new MinePageFragment();
    }

    public void initView() {

        DaggerMinePageComponent.builder()
                .appComponent(getAppComponent())
                .minePageModule(new MinePageModule(this, MainDataManager.getInstance(mDataManager)))
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
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("MESSAGE_TYPE", "3");

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "S004");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }

    //未读消息
    public void initMessageData() {
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I002");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getUnreadMessageRequestData(mapHeaders, mapParameters);


    }

    @Override
    public void setResponseData(UserInfoResponse userInfoResponse) {
        this.userInfoResponse = userInfoResponse;
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
    public void setUnreadMessageResponseData(UnReadMessageResponse unReadMessageResponse) {
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

        String name = userInfoResponse.getData().getNAME();

        String head_portrait = userInfoResponse.getData().getHEAD_PORTRAIT();
        String vip_status = userInfoResponse.getData().getVIP_STATUS();
        mAuthenticate_status = userInfoResponse.getData().getAUTHENTICATE_STATUS();
        if (!TextUtils.isEmpty(name)) {
            tvName.setText(name);
        }
        if (!TextUtils.isEmpty(vip_status)) {
            if (!"1".equals(vip_status)) {
                ivIsVip.setVisibility(View.GONE);
            } else {
                ivIsVip.setVisibility(View.VISIBLE);
            }
        } else {
            ivIsVip.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mAuthenticate_status)) {
            //"TYPE": "0:未认证，1:认证审核中，2:变更审核中，3:已认证",

            if ("1".equals(mAuthenticate_status)) {

                //1:认证审核中

                renzhengShenhe.setVisibility(View.VISIBLE);

                renzhengAlready.setVisibility(View.GONE);
                renzhengNo.setVisibility(View.GONE);
                renzhengBiangengShenhe.setVisibility(View.GONE);


            } else if ("0".equals(mAuthenticate_status)) {

                //0:未认证

                renzhengAlready.setVisibility(View.GONE);
                renzhengNo.setVisibility(View.VISIBLE);
                renzhengBiangengShenhe.setVisibility(View.GONE);
                renzhengShenhe.setVisibility(View.GONE);
            } else if ("2".equals(mAuthenticate_status)) {
                //2:变更审核中

                renzhengAlready.setVisibility(View.GONE);
                renzhengNo.setVisibility(View.GONE);
                renzhengBiangengShenhe.setVisibility(View.VISIBLE);
                renzhengShenhe.setVisibility(View.GONE);
            } else {
                //3:已认证

                renzhengAlready.setVisibility(View.VISIBLE);
                renzhengNo.setVisibility(View.GONE);
                renzhengBiangengShenhe.setVisibility(View.GONE);
                renzhengShenhe.setVisibility(View.GONE);


            }

        } else {
            //0:未认证

            renzhengAlready.setVisibility(View.GONE);
            renzhengNo.setVisibility(View.VISIBLE);
            renzhengBiangengShenhe.setVisibility(View.GONE);
            renzhengShenhe.setVisibility(View.GONE);
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
        if (mSystemMessageReceiver != null) {
            mActivity.unregisterReceiver(mSystemMessageReceiver);
        }
    }


    @OnClick({R2.id.jkx_title_left, R2.id.jkx_title_left_btn, R2.id.jkx_title_right_btn,
            R2.id.renzheng_no, R2.id.renzheng_already, R2.id.ll_userAuthentication,
            R2.id.rl_myFeedBack, R2.id.cert_download, R2.id.rl_findCa, R2.id.rl_modifyPassword,
            R2.id.hospital_change, R2.id.rl_rebindPhone, R2.id.rl_about, R2.id.tv_loginOut})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {

        } else if (i == R.id.jkx_title_left_btn) {

        } else if (i == R.id.jkx_title_right_btn) {
            //我的消息
            if (!NoDoubleClickUtils.isDoubleClick())
                mIntent = new Intent(mActivity, MessageActivity.class);
            mIntent.putExtra("tz", mTz);
            mIntent.putExtra("tx", mTx);
            mIntent.putExtra("gz", mGz);
            startActivity(mIntent);
        } else if (i == R.id.renzheng_no) {
            if (!NoDoubleClickUtils.isDoubleClick()) {
                jumpUserAuthenticationActivity();
            }


        } else if (i == R.id.renzheng_already) {

        } else if (i == R.id.ll_userAuthentication) {

        } else if (i == R.id.rl_myFeedBack) {
            //我的反馈
            if (!NoDoubleClickUtils.isDoubleClick())
                jumpMyFeedBackActivity();

        } else if (i == R.id.cert_download) {
            ToastUtil.showToast(mContext.getApplicationContext(), "开发中");
        } else if (i == R.id.rl_findCa) {
            ToastUtil.showToast(mContext.getApplicationContext(), "开发中");
        } else if (i == R.id.rl_modifyPassword) {
            //修改密码
            if (!NoDoubleClickUtils.isDoubleClick())
                jumpModifyPasswordActivity();
        } else if (i == R.id.hospital_change) {
            //医院变更
            if (!NoDoubleClickUtils.isDoubleClick())
                HospitalChangeMethod();
        } else if (i == R.id.rl_rebindPhone) {
            //更换手机号码
            if (!NoDoubleClickUtils.isDoubleClick())
                jumpRebindPhoneActivity();
        } else if (i == R.id.rl_about) {
            //关于
            if (!NoDoubleClickUtils.isDoubleClick())
                jumpAboutMeActivity();
        } else if (i == R.id.tv_loginOut) {
            //退出
            if (!NoDoubleClickUtils.isDoubleClick())
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

            mActivity.finish();

        }
    }

    private void jumpMyFeedBackActivity() {
        Intent intent = new Intent(mActivity, FeedBackActivity.class);
        intent.putExtra(CONTENT_TITLE, "我的反馈");

        startActivity(intent);
    }

    private void jumpRebindPhoneActivity() {
        Intent intent = new Intent(mActivity, ChangePhoneActivity.class);
        intent.putExtra(CONTENT_TITLE, "更换手机");

        startActivity(intent);

    }

    private void HospitalChangeMethod() {
        if (!TextUtils.isEmpty(mAuthenticate_status)) {
            //"TYPE": "0:未认证，1:认证审核中，2:变更审核中，3:已认证",
            if ("1".equals(mAuthenticate_status)) {
                //1:认证审核中
                ToastUtil.showToast(mContext.getApplicationContext(), "认证审核中,不能进行医院变更");

            } else if ("0".equals(mAuthenticate_status)) {
                //0:未认证
                ToastUtil.showToast(mContext.getApplicationContext(), "未认证,请认证");
            } else if ("2".equals(mAuthenticate_status)) {
                //2:变更审核中
                ToastUtil.showToast(mContext.getApplicationContext(), "变更审核中,不能进行医院变更");
            } else {
                //3:已认证
                //进行医生认证
                jumpHospitalChangeActivity();
            }

        } else {
            //0:未认证
            ToastUtil.showToast(mContext.getApplicationContext(), "未认证,请认证");

        }
    }

    private void jumpModifyPasswordActivity() {
        Intent intent = new Intent(mActivity, ChangePasswordActivity.class);
        intent.putExtra(CONTENT_TITLE, "修改密码");

        startActivity(intent);

    }

    private void jumpHospitalChangeActivity() {
        Intent intent = new Intent(mActivity, ChangeAuthenticationActivity.class);
        intent.putExtra(CONTENT_TITLE, "医院变更");

        startActivityForResult(intent, mChangeRequestCode);
    }

    private void jumpAboutMeActivity() {
        Intent intent = new Intent(mActivity, AboutMeActivity.class);
        intent.putExtra(CONTENT_TITLE, "关于");

        startActivity(intent);
    }

    private void jumpUserAuthenticationActivity() {

        Intent intent = new Intent(mActivity, AuthenticationActivity.class);
        intent.putExtra(CONTENT_TITLE, "用户认证");

        startActivityForResult(intent, mRequestCode);
    }

    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     * <p>
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCode) {
            if (resultCode == RESULT_OK) {
                String userAuthenticationCommitResult = data.getStringExtra("userAuthenticationCommitResult");
                if (!TextUtils.isEmpty(userAuthenticationCommitResult)) {
                    //用户认证提交成功，变更用户状态
                    if (userAuthenticationCommitResult.equals("SUCCESS_OK")) {
                        //1:认证审核中

                        renzhengShenhe.setVisibility(View.VISIBLE);

                        renzhengAlready.setVisibility(View.GONE);
                        renzhengNo.setVisibility(View.GONE);
                        renzhengBiangengShenhe.setVisibility(View.GONE);
                    }
                }

            }

        } else if (requestCode == mChangeRequestCode) {
            if (resultCode == RESULT_OK) {
                String userAuthenticationCommitResult = data.getStringExtra("userAuthenticationCommitResult");
                if (!TextUtils.isEmpty(userAuthenticationCommitResult)) {
                    //用户认证提交成功，变更用户状态
                    if (userAuthenticationCommitResult.equals("SUCCESS_OK")) {
                        //2:变更审核中

                        renzhengAlready.setVisibility(View.GONE);
                        renzhengNo.setVisibility(View.GONE);
                        renzhengBiangengShenhe.setVisibility(View.VISIBLE);
                        renzhengShenhe.setVisibility(View.GONE);
                    }
                }

            }
        }

    }
}
