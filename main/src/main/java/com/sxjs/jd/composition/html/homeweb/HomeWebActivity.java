package com.sxjs.jd.composition.html.homeweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.StringUtils;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.common.view.X5WebView;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.LoginResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/homeWebActivity/homeWebActivity")
public class HomeWebActivity extends BaseActivity implements HomeWebContract.View {
    @Inject
    HomeWebPresenter presenter;
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
    @BindView(R2.id.progressBar)
    ProgressBar      progressBar;
    @BindView(R2.id.webView)
    X5WebView        webView;


    private              String        mXinGeToken;
    private static final String        TAG = "MessageActivity";
    private              Button        mLoginEntry;
    private              LoginResponse loginResponse;
    private Intent mIntent;
    private String mWebUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_web);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        initTitle();
        initView();


    }

    private void initView() {


        DaggerHomeWebActivityComponent.builder()
                .appComponent(getAppComponent())
                .homeWebPresenterModule(new HomeWebPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        mIntent = getIntent();

        mWebUrl = mIntent.getStringExtra("url");
        webView.loadUrl(mWebUrl);
        MyWebChromClient webChromClient = new MyWebChromClient();
        webView.setWebChromeClient(webChromClient);


    }
    private void initTitle() {

        jkxTitleCenter.setText("详情");

        jkxTitleLeft.setVisibility(View.VISIBLE);

    }

    class MyWebChromClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title.contains("404")) {
                webView.setVisibility(View.GONE);
            }else {
                jkxTitleCenter.setText(title);
            }
        }
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
            goBack();

        } else if (i == R.id.jkx_title_left_btn) {


        }
    }
    public void goBack() {
        if (webView.canGoBack())
            webView.goBack();
        else
           finish();
    }
}
