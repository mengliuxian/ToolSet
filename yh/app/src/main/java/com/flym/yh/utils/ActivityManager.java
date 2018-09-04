package com.flym.yh.utils;

import android.app.Activity;
import android.content.Intent;


import com.flym.yh.base.SplashActivity;
import com.flym.yh.ui.activity.WebViewActivity;
import com.flym.yh.ui.activity.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import static com.flym.yh.utils.Constants.ERROR_LOGIN;


/**
 * Created by Morphine on 2017/6/12.
 */

public class ActivityManager {

    private static final String TAG = "ActivityManager";
    private static final int AGAINLOGIN = 109;

    private static volatile List<Activity> activitys = new ArrayList<>();

    private static ActivityManager instance;

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    public void toSplashActivity() {
        popLastActivity();
        startNextActivity(SplashActivity.class, true);
    }

    public int currentActivitySize() {
        return activitys.size();
    }

    public Activity currentActivity() {
        Activity activity = null;
        if (activitys.size() != 0) {
            activity = activitys.get(activitys.size() - 1);
        }
        return activity;
    }

    public void push(Activity activity) {
        if (currentActivitySize() > 6) {
            popActivity(activitys.get(1));
        }
        activitys.add(activity);
    }

    public void popActivity(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is a null object");
        }
        activity.finish();
        activitys.remove(activity);
    }

    public void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }

    public void popLastActivity() {
        for (int i = activitys.size() - 1; i > 0; i--) {
            popActivity(activitys.get(i));
        }
    }

    public void exitApp() {
        popAllActivity();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void startNextActivity(Class<?> cls) {
        startNextActivity(cls, false);
    }

    public void startNextActivity(Class<?> cls, boolean isPopCurrent) {
        if (cls == null) {
            throw new NullPointerException("activity is a null object");
        }
        Activity currentActivity = currentActivity();
        currentActivity.startActivity(new Intent(currentActivity, cls));
        if (isPopCurrent) {
            popActivity(currentActivity);
        }
    }

    public void startNextActivity(Intent intent) {
        if (intent == null) {
            throw new NullPointerException("intent is a null object");
        }
        Activity currentActivity = currentActivity();
        currentActivity.startActivity(intent);
    }

    public void startNextActivity(Intent intent, boolean isPopCurrent) {
        if (intent == null) {
            throw new NullPointerException("intent is a null object");
        }
        Activity currentActivity = currentActivity();
        currentActivity.startActivity(intent);
        if (isPopCurrent) {
            popActivity(currentActivity);
        }
    }

    public void startNextActivityForResult(Intent intent, int requestCode) {
        if (intent == null) {
            throw new NullPointerException("intent is a null object");
        }
        Activity currentActivity = currentActivity();
        currentActivity.startActivityForResult(intent, requestCode);
    }

    public void startNextActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(currentActivity(), cls);
        startNextActivityForResult(intent, requestCode);
    }


    public void toLoginActivity() {
        popLastActivity();
        ActivityManager.getInstance().startNextActivity(new Intent(
                ActivityManager.getInstance().currentActivity()
                , LoginActivity.class), true);
    }

    public void toWebActivity(String url, String html) {
        Intent intent = new Intent(currentActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("html", html);
        startNextActivity(intent);
    }

}
