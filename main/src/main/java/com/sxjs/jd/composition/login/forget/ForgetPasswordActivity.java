package com.sxjs.jd.composition.login.forget;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.HandlerFactory;
import com.sxjs.common.util.PhoneValidator;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.StringUtils;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.Tool;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/forget/forget")
public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View {

    @Inject
    ForgetPasswordPresenter presenter;

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
    @BindView(R2.id.find_phone)
    EditText       findPhone;
    @BindView(R2.id.find_Validate_code)
    EditText       findValidateCode;
    @BindView(R2.id.btn_getValidateCode)
    Button         btnGetValidateCode;
    @BindView(R2.id.voice_remind)
    TextView       voiceRemind;
    @BindView(R2.id.find_password)
    EditText       findPassword;
    @BindView(R2.id.find_again_password)
    EditText       findAgainPassword;
    @BindView(R2.id.find_submit)
    Button         findSubmit;
    @BindView(R2.id.find_layout_main)
    LinearLayout   findLayoutMain;
    @BindView(R2.id.find_login_entry)
    Button         findLoginEntry;
    @BindView(R2.id.find_success_layout)
    LinearLayout   findSuccessLayout;

    private ForgetPasswordResponse forgetPasswordResponse;

    private String lRandomNumber;

    private int   timeLong = 90;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        initTitle();
    }

    private void initView() {
        DaggerForgetPasswordComponent.builder()
                .appComponent(getAppComponent())
                .forgetPasswordPresenterModule(new ForgetPasswordPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    /**
     * 初始化title
     */
    public void initTitle() {
        jkxTitleLeft.setVisibility(View.VISIBLE);
        jkxTitleCenter.setText(R.string.find_title);
    }

    @Override
    public void setResponseData(ForgetPasswordResponse forgetPasswordResponse) {
        this.forgetPasswordResponse = forgetPasswordResponse;
        try {
            String code = forgetPasswordResponse.getCode();
            String msg = forgetPasswordResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.find_password_success));
                boolean checked = PrefUtils.readCheckRemember(this.getApplicationContext());
                if (checked) {
                    //保存账号密码
                    PrefUtils.writeUserName(findPhone.getText().toString().trim(), this.getApplicationContext());
                    PrefUtils.writePassword(findAgainPassword.getText().toString().trim(), this.getApplicationContext());
                }

                findLayoutMain.setVisibility(View.GONE);
                findSuccessLayout.setVisibility(View.VISIBLE);
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

    @Override
    public void setCodeResponseData(RegisterCodeResponse registerCodeResponse) {
        try {
            String code = registerCodeResponse.getCode();
            String msg = registerCodeResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                countDown();
                ToastUtil.showToast(this.getApplicationContext(), "验证码发送成功");
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }
                btnGetValidateCode.setClickable(true);
            }


        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("forgetPasswordResponse", forgetPasswordResponse);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            ForgetPasswordResponse forgetPasswordResponse = (ForgetPasswordResponse) savedInstanceState.getSerializable("forgetPasswordResponse");
            this.forgetPasswordResponse = forgetPasswordResponse;

        }
    }

    @OnClick({R2.id.jkx_title_left, R2.id.btn_getValidateCode, R2.id.find_submit, R2.id.find_login_entry})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {
            finish();

        } else if (i == R.id.btn_getValidateCode) {

            String lPhone = findPhone.getText().toString().trim();
            String errorMsg = PhoneValidator.validate(lPhone);
            if (null != errorMsg) {
                ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
                return;
            }
            getValidateCodeRequest();

        } else if (i == R.id.find_submit) {
            findPasswordMethod();
        } else if (i == R.id.find_login_entry) {
            finish();
        }
    }

    private void findPasswordMethod() {
        String errorMsg = PhoneValidator.validate(findPhone.getText().toString()
                .trim());
        if (null != errorMsg) {
            ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
            return;
        }

        String lValidateCode = findValidateCode.getText().toString().trim();
        if (TextUtils.isEmpty(lValidateCode)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.verification_not_empty),
                    Toast.LENGTH_SHORT);
            return;
        }
        String lPassword = findPassword.getText().toString().trim();
        if (TextUtils.isEmpty(lPassword)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.password_not_empty), Toast.LENGTH_SHORT);
            return;
        }

        if (!StringUtils.isPasswordRegex(lPassword)) {

            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.password_character_restrict), Toast.LENGTH_SHORT);
            return;
        }

        String lAgainPassword = findAgainPassword.getText().toString().trim();
        if (TextUtils.isEmpty(lAgainPassword)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.confirm_password_not_empty),
                    Toast.LENGTH_SHORT);
            return;
        }
        if (!lPassword.equals(lAgainPassword)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.confirm_password_wrong),
                    Toast.LENGTH_SHORT);
            return;
        }

        /*
          retrofit 找回密码请求参数
         */
        Map<String, Object> mapParameters = new HashMap<>();
        mapParameters.put("MOBILE", findPhone.getText().toString().trim());
        mapParameters.put("RANDOM_NUMBER", lRandomNumber);
        mapParameters.put("CODE", lValidateCode);
        mapParameters.put("PASSWORD", lAgainPassword);


        Map<String, String> mapHeaders = new HashMap<>(1);
        mapHeaders.put("ACTION", "S007");
        //        mapHeaders.put("SESSION_ID", TaskManager.SESSION_ID);
        initData(mapHeaders, mapParameters);
    }

    public void initData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getRequestData(mapHeaders, mapParameters);
    }

    /**
     * 获取验证码
     */
    public void getValidateCodeRequest() {
        lRandomNumber = Tool.GetRandomNumber(3);
        // mValidateCode.setHint("序号 :" + lRandomNumber);
        btnGetValidateCode.setClickable(false);

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("MOBILE", findPhone.getText().toString().trim());
        mapParameters.put("RANDOM_NUMBER", lRandomNumber);
        mapParameters.put("TYPE", "2");

        Map<String, String> mapHeaders = new HashMap<>(1);
        mapHeaders.put("ACTION", "CM001");
        //        mapHeaders.put("SESSION_ID", TaskManager.SESSION_ID);

        initGetCodeData(mapHeaders, mapParameters);


    }

    private void initGetCodeData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getCodeRequestData(mapHeaders, mapParameters);
    }

    private final int COUNTDOWN = 3;
    HandlerFactory.OnMessageListener messageListener = new HandlerFactory.OnMessageListener() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case COUNTDOWN:
                    //                    btnGetValidateCode.setText(String.valueOf(msg.arg1) + "s");
                    btnGetValidateCode.setText(msg.arg1 + "s");
                    if (msg.arg1 == 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            findValidateCode.setHint(R.string.regist_verification_code);
                            btnGetValidateCode.setText(R.string.regist_get_verification_code);
                            btnGetValidateCode.setClickable(true);
                        }
                    }
                    break;
            }
        }
    };
    HandlerFactory.WeakHandler       mHandler        = HandlerFactory.buildWeakHandler(this, messageListener);

    /**
     * 倒计时器
     */
    public void countDown() {
        timeLong = 90;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message message = Message.obtain(mHandler);
                message.what = COUNTDOWN;
                message.arg1 = timeLong--;
                message.sendToTarget();
            }
        }, 1000, 1000);
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

        if (mTimer != null) {
            mTimer.cancel();

        }
    }
}
