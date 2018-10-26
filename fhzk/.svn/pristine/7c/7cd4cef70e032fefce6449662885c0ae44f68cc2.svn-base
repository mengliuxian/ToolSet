package com.flym.fhzk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.utils.GlideUtil;

import java.io.File;


/**
 * Created by Morphine on 2017/6/16.
 */

public class CustomMineItemView extends LinearLayout {

    private int leftIcon, rightIcon = R.drawable.ic_right;
    private String titleText = "", rightText = "";
    private boolean isVisity;
    ImageView leftIconView;
    ImageView rightIconView;
    TextView rightTextView;
    TextView titleTextView;
    Context context;
    CircleImageView rightImgView;

    public CustomMineItemView(Context context) {
        this(context, null);
    }

    public CustomMineItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomMineItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.mine_bottom_item, this, true);
        leftIconView = (ImageView) findViewById(R.id.left_icon);
        rightIconView = (ImageView) findViewById(R.id.right_icon);
        rightTextView = (TextView) findViewById(R.id.right_text);
        titleTextView = (TextView) findViewById(R.id.title);
        rightImgView = (CircleImageView) findViewById(R.id.right_img);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMineItemView, defStyleAttr, 0);

        leftIcon = a.getResourceId(R.styleable.CustomMineItemView_leftIcon, 0);
        rightIcon = a.getResourceId(R.styleable.CustomMineItemView_rightIcon, 0);
        rightText = a.getString(R.styleable.CustomMineItemView_rightText);
        titleText = a.getString(R.styleable.CustomMineItemView_titleText);
        isVisity = a.getBoolean(R.styleable.CustomMineItemView_isVisity, true);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        rightImgView.setVisibility(rightIcon != 0 ? VISIBLE : GONE);
        rightTextView.setVisibility(TextUtils.isEmpty(rightText) ? GONE : VISIBLE);
        rightIconView.setVisibility(isVisity ? VISIBLE : GONE);
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

        GlideUtil.loadImage(this.context, imgUrl, rightImgView);
    }


    public void setTitleText(String titleText) {
        titleTextView.setText(titleText);
    }


    public void setRightIcon(File imgUrl) {

        GlideUtil.loadImage(this.context, imgUrl, rightImgView);
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        rightTextView.setText(rightText);
        if (!TextUtils.isEmpty(rightText)) {
            rightTextView.setVisibility(VISIBLE);
        }
    }
}
