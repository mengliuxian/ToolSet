package com.flym.yh.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.DensityUtil;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GlideUtil;

import java.io.File;


/**
 * @author lishuqi
 * @date 2018/3/19
 */

public class ImageTextView extends FrameLayout {
    private ImageView img;
    private TextView tv;
    private int imgbg;
    private String tvstring;
    private int width;
    private int height;
    private int bgId;
    private LinearLayout mLayout;
    private Context mContext;


    public ImageTextView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ImageTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        init(context);
    }

    public ImageTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        init(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mtypeArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        imgbg = mtypeArray.getResourceId(R.styleable.ImageTextView_ivbg, 0);
        tvstring = mtypeArray.getString(R.styleable.ImageTextView_tvstring);
        width = mtypeArray.getDimensionPixelOffset(R.styleable.ImageTextView_image_view_width, 0);
        height = mtypeArray.getDimensionPixelOffset(R.styleable.ImageTextView_image_view_height, 0);
        //用完就回收
        mtypeArray.recycle();
    }

    public void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_top_img_bottom_text, this);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(tvstring);
        img = (ImageView) findViewById(R.id.img);


        if (height != 0 && width != 0) {
            ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            img.setLayoutParams(layoutParams);
        }
        img.setImageResource(imgbg);
    }

    public void setBg(int i) {
        img.setImageResource(i);
    }

    public void setTextColor(int color) {
        tv.setTextColor(color);
    }

    public void setTe(String s) {
        tv.setText(s);
    }

    public void loadImage(File file) {
        GlideUtil.loadImage(mContext, file, img, R.drawable.ic_camear);
    }

    public void loadImage(String url) {
        GlideUtil.loadImage(mContext, ApiConstants.getImageUrl(url), img, R.drawable.ic_camear);
    }
}
