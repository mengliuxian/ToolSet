package com.flym.yh.net;

import android.util.Log;


import com.flym.yh.MyApplication;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.LogUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Administrator
 * @date 2018/2/8 0008
 */

class HttpLogger implements HttpLoggingInterceptor.Logger {
    private StringBuilder mMessage = new StringBuilder();
    private LoadingDialog show;

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
//            ActivityManager.getInstance().currentActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (show != null) {
//                        show.dismiss();
//                    }
//                    show = LoadingDialog.show(ActivityManager.getInstance().currentActivity());
//                }
//            });
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
//            ActivityManager.getInstance().currentActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (show != null) {
//                        show.dismiss();
//                    }
//                }
//            });
            if (LogUtils.APP_DBG) {
                Log.e("okHttp", mMessage.toString());
            }
        }
    }
}
