package com.sxjs.jd.composition.main.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.kpimine.authentication.AuthenticationActivity;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.MessageNotificationResponse;
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

import static com.sxjs.common.constant.Constant.CONTENT_TITLE;

/**
 * @Auther: xp
 * @Date: 2019/9/21 16:50
 * @Description:  我的模块
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
    private              Handler          mHandler;
    private static final String           TAG = "MinePageFragment";
    private              UserInfoResponse userInfoResponse;
    private final int mRequestCode =1 ;

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

    }

    @Override
    public void onLazyLoad() {

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
                    ToastUtil.showToast(mContext, msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext, "解析数据失败");
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
        String authenticate_status = userInfoResponse.getData().getAUTHENTICATE_STATUS();
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

        if (!TextUtils.isEmpty(authenticate_status)) {
            //"TYPE": "0:未认证，1:认证审核中，2:变更审核中，3:已认证",

            if ("1".equals(authenticate_status)) {

                //1:认证审核中

                renzhengShenhe.setVisibility(View.VISIBLE);

                renzhengAlready.setVisibility(View.GONE);
                renzhengNo.setVisibility(View.GONE);
                renzhengBiangengShenhe.setVisibility(View.GONE);
            } else if ("0".equals(authenticate_status)) {

                //0:未认证

                renzhengAlready.setVisibility(View.GONE);
                renzhengNo.setVisibility(View.VISIBLE);
                renzhengBiangengShenhe.setVisibility(View.GONE);
                renzhengShenhe.setVisibility(View.GONE);
            } else if ("2".equals(authenticate_status)) {
                //2:变更审核中

                renzhengAlready.setVisibility(View.GONE);
                renzhengNo.setVisibility(View.GONE);
                renzhengBiangengShenhe.setVisibility(View.VISIBLE);
                renzhengShenhe.setVisibility(View.GONE);
            } else {
                //3:已认证

//                renzhengAlready.setVisibility(View.VISIBLE);
//                renzhengNo.setVisibility(View.GONE);
//                renzhengBiangengShenhe.setVisibility(View.GONE);
//                renzhengShenhe.setVisibility(View.GONE);


                //0:开发暂时写未认证

                renzhengAlready.setVisibility(View.GONE);
                renzhengNo.setVisibility(View.VISIBLE);
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
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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

        } else if (i == R.id.renzheng_no) {

            //进行医生认证
            jumpUserAuthenticationActivity();
        } else if (i == R.id.renzheng_already) {

        } else if (i == R.id.ll_userAuthentication) {

        } else if (i == R.id.rl_myFeedBack) {

        } else if (i == R.id.cert_download) {

        } else if (i == R.id.rl_findCa) {

        } else if (i == R.id.rl_modifyPassword) {

        } else if (i == R.id.hospital_change) {

        } else if (i == R.id.rl_rebindPhone) {

        } else if (i == R.id.rl_about) {

        } else if (i == R.id.tv_loginOut) {

        }
    }

    private void jumpUserAuthenticationActivity() {

        Intent intent = new Intent(mActivity, AuthenticationActivity.class);
        intent.putExtra(CONTENT_TITLE, "用户认证");

        mActivity.startActivityForResult(intent, mRequestCode);
    }
}
