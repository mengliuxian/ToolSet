package com.flym.fhzk.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.flym.fhzk.R;


/**
 * Created by Morphine on 2017/2/20.
 */

public class LoadingDialog extends Dialog {

    private TextView mMessage;

    private LoadingDialog(Context context) {
        super(context, R.style.ProgressDialogStyle);
        initDialog();
    }

    public static LoadingDialog show(Context context) {
        return show(context, context.getString(R.string.loading_text));
    }

    public static LoadingDialog show(Context context, String msg) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        if (!TextUtils.isEmpty(msg)) {
            loadingDialog.setMessage(msg);
        }
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        return loadingDialog;
    }

    public static LoadingDialog show(Context context, String msg, boolean isAble) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        if (!TextUtils.isEmpty(msg)) {
            loadingDialog.setMessage(msg);
        } else {
            loadingDialog.setVisity();
        }
        loadingDialog.setCancelable(isAble);
        loadingDialog.show();
        return loadingDialog;
    }

    private void initDialog() {
        this.setContentView(R.layout.dialog_loading);
        setCancelable(false);
        mMessage = (TextView) findViewById(R.id.tv_message);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    public void setMessage(String message) {
        this.mMessage.setText(message);
    }

    public void setVisity() {
        this.mMessage.setVisibility(View.GONE);
    }

}
