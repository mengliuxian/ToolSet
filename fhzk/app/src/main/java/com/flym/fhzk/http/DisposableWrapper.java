package com.flym.fhzk.http;

import android.util.Log;

import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.ToastUtil;
import com.flym.fhzk.utils.UserManager;
import com.flym.fhzk.view.LoadingDialog;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Morphine on 2017/6/19.
 */

public abstract class DisposableWrapper<T> extends DisposableSubscriber<T> {

    private static final String TAG = "DisposableWrapper";
    private LoadingDialog dialog;

    public DisposableWrapper() {

    }

    public DisposableWrapper(LoadingDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    abstract public void onNext(T t);

    @Override
    public void onError(Throwable t) {
        if (t instanceof ResponseError) {
            ResponseError responseError = (ResponseError) t;
            if ("100002".equals(responseError.getErrorCode()) ||
                    "100003".equals(responseError.getErrorCode()) ||
                    "100004".equals(responseError.getErrorCode())) {
                ToastUtil.showMessage(ActivityManager.getInstance().currentActivity(), "登录状态失效，请重新登录");
                UserManager.logout(ActivityManager.getInstance().currentActivity());
            }else {
                ToastUtil.showMessage(ActivityManager.getInstance().currentActivity(), t.getMessage());
            }
        } else  {
            ExceptionHandle.ResponeThrowable responeThrowable = ExceptionHandle.handleException(t);
            ToastUtil.showMessage(ActivityManager.getInstance().currentActivity(), responeThrowable.getMessage());
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
