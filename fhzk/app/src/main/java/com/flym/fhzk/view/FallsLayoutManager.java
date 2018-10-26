package com.flym.fhzk.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Administrator
 * @date 2017/12/20 0020
 */

public class FallsLayoutManager extends LayoutManager {

    /**
     * RecyclerView.LayoutParams是继承自android.view.ViewGroup.MarginLayoutParams的，所以可以方便的使用各种margin
     * 这个方法最终会在recycler.getViewForPosition(i)时调用到
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {

        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 此方法是LayoutManager的入口，为界面添加Item
     * 它会在如下情况下被调用：
     * 1 在RecyclerView初始化时，会被调用两次。（在recyclerView的onMeasure()方法和onLayout()方法调用）
     * 2 在调用adapter.notifyDataSetChanged()时，会被调用。
     * 3 在调用setAdapter替换Adapter时,会被调用。
     * 4 在RecyclerView执行动画时，它也会被调用。
     * 即RecyclerView 初始化 、 数据源改变时 都会被调用。
     * 此方法相当于自定义ViewGroup时的onLauyout()方法，我们需要在此方法中layout出当前可见的所有子View，
     * 而不是layout出RecyclerView的所有子View
     *
     * @param recycler
     * @param state
     */

    private int mVerticalOffset; //竖直偏移量，每次换行的时候，要根据mVerticalOffset判断
    private int mFirstVisPosition; //屏幕可见的第一个Item的position
    private int mLastVisPosition; //屏幕可见的最后一个Item的position
    private SparseArray<Rect> mItenRect;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //当前RecyclerView没有数据时，界面为空
        if (getItemCount() == 0) {
            //detach轻量回收所有Item,此时回收的Item存放在scrapCache里面，scrapCache存放的Item不被用来进行复用
            detachAndScrapAttachedViews(recycler);

            return;
        }
        //getChildCount()返回的是当前屏幕可见的Item的数量
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler);
        //初始化
        mVerticalOffset = 0;
        mFirstVisPosition = 0;
        mLastVisPosition = getItemCount();

        //初始化时调用，填充Item
        fill(recycler, state, 0);
    }

    /**
     * 1 回收所有屏幕不可见的子View
     * 2 layout所有可见的子View
     * 填充childView的核心方法,应该先填充，再移动。
     * 在填充时，预先计算dy的在内，如果View越界，回收掉。
     * 一般情况是返回dy，如果出现View数量不足，则返回修正后的dy.
     *
     * @param recycler
     * @param state
     * @param dy       RecyclerView给我们的位移量,+,显示底端， -，显示头部
     * @return 修正以后真正的dy（可能剩余空间不够移动那么多了 所以return <|dy|）
     */
    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dy) {
        int topOffset = getPaddingTop(); //布局时的上偏移


        //回收越界Item
        if (getChildCount() > 0) {
            for (int i = getChildCount() - 1; i > 0; i--) {
                View child = getChildAt(i);
                if (dy > 0) //判断当前为开始滑动时，而不是初始化时
                {
                    //判断当前Item是否上越界
                    if (getDecoratedBottom(child) - dy < topOffset) {
                        removeAndRecycleView(child, recycler);
                        mFirstVisPosition++;
                        continue;
                    }
                } else if (dy < 0) {
                    if (getDecoratedTop(child) - dy > getHeight() - getPaddingBottom()) {
                        removeAndRecycleView(child, recycler);
                        mLastVisPosition--;
                        continue;
                    }
                }
            }
        }

        int leftOffset = getPaddingLeft(); //布局时的左偏移
        int lineMaxHeight = 0; //每一行的最大高度
        if (dy >= 0) {
            int minPosition = mFirstVisPosition; //初始化时，先假定layout所有的Item
            mLastVisPosition = getItemCount() - 1;
            if (getChildCount() > 0) {
                View child = getChildAt(getChildCount() - 1);
                minPosition = getPosition(child) + 1; //从最后一个可见的Item+1开始绘制
                topOffset = getDecoratedTop(child);
                leftOffset = getDecoratedRight(child);
                lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));
            }
            //顺序addChildView
            for (int i = minPosition; i < mLastVisPosition; i++) {
                //从recyclerView得到一个Item，
                // 不考虑Item是从scrapCache取到，
                // 还是RecyclerViewPool取到，（RecyclerViewPool里面存放可以复用的Item）
                // 又或者直接onCreateViewHolder取到
                // scrapCache 和 RecyclerViewPool 为两个集合
                View item = recycler.getViewForPosition(i);
                addView(item);
                //测量Item,这个方法会考虑到Item的ItemDecoration以及Margin
                measureChildWithMargins(item, 0, 0);
                //计算宽度,包括margin
                //如果左偏移量加上Item的水平宽度，小于recyclerView的宽度，则当前行还可以放的下
                if (leftOffset + getDecoratedMeasurementHorizontal(item) <= getHorizontalSpace()) {
                    //将Item开始layout出来，显示在屏幕上，内部会自动追加上该Item的ItemDecoration和Margin,此时的Item是可见的
                    layoutDecoratedWithMargins(item, leftOffset, topOffset,
                            leftOffset + getDecoratedMeasurementHorizontal(item),
                            topOffset + getDecoratedMeasurementVertical(item));
                    //保存Rect供逆序layout用
                    Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset,
                            leftOffset + getDecoratedMeasurementHorizontal(item),
                            topOffset + getDecoratedMeasurementVertical(item) + mVerticalOffset);
                    mItenRect.put(i, rect);

                    //改变leftOffset和lineMaxHeight
                    leftOffset += getDecoratedMeasurementHorizontal(item);
                    lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(item));
                } else {//当前行排列不下
                    //改变 top left lineHeight
                    leftOffset = getPaddingLeft();
                    topOffset += lineMaxHeight;
                    lineMaxHeight = 0;
                    //新起一行的时候，需要判断一下边界
                    if (topOffset - dy > getHeight() - getPaddingBottom()) {
                        //越界了就回收
                        removeAndRecycleView(item, recycler);
                        mLastVisPosition = i - 1;
                    } else {
                        layoutDecoratedWithMargins(item, leftOffset, topOffset,
                                leftOffset + getDecoratedMeasurementHorizontal(item),
                                topOffset + getDecoratedMeasurementVertical(item));
                        //保存Rect供逆序layout用
                        Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset,
                                leftOffset + getDecoratedMeasurementHorizontal(item),
                                topOffset + getDecoratedMeasurementVertical(item) + mVerticalOffset);
                        mItenRect.put(i, rect);
                        //改变leftOffset和lineMaxHeight
                        leftOffset += getDecoratedMeasurementHorizontal(item);
                        lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(item));
                    }
                }
            }
            //添加完Item之后，判断没有更多的Item，并且此时屏幕仍有空白，则需要修正
            View lastItem = getChildAt(getChildCount() - 1);
            if (getPosition(lastItem) == getItemCount() - 1) {
                int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(lastItem);
                if (gap > 0) {
                    dy = -gap;
                }
            }
        } else {
            int maxPosition = getItemCount() - 1;
            mFirstVisPosition = 0;
            if (getChildCount() >0)
            {
                View firstItem = getChildAt(0);
                maxPosition = getPosition(firstItem)-1;
            }
            for (int i = maxPosition; i >= mFirstVisPosition; i--) {
                Rect rect = mItenRect.get(i);

                if (rect.bottom - mVerticalOffset - dy < getPaddingTop()) {
                    mFirstVisPosition = i + 1;
                    break;
                } else {
                    View child = recycler.getViewForPosition(i);
                    addView(child, 0);//将View添加至RecyclerView中，childIndex为1，但是View的位置还是由layout的位置决定
                    measureChildWithMargins(child, 0, 0);

                    layoutDecoratedWithMargins(child, rect.left, rect.top - mVerticalOffset, rect.right, rect.bottom - mVerticalOffset);
                }
            }

        }

        return dy;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //位移为0，或者没有可显示的Item，就不移动
        if (dy == 0 || getChildCount() == 0) {
            return 0;
        }
        //实际滑动的距离，可能会在边界处被修复
        int realOffset = dy;
        //边界修复代码
        if (mVerticalOffset + realOffset < 0) //上边界
        {
            realOffset = -mVerticalOffset;
        } else if (realOffset > 0) //下边界
        {
            //利用最后一个Item比较修正
            View lastItem = getChildAt(getChildCount() - 1);
            if (getPosition(lastItem) == getItemCount() - 1) {
                int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(lastItem);
                if (gap > 0) {
                    realOffset = -gap;
                } else if (gap == 0) {
                    realOffset = 0;
                } else {
                    realOffset = Math.min(realOffset, -gap);
                }
            }
        }
        //先填充，在位移
        realOffset = fill(recycler, state, realOffset);
        //累加实际滑动距离
        mVerticalOffset += realOffset;

        //竖直平移容器内的所有item，返回值会被recyclerview来判断是否到达边界
        offsetChildrenVertical(-realOffset);
        return realOffset;
    }

    //模仿LLM Horizontal 源码

    /**
     * 获取某个childView在水平方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }

    /**
     * 获取竖直方向上的剩余空间
     *
     * @return
     */
    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 获取水平方向上的剩余空间
     *
     * @return
     */
    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
