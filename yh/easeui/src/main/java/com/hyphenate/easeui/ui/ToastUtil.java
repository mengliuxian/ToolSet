package com.hyphenate.easeui.ui;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Morphine on 2017/2/24.
 */

public class ToastUtil {

    private static Toast toast = null;
    private static Object synObj = new Object();


    public static void showMessage(Context context, String msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessageGravity(Context context, String msg, int gravity) {
        showMessage(context, msg, Toast.LENGTH_SHORT, gravity);
    }

    public static void showMessage(Context context, double msg) {
        showMessage(context, msg + "", Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context context, boolean msg) {
        showMessage(context, msg + "", Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context context, float msg) {
        showMessage(context, msg + "", Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context context, int msg) {
        showMessage(context, msg + "", Toast.LENGTH_SHORT);
    }

    private static void showMessage(Context context, String msg, int duration) {
//        cancelToast();
        if (context == null) {
            return;
        }
        if (toast != null) {
            toast.setText(msg);
        } else {
            toast = Toast.makeText(context, msg, duration);
        }
        try {
            toast.show();
        } catch (Exception e) {
            Log.e("tag", "showMessage: " + e.getMessage());
        }
    }

    private static void showMessage(Context context, String msg, int duration, int gravity) {
        cancelToast();
        if (context == null) {
            return;
        }

        toast = Toast.makeText(context, msg, duration);
        toast.setGravity(gravity, 0, 0);

        try {
            toast.show();
        } catch (Exception e) {
            Log.e("tag", "showMessage: " + e.getMessage());
        }
    }

    private static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
