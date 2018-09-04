package com.flym.yh.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flym.yh.R;

/**
 * Created by mengl on 2018/3/19.
 */

public class ToolbarLayout extends RelativeLayout {

    public String conterTitleText, rightTitleText;

    public TextView conterText;
    public TextView rightText;

    public void setListener(OnClickListener listener) {
        rightText.setOnClickListener(listener);
    }

    public ToolbarLayout(Context context) {
        this(context, null);
    }

    public ToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.no_back_toolbar, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolbarLayout, defStyleAttr, 0);
        conterTitleText = typedArray.getString(R.styleable.ToolbarLayout_contertitleText);
        rightTitleText = typedArray.getString(R.styleable.ToolbarLayout_rightTitleText);
        conterText = (TextView) findViewById(R.id.conter_title);
        rightText = (TextView) findViewById(R.id.right_title);
        typedArray.recycle();
        init();
    }

    public void init() {
        if (!TextUtils.isEmpty(conterTitleText)) {
            conterText.setText(conterTitleText);
        }

        if (!TextUtils.isEmpty(rightTitleText)) {
            rightText.setText(rightTitleText);
        }
    }
}
