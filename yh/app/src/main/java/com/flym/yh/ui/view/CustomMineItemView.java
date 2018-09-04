package com.flym.yh.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.flym.yh.R;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;

import java.io.File;


/**
 * Created by Morphine on 2017/6/16.
 */

public class CustomMineItemView extends LinearLayout {

    private int leftIcon, rightColor, bg, rightIcon = R.drawable.wd_smrz_xuanze;
    private String titleText = "", rightText = "";
    private float rightSize;
    private boolean isVisity, isNotVisity;
    View view;
    RelativeLayout layout;
    ImageView leftIconView;
    ImageView rightIconView;
    TextView rightTextView;
    TextView titleTextView;
    Context context;
    CircleImageView rightImgView;
    private View inflate;
    private int rightGravity;
    private int width;
    private int height;

    public CustomMineItemView(Context context) {
        this(context, null);
    }

    public CustomMineItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomMineItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate = LayoutInflater.from(context).inflate(R.layout.mine_bottom_item, this, true);
        leftIconView = (ImageView) findViewById(R.id.left_icon);
        layout = (RelativeLayout) findViewById(R.id.layout);
        view = (View) findViewById(R.id.view);
        rightIconView = (ImageView) findViewById(R.id.right_icon);
        rightTextView = (TextView) findViewById(R.id.right_text);
        titleTextView = (TextView) findViewById(R.id.title);
        rightImgView = (CircleImageView) findViewById(R.id.right_img);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMineItemView, defStyleAttr, 0);

        leftIcon = a.getResourceId(R.styleable.CustomMineItemView_leftIcon, 0);
        rightIcon = a.getResourceId(R.styleable.CustomMineItemView_rightIcon, 0);
        rightText = a.getString(R.styleable.CustomMineItemView_rightText);
        titleText = a.getString(R.styleable.CustomMineItemView_titleText);
        bg = a.getColor(R.styleable.CustomMineItemView_bg, getResources().getColor(R.color.grey_33));
        rightSize = a.getDimension(R.styleable.CustomMineItemView_rightSize, 16);
        rightColor = a.getColor(R.styleable.CustomMineItemView_rightColor, getResources().getColor(R.color.black));
        isVisity = a.getBoolean(R.styleable.CustomMineItemView_isVisity, true);
        isNotVisity = a.getBoolean(R.styleable.CustomMineItemView_isNotVisity, true);
        rightGravity = a.getInt(R.styleable.CustomMineItemView_rightGravity, 0);
        width = a.getDimensionPixelOffset(R.styleable.CustomMineItemView_imageWidth, 0);
        height = a.getDimensionPixelOffset(R.styleable.CustomMineItemView_imageHeight, 0);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view.setVisibility(isNotVisity ? GONE : VISIBLE);
        layout.setBackgroundColor(bg);
        rightImgView.setVisibility(rightIcon != 0 ? VISIBLE : GONE);
        rightTextView.setVisibility(TextUtils.isEmpty(rightText) ? GONE : VISIBLE);
        rightIconView.setVisibility(isVisity ? VISIBLE : GONE);
        rightTextView.setTextColor(rightColor);
        if (width != 0 && height != 0) {
            ViewGroup.LayoutParams layoutParams = rightImgView.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            rightImgView.setLayoutParams(layoutParams);
        }
        if (rightGravity == 0) {
            rightTextView.setGravity(Gravity.RIGHT);

        } else if (rightGravity == 1) {
            rightTextView.setGravity(Gravity.LEFT);
        } else if (rightGravity == 2) {
            rightTextView.setGravity(Gravity.CENTER);

        }
        rightTextView.setTextSize(rightSize);
        if (leftIcon == 0) {
            leftIconView.setVisibility(GONE);
        } else {
            leftIconView.setImageDrawable(context.getResources().getDrawable(leftIcon));
        }
        titleTextView.setText(titleText);
        rightTextView.setText(rightText);
        if (rightIcon != 0) {
            rightImgView.setImageDrawable(context.getResources().getDrawable(rightIcon));
        }
    }


    public void setRightIcon(String imgUrl) {

        GlideUtil.loadImage(this.context, ApiConstants.getImageUrl(imgUrl), rightImgView, R.drawable.wd_grmp__touxiang);
    }


    public void setTitleText(String titleText) {
        titleTextView.setText(titleText);
    }


    public void setRightIcon(File imgUrl) {

        GlideUtil.loadImage(this.context, imgUrl, rightImgView, R.drawable.wd_grmp__touxiang);
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        rightTextView.setText(rightText);
        if (!TextUtils.isEmpty(rightText)) {
            rightTextView.setVisibility(VISIBLE);
        }
    }


}
