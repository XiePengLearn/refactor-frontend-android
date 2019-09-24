package com.sxjs.common.util;

/**
 * @Auther: xp
 * @Date: 2019/9/23 16:37
 * @Description: 防止按钮2连续多次点击
 */
public class NoDoubleClickUtils {

    private final static int SPACE_TIME = 500;//2次点击的间隔时间，单位ms
    private static long lastClickTime;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick = false;
        } else {
            isClick = true;
        }
        lastClickTime = currentTime;
        return isClick;
    }
}
