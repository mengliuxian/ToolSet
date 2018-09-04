package com.flym.yh.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class InputMethodUtils {
    //隐藏输入法
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) ActivityManager.getInstance().currentActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
