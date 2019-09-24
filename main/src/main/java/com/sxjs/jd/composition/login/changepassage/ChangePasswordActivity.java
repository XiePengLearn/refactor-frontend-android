package com.sxjs.jd.composition.login.changepassage;

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
import com.sxjs.common.util.StringUtils;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.Tool;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.main.MainActivity;
import com.sxjs.jd.entities.ChangePasswordResponse;
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

import static com.sxjs.common.constant.Constant.CONTENT_TITLE;
import static com.sxjs.jd.composition.main.MainActivity.instance;

@Route(path = "/changePassword/changePassword")
public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract.View {

    @Inject
    ChangePasswordPresenter presenter;
    @BindView(R2.id.fake_status_bar)
    View                    fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView                jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button                  jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView                jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView                jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView                newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout          rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView                jkxTitleRight;
    @BindView(R2.id.old_password)
    EditText                oldPassword;
    @BindView(R2.id.new_password)
    EditText                newPassword;
    @BindView(R2.id.again_password)
    EditText                againPassword;
    @BindView(R2.id.c_password)
    LinearLayout            cPassword;
    @BindView(R2.id.commit_password)
    Button                  commitPassword;
    @BindView(R2.id.again_succces)
    TextView                againSuccces;


    private ChangePasswordResponse changePasswordResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        initTitle();
    }

    private void initView() {
        DaggerChangePasswordComponent.builder()
                .appComponent(getAppComponent())
                .changePasswordPresenterModule(new ChangePasswordPresenterModule(this, MainDataManager.getInstance(mDataManager)))
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
    public void setResponseData(ChangePasswordResponse changePasswordResponse) {
        this.changePasswordResponse = changePasswordResponse;
        try {
            String code = changePasswordResponse.getCode();
            String msg = changePasswordResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                ToastUtil.showToast(this.getApplicationContext(), "密码修改成功，请重新登录");
                boolean checked = PrefUtils.readCheckRemember(this.getApplicationContext());
                if (checked) {
                    //保存账号密码
                    PrefUtils.writePassword(againPassword.getText().toString().trim(), this.getApplicationContext());
                }
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();//关闭当前Activity
                MainActivity.instance.finish();//关闭MainActivity
                //                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }


    @OnClick({R2.id.jkx_title_left, R2.id.commit_password, R2.id.again_succces})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {
            finish();

        } else if (i == R.id.commit_password) {
            commitChangePasswordMethod();

        } else if (i == R.id.again_succces) {


        }
    }

    private void commitChangePasswordMethod() {



        String lOldPsw = oldPassword.getText().toString().trim();
        if (TextUtils.isEmpty(lOldPsw)) {
            ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.input_old_password));
            return;
        }

        String lNewPsw = newPassword.getText().toString().trim();

        if (TextUtils.isEmpty(lNewPsw)) {
            ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.input_new_password));
            return;

        }

        if (lOldPsw.equals(lNewPsw)) {

            ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.new_and_old_password_same));
            return;

        }

        if (!StringUtils.isPasswordRegex(lNewPsw)) {
            ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.password_character_restrict));
            return;
        }

        String lConfirPsw = againPassword.getText().toString().trim();

        if (!lNewPsw.equals(lConfirPsw)) {
            ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.new_and_old_not_same));
            return;
        }


        /*
           修改密码请求参数
         */
        //        OLD_PASSWORD	旧密码	字符型
        //        NEW_PASSWORD	新密码	字符型
        String session_id = PrefUtils.readSESSION_ID(this.getApplicationContext());


        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("OLD_PASSWORD", lOldPsw);
        mapParameters.put("NEW_PASSWORD", lNewPsw);


        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "S006");
        mapHeaders.put("SESSION_ID", session_id);
        initData(mapHeaders, mapParameters);
    }



    public void initData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getRequestData(mapHeaders, mapParameters);
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
