package com.sxjs.jd.composition.login.register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.HandlerFactory;
import com.sxjs.common.util.PhoneValidator;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.StringUtils;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.Tool;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.common.view.SelectPicPopupWindow;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.RegisterCodeResponse;
import com.sxjs.jd.entities.RegisterResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/register/register")
public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @Inject
    RegisterPresenter presenter;

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
    @BindView(R2.id.reg_realName)
    EditText       regRealName;
    @BindView(R2.id.tv_sex)
    TextView       tvSex;
    @BindView(R2.id.reg_idnumber)
    EditText       regIdnumber;
    @BindView(R2.id.reg_password)
    EditText       regPassword;
    @BindView(R2.id.reg_again_password)
    EditText       regAgainPassword;
    @BindView(R2.id.reg_phone)
    EditText       regPhone;
    @BindView(R2.id.reg_verification_code)
    EditText       regVerificationCode;
    @BindView(R2.id.btn_getValidateCode)
    Button         btnGetValidateCode;
    @BindView(R2.id.voice_remind)
    TextView       voiceRemind;
    @BindView(R2.id.reg_agree_terms)
    CheckBox       regAgreeTerms;
    @BindView(R2.id.reg_agree_terms_textview)
    TextView       regAgreeTermsTextview;
    @BindView(R2.id.reg_btn_register)
    Button         regBtnRegister;
    @BindView(R2.id.tv_notReceiveSecurityCode)
    TextView       tvNotReceiveSecurityCode;
    @BindView(R2.id.ll_register_root_view)
    LinearLayout   llRegisterRootView;

    private RegisterResponse registerResponse;
    private String           lRandomNumber;

    private int   timeLong = 90;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        initTitle();
    }

    private void initView() {
        DaggerRegisterActivityComponent.builder()
                .appComponent(getAppComponent())
                .registerPresenterModule(new RegisterPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    /**
     * 初始化title
     */
    public void initTitle() {
        jkxTitleLeft.setVisibility(View.VISIBLE);
        jkxTitleCenter.setText(R.string.regist_title);
    }

    @Override
    public void setResponseData(RegisterResponse registerResponse) {
        this.registerResponse = registerResponse;
        try {
            String code = registerResponse.getCode();
            String msg = registerResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.Registered_successfully));
                finish();
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
        outState.putSerializable("registerResponse", registerResponse);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            RegisterResponse registerResponse = (RegisterResponse) savedInstanceState.getSerializable("registerResponse");
            this.registerResponse = registerResponse;

        }
    }


    @OnClick({R2.id.jkx_title_left, R2.id.btn_getValidateCode, R2.id.reg_btn_register,
            R2.id.tv_sex
    })
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {

            finish();

        } else if (i == R.id.btn_getValidateCode) {
            String lPhone = regPhone.getText().toString().trim();
            String errorMsg = PhoneValidator.validate(lPhone);
            if (null != errorMsg) {
                ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
                return;
            }
            getValidateCodeRequest();

        } else if (i == R.id.reg_btn_register) {

            //注册
            RegisterMethod();

        } else if (i == R.id.tv_sex) {
            KeyboardUtils.hideSoftInput(this);
            SelectPicPopupWindow popupWindow = new SelectPicPopupWindow(mContext, llRegisterRootView);
            popupWindow.setData("男", "女", null);
            popupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {
                    tvSex.setText(str);
                }
            });
            popupWindow.showPopWindow();
        }
    }

    private void RegisterMethod() {
        String lRealName = regRealName.getText().toString().trim();
        if (TextUtils.isEmpty(lRealName)) {
            ToastUtil.showToast(mContext, "请填写本人真实姓名", Toast.LENGTH_SHORT);
            return;
        }
        if (com.blankj.utilcode.util.StringUtils.isEmpty(tvSex.getText())) {
            ToastUtils.showLong("请选择性别");
            return;
        }
        //        String lIdNumber = mIdNumber.getText().toString().trim();
        //        if (TextUtils.isEmpty(lIdNumber)) {
        //            ToastUtil.showToast(
        //                    mContext,
        //                    mContext.getResources().getString(
        //                            R.string.sf_card_not_empty), Toast.LENGTH_SHORT);
        //            return;
        //        }

        //boolean flag = new IdCardValidator().isValidatedAllIdcard(lIdNumber);
        //        String flag = IDCardValidateUtils.IDCardValidate(lIdNumber.toLowerCase());
        //        if (!"".equals(flag)) {
        //            ToastUtil.showToast(
        //                    mContext,
        //                    mContext.getResources().getString(
        //                            R.string.sf_card_format_wrong), Toast.LENGTH_SHORT);
        //            return;
        //        }

        String lPassword = regPassword.getText().toString().trim();
        if (TextUtils.isEmpty(lPassword)) {
            ToastUtil.showToast(
                    mContext,
                    "请填写密码（密码长度6-16个字符，要包含字母和数字）", Toast.LENGTH_SHORT);
            return;
        }
        if (!StringUtils.isPasswordRegex(lPassword)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.password_character_restrict), Toast.LENGTH_SHORT);
            return;
        }
        String lAgainPassword = regAgainPassword.getText().toString().trim();
        if (!lPassword.equals(lAgainPassword)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.password_not_same), Toast.LENGTH_SHORT);
            return;
        }
        String errorMsg = PhoneValidator.validate(regPhone.getText().toString()
                .trim());
        if (null != errorMsg) {
            ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
            return;
        }

        String lValidateCode = regVerificationCode.getText().toString().trim();
        if (null == lValidateCode || lValidateCode.length() == 0) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.verification_not_empty),
                    Toast.LENGTH_SHORT);
            return;
        }


        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("NAME", lRealName);
        mapParameters.put("SEX", "男".equals(tvSex.getText().toString()) ? "1" : "2");
        mapParameters.put("MOBILE", regPhone.getText().toString().trim());
        mapParameters.put("PASSWORD", lAgainPassword);
        mapParameters.put("RANDOM_NUMBER", lRandomNumber);
        mapParameters.put("CODE", lValidateCode);

        Map<String, String> mapHeaders = new HashMap<>(1);
        mapHeaders.put("ACTION", "S001");
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

        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("MOBILE", regPhone.getText().toString().trim());
        mapParameters.put("RANDOM_NUMBER", lRandomNumber);
        mapParameters.put("TYPE", "1");

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
                            regVerificationCode.setHint(R.string.regist_verification_code);
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
