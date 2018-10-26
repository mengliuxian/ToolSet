package com.flym.fhzk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class ScrollViewChosses extends NestedScrollView {

    View view;
    private int height;
    private int measuredHeight;
    private boolean isFoot;

    public ScrollViewChosses(Context context) {
        this(context, null);
    }

    public ScrollViewChosses(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewChosses(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ScrollViewListener scrollViewListener;

    //接口回调
    public interface ScrollViewListener {
        void onFix();

        void onReset();

        void loadMore();

        void onScrollChanged(ScrollViewChosses scrollView, int x, int y, int oldx, int oldy);

    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {

            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
            int height = getChildAt(0).getHeight();
            int measuredHeight = getMeasuredHeight();

            if (y >= height - measuredHeight) {
                scrollViewListener.loadMore();
                isFoot = true;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            default:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFoot) {
                    scrollViewListener.loadMore();
                    isFoot = false;
                    return true;
                }

                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View views = getChildAt(0);
        if (changed) {
            if (views instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) views;
                int childCount = 0;
                if (linearLayout != null) {

                    childCount = linearLayout.getChildCount();
                }
                for (int i = 0; i < childCount; i++) {
                    if (linearLayout.getChildAt(i).getTag() != null
                            && "tab".equals((String) linearLayout.getChildAt(i).getTag())) {
                        view = linearLayout.getChildAt(i);
                    }
                }
            }
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (view != null && getScrollY() >= view.getTop()) {
            fixHead();
            //保存画布状态
            canvas.save();
            canvas.translate(0, getScrollY());
            //裁剪一块矩形区域
            canvas.clipRect(0, 0, view.getWidth(), view.getHeight());
            view.draw(canvas);
            //取出画布状态
            canvas.restore();
        } else {
            resetHead();
        }
    }

    private void fixHead() {
        if (scrollViewListener != null) {
            scrollViewListener.onFix();
        }
    }

    private void resetHead() {
        if (scrollViewListener != null) {
            scrollViewListener.onReset();
        }
    }


}
