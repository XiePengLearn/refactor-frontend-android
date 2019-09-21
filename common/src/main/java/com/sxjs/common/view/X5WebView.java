package com.sxjs.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {
    public OnScrollChangeListener listener;

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        /**
         * 防止加载网页时调起系统浏览器
         */
        // 解决7.0以上部分机型打不开webview问题
        // 证书的日期是无效的
        // 证书已经过期
        //一个通用的错误发生
        //不受信任的证书颁发机构
        WebViewClient client = new WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                // 解决7.0以上部分机型打不开webview问题
                if (sslError.getPrimaryError() == android.net.http.SslError.SSL_DATE_INVALID// 证书的日期是无效的
                        || sslError.getPrimaryError() == android.net.http.SslError.SSL_EXPIRED // 证书已经过期
                        || sslError.getPrimaryError() == android.net.http.SslError.SSL_INVALID  //一个通用的错误发生
                        || sslError.getPrimaryError() == android.net.http.SslError.SSL_UNTRUSTED //不受信任的证书颁发机构
                ) {
                    sslErrorHandler.proceed();
                } else {
                    sslErrorHandler.cancel();
                }

                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                if (errorResponse.getStatusCode() == 404) {
                    if (errorCallBackListener != null) {
                        // errorCallBackListener.error();
                    }
                }

            }

        };
        this.setWebViewClient(client);
        if (this.getX5WebViewExtension() != null) {
            this.getX5WebViewExtension().setScrollBarFadingEnabled(false); //隐藏滚动条
        }
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.getView().setClickable(true);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true); //js脚本自动打开窗口
        webSetting.setAllowFileAccess(true); //允许文件访问
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS); //算法格式
        webSetting.setSupportZoom(true); //支持缩放
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true); //使用宽视图窗口
        webSetting.setSupportMultipleWindows(true); // 支持多个窗口
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true); //应用程序缓存启用
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(false); //地理位置
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//页面缓存容量
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    private ErrorCallBackListener errorCallBackListener;

    public void setErrorCallBackListener(ErrorCallBackListener errorCallBackListener) {
        this.errorCallBackListener = errorCallBackListener;
    }

    public interface ErrorCallBackListener {
        void error();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        super.onScrollChanged(l, t, oldl, oldt);

        float webcontent = getContentHeight() * getScale();// webview的高度
        float webnow = getHeight() + this.getScrollY();// 当前webview的高度
        if (Math.abs(webcontent - webnow) < 1) {
            // 已经处于底端
            // Log.i("TAG1", "已经处于底端");
            listener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            // Log.i("TAG1", "已经处于顶端");
            listener.onPageTop(l, t, oldl, oldt);
        } else {
            listener.onScrollChanged(l, t, oldl, oldt);
        }

    }

    public void setOnScrollChangeListenerToTop(OnScrollChangeListener listener) {

        this.listener = listener;

    }

    public interface OnScrollChangeListener {
        void onPageEnd(int l, int t, int oldl, int oldt);

        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }

}
