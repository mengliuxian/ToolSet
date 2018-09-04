package com.hyphenate.easeui.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by Morphine on 2017/8/28.
 */

public class PermissionUtil {
    /**
     * 请求拍照权限请求码
     */
    public static final int REQUEST_CAMERA_PERMISSIONS = 3;
    /**
     * 请求存储卡权限请求码
     */
    public static final int REQUEST_SDCARD_PERMISSIONS = 4;

    @AfterPermissionGranted(REQUEST_CAMERA_PERMISSIONS)
    public static boolean checkCameraPermission(Activity activity) {
        String[] perms = new String[]{Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(activity,
                    "请求相机权限", REQUEST_CAMERA_PERMISSIONS, perms);
            return false;
        }
    }

    @AfterPermissionGranted(REQUEST_CAMERA_PERMISSIONS)
    public static boolean checkCameraPermission(Context context, Fragment fragment) {
        String[] perms = new String[]{Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(context, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(fragment,
                    "请求相机权限", REQUEST_CAMERA_PERMISSIONS, perms);
            return false;
        }
    }


    @AfterPermissionGranted(REQUEST_SDCARD_PERMISSIONS)
    public static boolean checkSDCardPermission(Context context, Fragment fragment) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(fragment,
                    "请求sd卡存储权限", REQUEST_SDCARD_PERMISSIONS, perms);
            return false;
        }
    }

    @AfterPermissionGranted(REQUEST_SDCARD_PERMISSIONS)
    public static boolean checkSDCardPermission(Activity activity) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(activity,
                    "请求sd卡存储权限", REQUEST_SDCARD_PERMISSIONS, perms);
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
