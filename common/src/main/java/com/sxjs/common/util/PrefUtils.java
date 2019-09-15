package com.sxjs.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Auther: xp
 * @Date: 2019/9/12 12:35
 * @Description:
 */
public class PrefUtils {
    private static final String SHARE_PREFS_NAME = "kpi_client";


    public static void writeUserName(String userName, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("userName", userName);
        editor.apply();
    }

    public static String readUserName(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("userName", "");
    }

    public static void writePassword(String password, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();
    }

    public static String readPassword(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("password", "");

    }

    public static void writeCheckRemember(boolean isCheckRemember, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean("isCheckRemember", isCheckRemember);
        editor.apply();
    }

    public static boolean readCheckRemember(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getBoolean("isCheckRemember",false);

    }

    //绩时查 token
    public static void writeSESSION_ID(String SESSION_ID, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("SESSION_ID", SESSION_ID);
        editor.apply();
    }

    public static String readSESSION_ID(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("SESSION_ID", "");

    }

    //绩时查 token
    public static void writeXinGeToken(String xinGeToken, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("xin_ge_token", xinGeToken);
        editor.apply();
    }

    public static String readXinGeToken(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("xin_ge_token", "");

    }

    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putString(key, value).apply();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getString(key, defaultValue);
    }

    public static void putInt(Context ctx, String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putInt(key, value).apply();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getInt(key, defaultValue);
    }


    //0没有绑定手机号  1已经绑定手机号
    public static void writeIsPhone(String isPhone, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("isPhone", isPhone);
        editor.apply();
    }

    public static String readIsPhone(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("isPhone", "");
    }

    public static void writeCity(String city, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("city", city);
        editor.apply();
    }

    public static String readCity(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("city", "");
    }

    public static void writeLongitude(String longitude, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("longitude", longitude);
        editor.apply();
    }

    public static String readLongitude(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("longitude", "");
    }

    public static void writeLatitude(String latitude, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("latitude", latitude);
        editor.apply();
    }

    public static String readLatitude(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("latitude", "");
    }


    //绩时查 UID
    public static void writeUid(String token, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("uid", token);
        editor.apply();
    }

    public static String readUid(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("uid", "");

    }

    //微信 头像

    public static void writeHeadImg(String token, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("head_img", token);
        editor.apply();
    }

    public static String readHeadImg(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("head_img", "");

    }


    //微信昵称
    public static void writeNiceName(String token, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("nick_name", token);
        editor.apply();
    }

    public static String readNiceName(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("nick_name", "");

    }

    //性别
    public static void writeGender(String token, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("gender", token);
        editor.apply();
    }

    public static String readGender(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("gender", "");

    }

    //写缓存
    public static void writeCacheDate(String key, String msg, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, msg);
        editor.apply();
    }

    //读缓存
    public static String readCacheDate(String key, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        String state = mySharedPreferences.getString(key, "");
        return state;
    }

    public static void writeDownloadUrl(String url, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("url", url);
        editor.apply();
    }

    public static String readDownloadUrl(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME, Activity.MODE_PRIVATE);
        String url = mySharedPreferences.getString("url", "");
        return url;
    }

}
