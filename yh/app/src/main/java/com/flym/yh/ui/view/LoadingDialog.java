package com.flym.yh.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.ui.fragment.mine.MineFragment;

import java.lang.ref.WeakReference;


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

    public static LoadingDialog show(Context context, boolean is) {
        return show(context, context.getString(R.string.loading_text), is);
    }

    public static LoadingDialog show(Context context, String msg) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        if (!TextUtils.isEmpty(msg)) {
            loadingDialog.setMessage(msg);
        }
        loadingDialog.setCancelable(false);
        WindowManager.LayoutParams attributes = loadingDialog.getWindow().getAttributes();
        Window window = loadingDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
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
        WindowManager.LayoutParams attributes = loadingDialog.getWindow().getAttributes();
        Window window = loadingDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
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
