package com.flym.fhzk.utils;

import android.text.TextUtils;

/**
 * Created by Morphine on 2017/6/12.
 */

public class ApiConstants {

    public static final String BASE_URL = "http://php.ssssapp.com/fhzg/api/";

    public static final String PATH = "appLoginCtl/applogin";


    public static final String IMAGE_PATH = "http://59.44.22.65:8080";


    public static final String LOGIN = BASE_URL + "User/login";

    public static final String GETCODE = BASE_URL + "User/getCode";

    public static final String REG = BASE_URL + "User/reg";

    public static final String SETPWD = BASE_URL + "User/setpwd";

    public static final String GETBANNER = BASE_URL + "App/getBanner";

    public static final String GETSKU = BASE_URL + "App/getSku";

    public static final String GETACTIVE = BASE_URL + "App/getChannel";

    public static final String GETGOODS = BASE_URL + "App/getGoods";

    public static final String GETHOTKEY = BASE_URL + "Search/getHotKey";

    public static final String GETKEYGOODS = BASE_URL + "Search/getKeyGoods";

    public static final String GETCOUPONS = BASE_URL + "Coupon/getCoupons";

    public static final String GETCOUPONGOODS = BASE_URL + "Coupon/getCouponGoods";

    public static final String GETVERSION = BASE_URL + "Set/getVersion";

    public static final String LOGOUT = BASE_URL + "Set/logout";

    public static final String GETPROFILE = BASE_URL + "Set/getProfile";

    public static final String UPDATAPROFILE = BASE_URL + "Set/updateProfile";

    public static final String COLLECT = BASE_URL + "App/collect";

    public static final String GETCOLLECTED = BASE_URL + "Set/getCollected";

    public static final String BROWSE = BASE_URL + "App/browse";

    public static final String GETBROWSED = BASE_URL + "Set/getBrowsed";

    public static final String DELBROWSED = BASE_URL + "Set/delBrowsed";

    public static final String GETCOUPONSKUGOODS = BASE_URL + "Coupon/getCouponSkuGoods";

    public static final String GETCOUPONSKU = BASE_URL + "Coupon/getCouponSku";

    public static final String GETSKUGOODS = BASE_URL + "App/getSkuGoods";

    public static final String UNREADMSG = BASE_URL + "Set/unreadMsg";

    public static final String GETMESSAGE = BASE_URL + "Set/getMessage";

    public static final String GETSIGNINFO  = BASE_URL + "Sign/getSignInfo";

    public static final String SIGNIN  = BASE_URL + "Sign/signIn";

    public static final String GETHELPCONF  = BASE_URL + "Help/getHelpConf";

    public static final String GETHELPDETAIL = BASE_URL + "Help/getHelpDetail";

    public static final String GETINVITEINFO = BASE_URL + "Set/getInviteInfo";

    public static final String ABOUTUS = BASE_URL + "Set/aboutUs";

    public static final String GETCHANNELSKU = BASE_URL + "Channel/getChannelSku";

    public static final String GETCHANNELGOODS = BASE_URL + "Channel/getChannelGoods";












































    public static String getImageUrl(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            return "";
        }
        if (imgUrl.startsWith("http")) {
            return imgUrl;
        } else {
            return IMAGE_PATH + imgUrl;
        }
    }

    public static String getVideoUrl(String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            return "";
        }
        if (videoUrl.startsWith("http")) {
            return videoUrl;
        } else {
            return BASE_URL + IMAGE_PATH + videoUrl;
        }
    }

//    public static String getFileUrl(String fileUrl) {
//        if (TextUtils.isEmpty(fileUrl)) {
//            return "";
//        }
//        if (fileUrl.startsWith("http")) {
//            return fileUrl;
//        } else return BASE_URL + IMAGE_PATH + fileUrl;
//    }
}
