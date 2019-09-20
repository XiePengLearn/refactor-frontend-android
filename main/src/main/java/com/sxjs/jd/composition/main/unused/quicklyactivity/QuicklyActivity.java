package com.sxjs.jd.composition.main.unused.quicklyactivity;

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
import com.sxjs.jd.composition.login.login.DaggerLoginActivityComponent;
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
@Route(path = "/quicklyActivity/quicklyActivity")
public class QuicklyActivity extends BaseActivity implements QuicklyContract.View {
    @Inject
    QuicklyPresenter presenter;



    private              String        mXinGeToken;
    private static final String        TAG                   = "NationExamActivity";
    private              Button        mLoginEntry;
    private              LoginResponse loginResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickly);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();


    }

    private void initView() {


        DaggerQuicklyActivityComponent.builder()
                .appComponent(getAppComponent())
                .quicklyPresenterModule(new QuicklyPresenterModule(this, MainDataManager.getInstance(mDataManager)))
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
}
