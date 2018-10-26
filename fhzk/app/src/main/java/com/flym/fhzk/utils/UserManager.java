package com.flym.fhzk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.flym.fhzk.data.model.User;

//import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;

/**
 * Created by Morphine on 2017/6/19.
 */

public class UserManager {

    //
    private static String token;

    private static User user;

//    private static UserInfoBean userInfoBean;

    public static final String USER_SP_NAME = "user";
    public static final String ALISA = "alisa";

    public static void init(Context context) {
        user = getUser(context);
        if (user != null) {
            token = user.token;
        }
    }


    public static boolean isLogin() {
        user = getUser(ActivityManager.getInstance().currentActivity());
        if (user == null) {
            return false;
        } else {
            token = user.token;
        }
        return !TextUtils.isEmpty(token);
    }

    public static void login(Context context, User user1) {
        user = user1;
        token = user1.token;
        saveUserToNative(context, user1);
    }


    public static void logout(Context context) {
        user = null;
        token = null;
        AlibcLogin.getInstance().logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
        clearNativeUser(context);
        ActivityManager.getInstance().toLoginActivity();
    }

    public static String getToken(Context context) {
        if (TextUtils.isEmpty(token)) {
            token = getUser(context).token;
        }
        return token;
    }

    //
    public static User getUser(Context context) {
        if (user == null) {
            user = getUserFromNative(context);
        }
        return user;
    }

    //    public static void setSchoolId(String schoolId) {
//        user.schoolId = schoolId;
//    }
//
//    public static void setAuthorityLevel(String authorityLevel) {
//        user.authorityLevel = authorityLevel;
//    }
//
//    public static String getAuthorityLevel(Context context) {
//        return getUser(context).authorityLevel;
//    }
//
//
//    public static void setStudentId(String studentId) {
//        user.studentId = studentId;
//    }
//
//    public static void setIdentity(Context context, String identity) {
//        if (identity.equals(Constants.IDENTITY_BOSS)) identity = Constants.IDENTITY_TEACHER;
//        if ((identity.equals(Constants.IDENTITY_PARENT) && user.userType == 2) ||
//                (identity.equals(Constants.IDENTITY_TEACHER) && user.userType == 3)) {
//            user.userType = 5;
//        } else if (identity.equals(Constants.IDENTITY_STUDENT)) {
//            user.userType = 6;
//        } else if (identity.equals(Constants.IDENTITY_TEACHER)) {
//            user.userType = 2;
//        } else if (identity.equals(Constants.IDENTITY_PARENT)) {
//            user.userType = 3;
//        }
//        saveUserToNative(context, user);
//    }
//
    public static User getUserFromNative(Context context) {
        User user = null;
        SharedPreferences sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        String string = sp.getString(USER_SP_NAME, "");
        if (!TextUtils.isEmpty(string)) {
            user = GsonUtil.fromJson(string, User.class);
        }
        return user;
    }

    public static void saveAlias(Context context, boolean isTrue) {
        SharedPreferences sp = context.getSharedPreferences(ALISA, Context.MODE_PRIVATE);
        sp.edit().putBoolean(ALISA, isTrue).apply();
    }

    public static boolean getAlias(Context context) {
        SharedPreferences sp = context.getSharedPreferences(ALISA, Context.MODE_PRIVATE);
        boolean isTrue = sp.getBoolean(USER_SP_NAME, false);
        return isTrue;
    }


    public static void saveUserToNative(Context context, User user) {
        SharedPreferences sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_SP_NAME, GsonUtil.toJson(user)).apply();
    }


    //
    private static void clearNativeUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        SharedPreferences spA = context.getSharedPreferences(ALISA, Context.MODE_PRIVATE);
        spA.edit().clear().apply();
    }

}
