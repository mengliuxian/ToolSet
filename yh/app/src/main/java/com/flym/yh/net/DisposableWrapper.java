package com.flym.yh.net;

import android.util.Log;


import com.flym.yh.MyApplication;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.LogUtils;
import com.flym.yh.utils.ToastUtil;
import com.flym.yh.utils.UserManager;

import org.reactivestreams.Subscription;

import io.reactivex.internal.util.EndConsumerHelper;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Morphine on 2017/6/19.
 */

public abstract class DisposableWrapper<T> extends DisposableSubscriber<T> {

    public static boolean showDialog = true; //控制显示dialog
    private static final String TAG = "DisposableWrapper";
    private LoadingDialog dialog;

    public DisposableWrapper() {
        if (showDialog) {
            dialog = LoadingDialog.show(ActivityManager.getInstance().currentActivity());
        }
    }


    public DisposableWrapper(LoadingDialog dialog) {
        this.dialog = dialog;
    }


    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable t) {
        if (t instanceof ResponseError) {
            ResponseError responseError = (ResponseError) t;
            if ("100020".equals(responseError.getErrorCode()) ||
                    "100030".equals(responseError.getErrorCode()) ||
                    "100021".equals(responseError.getErrorCode()) ||
//                    "100000".equals(responseError.getErrorCode()) ||
                    "100040".equals(responseError.getErrorCode())) {
                ToastUtil.showMessage(ActivityManager.getInstance().currentActivity(), t.getMessage());
                UserManager.logout(ActivityManager.getInstance().currentActivity());
            } else {
                String message = responseError.getMessage();
                String last = message.substring(message.length() - 1, message.length());
                if ("1".equals(last)) {
                    ToastUtil.showMessage(MyApplication.mContext, responseError.getMessage());
                } else if ("0".equals(last)) {
                    if (LogUtils.APP_DBG) {
                        ToastUtil.showMessage(MyApplication.mContext, t.getMessage());
                    }
                }
            }
        } else {
            ExceptionHandle.ResponeThrowable responeThrowable = ExceptionHandle.handleException(t);
            ToastUtil.showMessage(ActivityManager.getInstance().currentActivity(), responeThrowable.message);
        }
        Log.e(TAG, "onError: " + t.getMessage());
        onFinish();
    }


    @Override
    public void onComplete() {
        onFinish();
    }

    public void onFinish() {
        if (dialog != null) {
            dialog.dismiss();
        }

    }
}
