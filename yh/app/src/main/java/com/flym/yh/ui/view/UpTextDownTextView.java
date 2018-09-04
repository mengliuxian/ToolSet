package com.flym.yh.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.yh.R;

/**
 * Created by mengl on 2018/3/23.
 */

public class UpTextDownTextView extends LinearLayout {

    private TextView upText, downText;
    private String upString, dwonString;

    public UpTextDownTextView(Context context) {
        this(context, null);
    }

    public UpTextDownTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpTextDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_two_text, this);
        upText = (TextView) findViewById(R.id.up_text);
        downText = (TextView) findViewById(R.id.down_text);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UpTextDownTextView);
        upString = typedArray.getString(R.styleable.UpTextDownTextView_up_text);
        dwonString = typedArray.getString(R.styleable.UpTextDownTextView_down_text);
        typedArray.recycle();
        init();
    }

    public void init() {

        if (!TextUtils.isEmpty(upString)) {
            upText.setText(upString);
        }
        if (!TextUtils.isEmpty(dwonString)) {
            downText.setText(dwonString);
        }
    }

    public void setUpString(String upString) {
        if (!TextUtils.isEmpty(upString)) {
            upText.setText(upString);
        }
    }

    public void setDwonString(String dwonString) {
        if (!TextUtils.isEmpty(dwonString)) {
            downText.setText(dwonString);
        }
    }
}
