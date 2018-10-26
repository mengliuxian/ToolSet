package com.flym.fhzk.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Morphine on 2017/4/26.
 */

public abstract class BasePopupWindow extends PopupWindow {
    protected Context mContext;

    private int layoutId;
    protected View rootView;
    protected OnWindowDismiss dismisslistener;

    public BasePopupWindow(Context context, int layoutId) {
        this.mContext = context;
        this.layoutId = layoutId;
        rootView = View.inflate(mContext, layoutId, null);
        init();
    }

    private void init() {
        setContentView(rootView);
        this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        this.setBackgroundDrawable(cd);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1f);
                onWindowDismiss();
            }
        });
    }

    protected void onWindowDismiss() {
        if (dismisslistener != null) {
            dismisslistener.WindowDismiss();
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setWindowAlpha(0.4f);
    }



    protected void setWindowAlpha(float alpha) {
        Activity activity = (Activity) mContext;
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }


    public void setOnWindowDismissListener(OnWindowDismiss listener) {
        this.dismisslistener = listener;
    }

    public interface OnWindowDismiss {
        void WindowDismiss();
    }
}
