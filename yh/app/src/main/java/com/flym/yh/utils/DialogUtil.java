package com.flym.yh.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Created by Morphine on 2017/4/28.
 */

public class DialogUtil {
    public static void showSimpleDialog(Context context, String title, String msg, String positiveText,
                                        DialogInterface.OnClickListener positiveClick,
                                        String negativeText, DialogInterface.OnClickListener negativeClick) {

        showSimpleDialog(context, true, title, msg, positiveText, positiveClick, negativeText, negativeClick);
    }

    public static void showSimpleDialog(Context context, boolean cancelable, String msg, String positiveText,
                                        DialogInterface.OnClickListener positiveClick,
                                        String negativeText, DialogInterface.OnClickListener negativeClick) {

        showSimpleDialog(context, cancelable, null, msg, positiveText, positiveClick, negativeText, negativeClick);
    }

    public static void showSimpleDialog(Context context, boolean cancelable, String title, String msg, String positiveText,
                                        DialogInterface.OnClickListener positiveClick,
                                        String negativeText, DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setPositiveButton(positiveText, positiveClick);
        builder.setNegativeButton(negativeText, negativeClick != null ? negativeClick : new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.show();
    }
}
