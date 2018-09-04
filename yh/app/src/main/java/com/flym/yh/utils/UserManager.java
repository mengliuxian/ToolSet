package com.flym.yh.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.SimpleString;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by Morphine on 2017/6/19.
 */

public class UserManager {

    private static String token;

    private static SimpleString user;

    public static final String USER_SP_NAME = "user";
    public static final String USER_DATA = "data";
    public static final String LANG = "lang";
    public static final String TOKEN = "token";
    public static final String PUSH = "push";

    public static void init(Context context) {
        user = getUser(context);
        if (user != null) {
            token = user.string;
        }
    }

    public static boolean isLogin() {
        user = getUser(ActivityManager.getInstance().currentActivity());
        if (user == null) {
            return false;
        } else {
            token = user.string;
        }
        return !TextUtils.isEmpty(token);
    }

    public static void login(Context context, SimpleString users) {
        user = users;
        token = users.string;
        saveUserToNative(context, users);
//        saveLang(context, users.auth_status);
//        savePush(context, true);
    }


    public static void logout(Context context) {
        user = null;
        token = null;
        clearNativeUser(context);
        ActivityManager.getInstance().toLoginActivity();
    }

    public static String getToken(Context context) {
        if (TextUtils.isEmpty(token)) {
            token = getUser(context).string;
        }
        return token;
    }

    //
    public static SimpleString getUser(Context context) {
        if (user == null) {
            user = getUserFromNative(context);
        }
        return user;
    }

    public static SimpleString getUserFromNative(Context context) {
        SimpleString user = null;
        SharedPreferences sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        String string = sp.getString(USER_SP_NAME, "");
        if (!TextUtils.isEmpty(string)) {
            user = GsonUtil.fromJson(string, SimpleString.class);
        }
        return user;
    }

    public static void saveLang(Context context, int isTrue) {
        SharedPreferences sp = context.getSharedPreferences(LANG, Context.MODE_PRIVATE);
        sp.edit().putInt(LANG, isTrue).apply();
    }


    public static void savePush(Context context, boolean token) {
        SharedPreferences sp = context.getSharedPreferences(PUSH, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PUSH, token).apply();
    }

    public static int getLang(Context context) {
        SharedPreferences sp = context.getSharedPreferences(LANG, Context.MODE_PRIVATE);
        return sp.getInt(LANG, 1);
    }

    public static boolean getPush(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PUSH, Context.MODE_PRIVATE);
        return sp.getBoolean(PUSH, true);
    }

//    public static String getToken(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
//        return sp.getString(TOKEN, "");
//    }


    public static void saveUserToNative(Context context, SimpleString simpleString) {
        SharedPreferences sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_SP_NAME, GsonUtil.toJson(simpleString)).apply();
    }

    public static void saveUserDataToNative(Context context, DoctorGetdetailBean data) {
        SharedPreferences sp = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        sp.edit().putString(USER_DATA, GsonUtil.toJson(data)).apply();
    }

    public static DoctorGetdetailBean getUserDataFromNative(Context context) {
        DoctorGetdetailBean data = null;
        SharedPreferences sp = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        String string = sp.getString(USER_DATA, "");
        if (!TextUtils.isEmpty(string)) {
            data = GsonUtil.fromJson(string, DoctorGetdetailBean.class);
        }
        return data;
    }


    private static void clearNativeUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        SharedPreferences spA = context.getSharedPreferences(LANG, Context.MODE_PRIVATE);
        spA.edit().clear().apply();
        SharedPreferences spPush = context.getSharedPreferences(PUSH, Context.MODE_PRIVATE);
        spPush.edit().clear().apply();
        SharedPreferences spD = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        spD.edit().clear().apply();
    }

    public static void writeFile(Context context, SimpleString simpleString) {

        try {
            FileOutputStream fileOutputStream = context.openFileOutput("user.text", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(simpleString);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SimpleString readFile(Context context) {
        Object object = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("user.text");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (SimpleString) object;
    }


}
