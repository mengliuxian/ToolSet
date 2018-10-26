package com.flym.fhzk.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.flym.fhzk.R;

/**
 * @author Administrator
 * @date 2017/12/4 0004
 */

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull final Context context) {
        super(context, R.style.CustomDialog);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Activity activity = (Activity) context;
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //强制隐藏键盘
                    imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
    }


    public static Dialog HintNoTitleDialog(Context context, String hint, View.OnClickListener left, View.OnClickListener right) {

        final CustomDialog dialog = new CustomDialog(context);

        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_hint_no_title, null);
        if (left == null) {
            left = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
        }
        Button yes = (Button) view.findViewById(R.id.yes);
        Button no = (Button) view.findViewById(R.id.no);
        TextView hintText = (TextView) view.findViewById(R.id.hint);
        hintText.setText(hint);
        yes.setOnClickListener(right);
        no.setOnClickListener(left);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
        dialog.show();
        return dialog;
    }


}
