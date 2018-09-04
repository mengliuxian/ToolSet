package com.flym.yh.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.flym.yh.MyApplication;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class NetWorkUtils {

    public static final String WIFI = "WIFI";


    /**
     * 判断是否有网络连接
     *
     * @return
     */
    public static boolean isNetworkConnected() {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
        }
        return false;
    }


    /**
     * 判断WIFI网络是否可用
     *
     * @return
     */
    public static boolean isWifiConnected() {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();

            if (mWiFiNetworkInfo != null) {
                String typeName = mWiFiNetworkInfo.getTypeName();
                if (WIFI.equalsIgnoreCase(typeName)) {
                    return mWiFiNetworkInfo.isAvailable();
                }
        }
        return false;
    }


    /**
     * 判断MOBILE网络是否可用
     *
     * @return
     */
    public static boolean isMobileConnected() {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mMobileNetworkInfo != null) {
                int typeName = mMobileNetworkInfo.getType();
                if (ConnectivityManager.TYPE_MOBILE == typeName) {
                    return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 获取当前网络连接的类型信息
     *
     * @return
     */
    public static int getConnectedType() {

            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }

        return -1;
    }


    /**
     * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
     *
     * @return
     */
    public static int getAPNType() {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) MyApplication.mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) MyApplication.mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {
                netType = 2;// 3G
            } else {
                netType = 3;// 2G
            }
        }
        return netType;
    }
}
