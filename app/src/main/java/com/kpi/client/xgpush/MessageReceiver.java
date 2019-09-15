package com.kpi.client.xgpush;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sxjs.common.constant.Constant;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class MessageReceiver extends XGPushBaseReceiver {

    private void updataSystemMessageCount(Context context, String customContent) {


        SharedPreferences sp = context.getApplicationContext().
                getSharedPreferences(Constant.JIXIAO_AUTHORIZATION, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Constant.JIXIAO_AUTHORIZATION, "1");  //值为1时标识页面刷新来自于推送引起 为0时标识来自普通的activity声明周期引起
        edit.apply();
        Intent intent = new Intent("com.jkx.message");
        // 消息通知的广播名称
        context.sendBroadcast(intent);
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                if (!obj.isNull("msgCount")) {// 服务器或前台配置的key
                    String value = obj.getString("msgCount").trim();

                    if (value.length() > 0) {
                     /*   MessageData messageData = (MessageData) DataSaveManager.getInstance().getSaveData(SaveTask.KEY_MESSAGE);
                        if (!obj.isNull("isOpen")) {
                            String isOpen = obj.getString("isOpen").trim();
                            messageData.setIsOpen(isOpen);
                        }
                        messageData.setNewMessage(true);
                        SaveTask saveTask = new SaveTask();
                        saveTask.setmTaskKey(SaveTask.KEY_MESSAGE);
                        //lMsgData.setmMessageSize(Integer.valueOf(value));
                        saveTask.setmData(messageData);
                        DataSaveManager.getInstance().addSaveTask(saveTask);*/

                        // Intent intent = new Intent("com.jkx.message");
                        // 消息通知的广播名称
                        // context.sendBroadcast(intent);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDeleteTagResult(Context context, int arg1, String arg2) {
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
        /**
         * 暂时注释   JkxContentActivity.isMsg = true;
         */
//        JkxContentActivity.isMsg = true;
        updataSystemMessageCount(context, notifiShowedRlt.getCustomContent());
    }

    @Override
    public void onRegisterResult(Context context, int arg1, XGPushRegisterResult arg2) {

    }

    @Override
    public void onSetTagResult(Context context, int arg1, String arg2) {
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        if (context == null || message == null) {
            return;
        }
        updataSystemMessageCount(context, message.getCustomContent());
    }

    @Override
    public void onUnregisterResult(Context context, int arg1) {
    }

}
