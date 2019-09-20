package com.sxjs.jd.composition.html.messagedetails;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.common.view.X5WebView;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageWebViewActivity extends BaseActivity {

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
    @BindView(R2.id.progressBar)
    ProgressBar    progressBar;
    @BindView(R2.id.webView)
    X5WebView      webView;


    private static final String TAG = "MessageWebViewActivity";
    private              Intent mIntent;
    private              String mWebUrl;
    private              String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_webview);

        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mIntent = getIntent();
        mWebUrl = mIntent.getStringExtra("url");

        LogUtil.e(TAG,"========mWebUrl========="+mWebUrl);
        if(TextUtils.isEmpty(mWebUrl)||!mWebUrl.startsWith("http")){
            webView.setVisibility(View.GONE);
        }else {
            webView.setVisibility(View.VISIBLE);
        }
        mTitle = mIntent.getStringExtra("title");
        initTitle(mTitle);
        initView();
    }

    private void initView() {

        webView.loadUrl(mWebUrl);
        MyWebChromClient webChromClient = new MyWebChromClient();
        webView.setWebChromeClient(webChromClient);


    }

    private void initTitle(String title) {

        if (!TextUtils.isEmpty(title)) {
            jkxTitleCenter.setText(title);
        } else {
            jkxTitleCenter.setText("详情");
        }


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
            } else {
//                jkxTitleCenter.setText(title);
            }
        }
    }

    @Override
    protected void onDestroy() {



        try {
            if (webView != null) {

                ViewParent parent = webView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(webView);
                }
                webView.removeAllViews();
                webView.destroy();
                webView = null;

//                webView.stopLoading();
//                webView.removeAllViewsInLayout();
//                webView.removeAllViews();
//                webView.setWebViewClient(null);
//                CookieSyncManager.getInstance().stopSync();
//                webView.destroy();
//                webView = null;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            super.onDestroy();
        }
    }

}
