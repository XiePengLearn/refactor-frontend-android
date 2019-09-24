package com.kpi.client;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.app_common.CommonModule;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

/**
 * @author admin
 */
public class MyApplication extends Application {
    private static Context       mContext;
    private static MyApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        CommonModule.init(this);


        //初始化信鸽
        registPushXG();

        //初始化x5内核
        initX5webview();


    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    //信鸽初始化
    private void registPushXG() {

        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761518121760");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "5461812190760");
        //XGPushConfig.setMzPushAppId(this, "APPID");
        //XGPushConfig.setMzPushAppKey(this, "APPKEY");

        XGPushConfig.enableDebug(this, true);
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        //token在设备卸载重装的时候有可能会变
                        Log.d("TPush", "注册成功，设备token为：" + data);
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                    }
                });

    }

    //初始化X5内核
    private void initX5webview() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };


        HashMap<String, Object> map = new HashMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);

        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

    }
}
