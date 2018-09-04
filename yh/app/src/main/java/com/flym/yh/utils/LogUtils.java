package com.flym.yh.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class LogUtils {
    public static boolean APP_DBG = false; // debug:true,release:false

    public static void init(Context context) {
        APP_DBG = isApkDebugable(context);
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     *
     * @param context
     * @return
     * @author SHANHY
     * @date 2015-8-7
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info != null && (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        } catch (Exception e) {

        }
        return false;
    }
}
