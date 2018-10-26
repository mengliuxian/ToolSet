package com.flym.fhzk.utils;

/**
 * Created by Morphine on 2017/6/12.
 */

public class Constants {


    public static final long HTTP_TIME_OUT = 10000L; //http 超时时间(毫秒)


    public static final int HTTP_MAX_CONNECT_COUNT = 10;// HTTP最大连接数

    public static final long HTTP_KEEP_ALIVE_CONNECT_COUNT = 5;// HTTP保持KeepAlive的连接数

    /**
     * 请求存储卡权限请求码
     */
    public static final int REQUEST_SDCARD_PERMISSIONS = 3;

    /**
     * 请求录音权限请求码
     */
    public static final int REQUEST_RECORD_AUDIO_PERMISSIONS = 2;
    /**
     * 请求拍照权限请求码
     */
    public static final int REQUEST_CAMERA_PERMISSIONS = 3;

    public static final int REQUEST_LOCATION_PERMISSIONS = 12;

    public static final int REQUEST_LOCATION_PERMISSIONS_PHONE = 13;

    public static final String SHARE_PRE_NAME_GUIDE = "isGuide";


    public static final String AUDIT_STATE = "audit_state";

    public static final int LOGO_SELECT_ACTIVITY_REQUEST_CODE = 10;
    public static final int LOGO_SELECT_ACTIVITY_RESULT_CODE = 11;


    public static final String[] FILE_TYPE = {"doc", "docx", "xls", "xlsx", "ppt", "pdf", "pptx"};

}
