package com.flym.fhzk.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class SpannableStringUtils {

    public static SpannableString getSpannableString(String content, String color, int start, int end) {

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#" + color))
                , start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
