package com.kpi.client;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.app_common.CommonModule;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

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
}
