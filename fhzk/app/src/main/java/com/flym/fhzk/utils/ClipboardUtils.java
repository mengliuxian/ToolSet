package com.flym.fhzk.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * 剪切板复制
 *
 * @author Administrator
 * @date 2017/12/12 0012
 */


public class ClipboardUtils {

    //复制文字到剪切板
    public static void coptPaste(Context context, String s1) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("text", s1);
        cm.setPrimaryClip(data);
    }
}
