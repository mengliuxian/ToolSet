package com.flym.yh.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flym.yh.R;

/**
 * Author:mengl
 * Time:2018/4/17
 */
public class TextUpDownView extends RelativeLayout implements View.OnClickListener {

    private String title, content;
    private TextView tvTitle, tvContent;
    private RelativeLayout layout;

    public TextUpDownView(Context context) {
        this(context, null);
    }

    public TextUpDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextUpDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_text_up_down, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextUpDownView);
        title = typedArray.getString(R.styleable.TextUpDownView_title);
        content = typedArray.getString(R.styleable.TextUpDownView_content);
        typedArray.recycle();
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setOnClickListener(this);
        setData();
    }

    private void setData() {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
    }

    @Override
    public void onClick(View v) {
        if (tvContent.getLineCount() <= 2) {
            tvContent.setMaxLines(Integer.MAX_VALUE);
        } else {
            tvContent.setMaxLines(2);
        }
//        invalidate();
    }

    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
    }

}
