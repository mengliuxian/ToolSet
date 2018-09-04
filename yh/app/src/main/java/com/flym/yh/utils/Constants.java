package com.flym.yh.utils;

/**
 * Created by Morphine on 2017/6/12.
 */

public class Constants {


    public static final String APP_ID = "";
    public static final String APP_SECRET = "";
    public static final long HTTP_TIME_OUT = 10000L; //http 超时时间(毫秒)
    public static final int ERROR_LOGIN =1001; //重新登录

    public static final int HTTP_MAX_CONNECT_COUNT = 10;// HTTP最大连接数

    public static final long HTTP_KEEP_ALIVE_CONNECT_COUNT = 5;// HTTP保持KeepAlive的连接数
    public static boolean isChange = false;
    /**
     * 请求存储卡权限请求码
     */
    public static final int REQUEST_SDCARD_PERMISSIONS = 4;

    /**
     * 请求录音权限请求码
     */
    public static final int REQUEST_RECORD_AUDIO_PERMISSIONS = 2;
    /**
     * 请求拍照权限请求码
     */
    public static final int REQUEST_CAMERA_PERMISSIONS = 3;


    public static final String SHARE_PRE_NAME_GUIDE = "isGuide";

    public static String LOGIN_ACTION = "com.flym.sfc.login";

    public static final int DEPART = 101;
    public static final int SHOPMALL = 201;

}
