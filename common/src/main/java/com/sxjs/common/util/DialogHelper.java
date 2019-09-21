package com.sxjs.common.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/10
 *     desc  : helper about dialog
 * </pre>
 */
public class DialogHelper {

    public static void showRationaleDialog(final ShouldRequest shouldRequest) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null || topActivity.isFinishing()) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(topActivity);
        builder.setTitle(android.R.string.dialog_alert_title)
                .setMessage("未得到授权请重新授权")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(true);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(false);
                    }
                })
                .setCancelable(false)
                .create();

        AlertDialog dialog = builder.show();
        Button btnPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnPos.setTextColor(Color.BLUE);
        btnNeg.setTextColor(Color.RED);
    }

    public static void showOpenAppSettingDialog() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null || topActivity.isFinishing()) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("请去设置页允许权限")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.launchAppDetailsSettings();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showAdaptScreenDialog() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null || topActivity.isFinishing()) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("Message!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

}
