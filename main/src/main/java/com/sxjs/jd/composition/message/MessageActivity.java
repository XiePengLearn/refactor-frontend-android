package com.sxjs.jd.composition.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.antiless.support.widget.TabLayout;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.message.attention.AttentionFragment;
import com.sxjs.jd.composition.message.notification.NotificationFragment;
import com.sxjs.jd.composition.message.warn.WarnFragment;
import com.sxjs.jd.entities.LoginResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/messageActivity/messageActivity")
public class MessageActivity extends BaseActivity implements MessageContract.View {
    @Inject
    MessagePresenter presenter;
    @BindView(R2.id.fake_status_bar)
    View             fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView         jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button           jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView         jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView         jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView         newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout   rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView         jkxTitleRight;
    @BindView(R2.id.tabLayout)
    TabLayout        tabLayout;
    @BindView(R2.id.jkx_viewpage)
    ViewPager        jkxViewpage;
    @BindView(R2.id.new_message1)
    TextView         newMessage1;
    @BindView(R2.id.new_message2)
    TextView         newMessage2;
    @BindView(R2.id.new_message3)
    TextView         newMessage3;


    private              String        mXinGeToken;
    private static final String        TAG = "MessageActivity";
    private              Button        mLoginEntry;
    private              LoginResponse loginResponse;

    List<Fragment> fragments;
    private MessageAdapter adapter;
    String[] tabTitle = {"通知", "提醒", "关注"};
    private Intent mIntent;
    private int mTz;
    private int mTx;
    private int mGz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        mIntent = getIntent();
        mTz = mIntent.getIntExtra("tz", 0);
        if(mTz>0){
            newMessage1.setVisibility(View.VISIBLE);
            newMessage1.setText(""+mTz);
        }else {
            newMessage1.setVisibility(View.INVISIBLE);
        }
        mTx = mIntent.getIntExtra("tx", 0);
        if(mTx>0){
            newMessage2.setVisibility(View.VISIBLE);
            newMessage2.setText(""+mTx);
        }else {
            newMessage2.setVisibility(View.INVISIBLE);
        }
        mGz = mIntent.getIntExtra("gz", 0);
        if(mGz>0){
            newMessage3.setVisibility(View.VISIBLE);
            newMessage3.setText(""+mGz);
        }else {
            newMessage3.setVisibility(View.INVISIBLE);
        }

        initTitle();
        initView();
        initDataFragment();


    }

    private void initDataFragment() {

        fragments = new ArrayList<>();
        NotificationFragment notificationFragment = NotificationFragment.newInstance();
        WarnFragment warnFragment = WarnFragment.newInstance();
        AttentionFragment attentionFragment = AttentionFragment.newInstance();
        fragments.add(notificationFragment);
        fragments.add(warnFragment);
        fragments.add(attentionFragment);
        adapter = new MessageAdapter(getSupportFragmentManager(), fragments, tabTitle);
        jkxViewpage.setAdapter(adapter);
        jkxViewpage.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(jkxViewpage);
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //扫一扫
        jkxTitleLeft.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText("我的消息");

        //消息
        //        jkxTitleRightBtn.setVisibility(View.VISIBLE);


    }

    private void initView() {


        DaggerMessageActivityComponent.builder()
                .appComponent(getAppComponent())
                .messagePresenterModule(new MessagePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);


    }

    public void initData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getLoginData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public void setLoginData(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        try {
            String code = loginResponse.getCode();
            String msg = loginResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + loginResponse.getData());


                //                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
                //                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }


    }


    private void LoginMethod() {


        Map<String, Object> mapParameters = new HashMap<>(6);
        //        mapParameters.put("MOBILE", lAccount);
        //        mapParameters.put("PASSWORD", lPassword);
        mapParameters.put("SIGNIN_TYPE", "1");
        mapParameters.put("USER_TYPE", "1");
        mapParameters.put("MOBILE_TYPE", "1");
        mapParameters.put("XINGE_TOKEN", mXinGeToken);

        Map<String, String> mapHeaders = new HashMap<>(1);
        mapHeaders.put("ACTION", "S002");
        //        mapHeaders.put("SESSION_ID", TaskManager.SESSION_ID);

        initData(mapHeaders, mapParameters);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("loginResponse", loginResponse);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            LoginResponse loginResponse = (LoginResponse) savedInstanceState.getSerializable("loginResponse");
            this.loginResponse = loginResponse;

        }
    }


    @Override
    public void showProgressDialogView() {
        showProgressDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hiddenProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
        }


    }

    @OnClick({R2.id.jkx_title_left, R2.id.jkx_title_left_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {


        } else if (i == R.id.jkx_title_left_btn) {

        }
    }
}
