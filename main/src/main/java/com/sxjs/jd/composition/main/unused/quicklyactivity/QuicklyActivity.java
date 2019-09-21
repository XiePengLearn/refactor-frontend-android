package com.sxjs.jd.composition.main.unused.quicklyactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.entities.LoginResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author xiepeng
 */
@Route(path = "/quicklyActivity/quicklyActivity")
public class QuicklyActivity extends BaseActivity implements QuicklyContract.View {
    @Inject
    QuicklyPresenter mPresenter;


    private              String        mXinGeToken;
    private static final String        TAG = "NationExamActivity";
    private              Button        mLoginEntry;
    private              LoginResponse loginResponse;
    private              String        mSession_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickly);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        DaggerQuicklyActivityComponent.builder()
                .appComponent(getAppComponent())
                .quicklyPresenterModule(new QuicklyPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    private void initData() {

        mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(1);
//        mapParameters.put("ACTION", "I002");
        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I002");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        try {
            String code = loginResponse.getCode();
            String msg = loginResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {


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
        if (mPresenter != null) {
            mPresenter.destory();
        }
    }
}
