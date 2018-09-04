package com.flym.yh.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * @author Administrator
 */

public class TextSpannableUtils {


    //两次加大字体，设置字体为红色（big会加大字号，font可以定义颜色）
//textView1.setText(Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩"));

    //设置字体大小为3级标题，设置字体为红色
//textView2.setText(Html.fromHtml("北京市发布霾黄色预警，<h3><font color='#ff0000'>外出携带好</font></h3>口罩"));

//设置字体大小为58（单位为物理像素），设置字体为红色，字体背景为黄色
//textView3.setText("北京市发布霾黄色预警，外出携带好口罩");
//    Spannable span = new SpannableString(textView3.getText());
//     span.setSpan(new AbsoluteSizeSpan(58), 11, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//     span.setSpan(new ForegroundColorSpan(Color.RED), 11, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//     span.setSpan(new BackgroundColorSpan(Color.YELLOW), 11, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//    textView3.setText(span);

    //两次缩小字体，设置字体为红色（small可以减小字号）
//textView4.setText(Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><small><small>外出携带好</small></small></font>口罩"));


    /**
     * 改变颜色
     *
     * @param content
     * @param star
     * @param end
     * @param color
     * @return
     */
    public static Spannable setSpannable(String content, int star, int end, int color) {
        Spannable spannable = new SpannableString(content);
        spannable.setSpan(new ForegroundColorSpan(color), star, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;

    }

    /**
     * 改变字体大小
     *
     * @param context
     * @param content
     * @param star
     * @param end
     * @param size
     * @return
     */
    public static Spannable setSpannableSize(Context context, String content, int star, int end, int size) {
        Spannable spannable = new SpannableString(content);
        spannable.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(context, size)), star, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 设置字体大小和颜色
     *
     * @param context
     * @param content
     * @param star
     * @param end
     * @param size
     * @param color
     * @return
     */
    public static Spannable setSpannable(Context context, String content, int star, int end, int size, int color) {
        Spannable spannable = new SpannableString(content);
        spannable.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(context, size)), star, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(color), star, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

}
