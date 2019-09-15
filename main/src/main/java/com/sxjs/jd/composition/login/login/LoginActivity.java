package com.sxjs.jd.composition.login.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.app_common.service.ITokenService;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.common.view.ClearEditText;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.LoginResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/login/login")
public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginPresenter presenter;


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
    @BindView(R2.id.edit_account)
    ClearEditText  editAccount;
    @BindView(R2.id.edit_password)
    ClearEditText  editPassword;
    @BindView(R2.id.login_remember_passwords)
    CheckBox       loginRememberPasswords;
    @BindView(R2.id.login_find_password)
    TextView       loginFindPassword;
    @BindView(R2.id.login_entry)
    Button         loginEntry;
    @BindView(R2.id.register)
    TextView       register;
    private              String        mXinGeToken;
    private static final String        TAG                   = "LoginActivity";
    private              Button        mLoginEntry;
    private              LoginResponse loginResponse;
    private              boolean       isClickForgetPassword = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();


    }

    private void initView() {


        DaggerLoginActivityComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        editAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editAccount.setClearIconVisible(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editPassword.setClearIconVisible(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        String lUserName = PrefUtils.readUserName(this.getApplicationContext());

        if (!TextUtils.isEmpty(lUserName)) {
            editAccount.setText(lUserName.trim());
            editAccount.setSelection(lUserName.length());// 获得焦点
        } else {
            editAccount.setText("");
        }

        String mPassword = PrefUtils.readPassword(this.getApplicationContext());

        if (!TextUtils.isEmpty(mPassword)) {
            editPassword.setText(mPassword.trim());
            editPassword.setSelection(mPassword.length());// 获得焦点
        } else {
            editPassword.setText("");
        }

        loginRememberPasswords.setChecked(PrefUtils.readCheckRemember(this.getApplicationContext()));


    }

    public void initData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getLoginData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isClickForgetPassword) {
            isClickForgetPassword = false;
            String phone = PrefUtils.readUserName(this.getApplicationContext());
            String password = PrefUtils.readPassword(this.getApplicationContext());
            boolean checked = PrefUtils.readCheckRemember(this.getApplicationContext());

            if (checked) {
                if (!TextUtils.isEmpty(phone)) {
                    editAccount.setText(phone);
                }
                if (!TextUtils.isEmpty(password)) {
                    editPassword.setText(password);

                }
            }
        }


    }


    @Override
    public void setLoginData(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        try {
            String code = loginResponse.getCode();
            String msg = loginResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + loginResponse.getData());
                boolean checked = loginRememberPasswords.isChecked();
                String SESSION_ID = loginResponse.getData();
                if (checked) {
                    //保存账号密码   储存状态 SESSION_ID
                    PrefUtils.writeUserName(editAccount.getText().toString().trim(), this.getApplicationContext());
                    PrefUtils.writePassword(editPassword.getText().toString().trim(), this.getApplicationContext());
                    PrefUtils.writeCheckRemember(true, this.getApplicationContext());
                    PrefUtils.writeSESSION_ID(SESSION_ID, this.getApplicationContext());

                } else {
                    //保存账号密码   储存状态 SESSION_ID
                    PrefUtils.writeUserName("", this.getApplicationContext());
                    PrefUtils.writePassword("", this.getApplicationContext());
                    PrefUtils.writeCheckRemember(false, this.getApplicationContext());
                    PrefUtils.writeSESSION_ID(SESSION_ID, this.getApplicationContext());
                }

                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
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

    @OnClick({R2.id.jkx_title_left_btn, R2.id.edit_account, R2.id.edit_password,
            R2.id.login_remember_passwords, R2.id.login_find_password, R2.id.login_entry, R2.id.register})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left_btn) {

        } else if (i == R.id.edit_account) {
            String editAccountData = editAccount.getText().toString().trim();

            editAccount.setSelection(editAccountData.length());// 获得焦点
        } else if (i == R.id.edit_password) {
            String editPasswordData = editPassword.getText().toString().trim();

            editPassword.setSelection(editPasswordData.length());// 获得焦点
        } else if (i == R.id.login_remember_passwords) {

        } else if (i == R.id.login_find_password) {
            //忘记密码
            isClickForgetPassword = true;
            ARouter.getInstance().build("/forget/forget").greenChannel().navigation(this);

        } else if (i == R.id.login_entry) {
            //登录
            LoginMethod();
        } else if (i == R.id.register) {
            ARouter.getInstance().build("/register/register").greenChannel().navigation(this);
        }
    }

    private void LoginMethod() {
        String lAccount = editAccount.getText().toString().trim();

        if (TextUtils.isEmpty(lAccount)) {

            ToastUtil.showToast(mContext, mContext.getResources().getString(
                    R.string.username_not_empty), Toast.LENGTH_SHORT);
            return;
        }

        String lPassword = editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(lPassword)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.password_not_empty), Toast.LENGTH_SHORT);
            return;
        }

        //密码加密 并没有用到 我给注释了
        //                String lPasswordMD5 = Tool.encryption2(lPassword);

        ITokenService service = ARouter.getInstance().navigation(ITokenService.class);
        if (service != null) {
            mXinGeToken = service.getToken();

        } else {
            Toast.makeText(this, "该服务所在模块未参加编译", Toast.LENGTH_LONG).show();
        }

        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("MOBILE", lAccount);
        mapParameters.put("PASSWORD", lPassword);
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
}
