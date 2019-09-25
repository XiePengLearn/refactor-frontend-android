package com.sxjs.jd.composition.login.changephone;

import android.content.Intent;
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
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.HandlerFactory;
import com.sxjs.common.util.PhoneValidator;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.Tool;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.main.MainActivity;
import com.sxjs.jd.entities.ChangePhoneResponse;
import com.sxjs.jd.entities.RegisterCodeResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sxjs.common.constant.Constant.CONTENT_TITLE;

@Route(path = "/changePhone/changePhone")
public class ChangePhoneActivity extends BaseActivity implements ChangePhoneContract.View {

    @Inject
    ChangePhonePresenter presenter;
    @BindView(R2.id.fake_status_bar)
    View                 fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView             jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button               jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView             jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView             jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView             newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout       rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView             jkxTitleRight;
    @BindView(R2.id.checkout_phonenumber)
    EditText             checkoutPhonenumber;
    @BindView(R2.id.checkout_nextstep)
    Button               checkoutNextstep;
    @BindView(R2.id.checkout_eidtcode)
    EditText             checkoutEidtcode;
    @BindView(R2.id.checkout_identity)
    Button               checkoutIdentity;
    @BindView(R2.id.old_parent)
    LinearLayout         oldParent;
    @BindView(R2.id.voice_remind)
    TextView             voiceRemind;
    @BindView(R2.id.exim_phone)
    LinearLayout         eximPhone;
    @BindView(R2.id.phonenumber)
    EditText             phonenumber;
    @BindView(R2.id.nextstep)
    Button               nextstep;
    @BindView(R2.id.eidtcode)
    EditText             eidtcode;
    @BindView(R2.id.commit)
    Button               commit;
    @BindView(R2.id.commit_parent)
    LinearLayout         commitParent;
    @BindView(R2.id.voice_remind2)
    TextView             voiceRemind2;
    @BindView(R2.id.change_phone)
    LinearLayout         changePhone;
    @BindView(R2.id.change_after)
    LinearLayout         changeAfter;


    private String lRandomNumber;

    private int                 timeLong = 90;
    private Timer               mTimer;
    private ChangePhoneResponse changePhoneResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        initTitle();
    }

    private void initView() {
        DaggerChangePhoneComponent.builder()
                .appComponent(getAppComponent())
                .changePhonePresenterModule(new ChangePhonePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    /**
     * 初始化title
     */
    public void initTitle() {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(CONTENT_TITLE);

        jkxTitleLeft.setVisibility(View.VISIBLE);
        jkxTitleCenter.setText(stringExtra);
    }

    @Override
    public void setResponseData(ChangePhoneResponse changePhoneResponse) {
        this.changePhoneResponse = changePhoneResponse;
        try {
            String code = changePhoneResponse.getCode();
            String msg = changePhoneResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                ToastUtil.showToast(this.getApplicationContext(), "变更手机成功，请重新登录");
                boolean checked = PrefUtils.readCheckRemember(this.getApplicationContext());
                if (checked) {
                    //保存账号
                    PrefUtils.writeUserName(phonenumber.getText().toString().trim(), this.getApplicationContext());
                }
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();//关闭当前Activity
                MainActivity.instance.finish();//关闭MainActivity


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
                nextstep.setClickable(true);
            }


        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }


    private void changePhoneMethod() {
        String errorMsg = PhoneValidator.validate(phonenumber.getText().toString()
                .trim());
        if (null != errorMsg) {
            ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
            return;
        }

        String lValidateCode = eidtcode.getText().toString().trim();
        if (TextUtils.isEmpty(lValidateCode)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.verification_not_empty));
            return;
        }


        /*
          retrofit 找回密码请求参数
         */
//        lRandomNumber = Tool.GetRandomNumber(3);

        String session_id = PrefUtils.readSESSION_ID(this.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>();
        mapParameters.put("MOBILE", phonenumber.getText().toString().trim());
        mapParameters.put("RANDOM_NUMBER", lRandomNumber);
        mapParameters.put("CODE", lValidateCode);


        Map<String, String> mapHeaders = new HashMap<>(1);
        mapHeaders.put("ACTION", "S008");
        mapHeaders.put("SESSION_ID", session_id);
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
        nextstep.setClickable(false);

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("MOBILE", phonenumber.getText().toString().trim());
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
                    //                    nextstep.setText(String.valueOf(msg.arg1) + "s");
                    nextstep.setText(msg.arg1 + "s");
                    if (msg.arg1 == 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            eidtcode.setHint(R.string.regist_verification_code);
                            nextstep.setText(R.string.regist_get_verification_code);
                            nextstep.setClickable(true);
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

    @OnClick({R2.id.jkx_title_left, R2.id.nextstep, R2.id.commit})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {
            finish();
        } else if (i == R.id.nextstep) {
            String lPhone = phonenumber.getText().toString().trim();
            String errorMsg = PhoneValidator.validate(lPhone);
            if (null != errorMsg) {
                ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
                return;
            }
            getValidateCodeRequest();

        } else if (i == R.id.commit) {
            changePhoneMethod();

        }
    }


}
