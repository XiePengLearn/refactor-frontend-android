package com.sxjs.common.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @Auther: xp
 * @Date: 2019/9/15 08:37
 * @Description:
 */
public class HandlerFactory {
    public static class WeakHandler extends Handler {

        private WeakReference<Object> reference = null;
        private OnMessageListener onMessageListener;

        public void setOnMessageListener(OnMessageListener onMessageListener) {
            this.onMessageListener = onMessageListener;
        }

        private WeakHandler(Object obj) {
            reference = new WeakReference<Object>(obj);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object act = reference.get();
            if (act == null) {
                return;
            }
            if (onMessageListener != null) {
                onMessageListener.handleMessage(msg);
            }
        }
    }

    public interface OnMessageListener {
        void handleMessage(Message msg);
    }

    public static WeakHandler buildWeakHandler(Object act, OnMessageListener listener) {
        WeakHandler weakHandler = new WeakHandler(act);
        weakHandler.setOnMessageListener(listener);
        return weakHandler;
    }

    public static WeakHandler buildWeakHandler(Object act) {
        return new WeakHandler(act);
    }
}
