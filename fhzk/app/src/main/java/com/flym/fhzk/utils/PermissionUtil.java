package com.flym.fhzk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.flym.fhzk.utils.Constants.REQUEST_LOCATION_PERMISSIONS;


/**
 * Created by Morphine on 2017/8/28.
 */

public class PermissionUtil {


    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSIONS)
    public static void checkLocationPermissions(Activity activity, OnPerMissionSuccess successListener, OnPerMissionFail failSuccess) {
        String[] perms = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            successListener.onSuccess();
            Log.d(TAG, "checkLocationPermissions: 111111");
        } else {
            EasyPermissions.requestPermissions(activity, "定位权限", REQUEST_LOCATION_PERMISSIONS, perms);
            failSuccess.onFail();
        }
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSIONS)
    public static boolean checkLocationPermissions(Activity activity) {
        String[] perms = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(activity, "定位权限", REQUEST_LOCATION_PERMISSIONS, perms);
            return false;
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CAMERA_PERMISSIONS)
    public static boolean checkCameraPermission(Activity activity) {
        String[] perms = new String[]{Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(activity,
                    "请求相机权限", Constants.REQUEST_CAMERA_PERMISSIONS, perms);
            return false;
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CAMERA_PERMISSIONS)
    public static boolean checkCameraPermission(Context context, Fragment fragment) {
        String[] perms = new String[]{Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(context, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(fragment,
                    "请求相机权限", Constants.REQUEST_CAMERA_PERMISSIONS, perms);
            return false;
        }
    }


    @AfterPermissionGranted(Constants.REQUEST_SDCARD_PERMISSIONS)
    public static boolean checkSDCardPermission(Context context, Fragment fragment) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(fragment,
                    "请求sd卡存储权限", Constants.REQUEST_SDCARD_PERMISSIONS, perms);
            return false;
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_SDCARD_PERMISSIONS)
    public static boolean checkSDCardPermission(Activity activity) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(activity,
                    "请求sd卡存储权限", Constants.REQUEST_SDCARD_PERMISSIONS, perms);
            return false;
        }
    }

    public interface OnPerMissionSuccess {
        void onSuccess();
    }

    public interface OnPerMissionFail {
        void onFail();
    }

}
