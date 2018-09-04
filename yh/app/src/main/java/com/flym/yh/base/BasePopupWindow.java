package com.flym.yh.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.flym.yh.utils.DensityUtil;


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
        int[] ints = calculatePopWindowPos(parent, rootView);

        super.showAtLocation(parent, gravity, ints[0], ints[1]);
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

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    private static int[] calculatePopWindowPos(final View anchorView, View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = DensityUtil.getScreenHeight(anchorView.getContext());
        final int screenWidth = DensityUtil.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = anchorLoc[0];
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = anchorLoc[0];
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }
}
