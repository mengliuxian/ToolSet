package com.flym.fhzk.utils;

import android.app.Activity;
import android.content.Intent;

import com.flym.fhzk.ui.SplashActivity;
import com.flym.fhzk.ui.WebViewActivity;
import com.flym.fhzk.ui.activity.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;



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
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
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
        ActivityManager.getInstance().startNextActivity(
                        LoginActivity.class,true);
    }
//
//    public void toStudentIndex(Context context) {
//        startNextActivity(StudentMainActivity.class);
//    }
//
//    public void toTeacherIndex(Context context) {
//        startNextActivity(TeacherMainActivity.class);
//    }
//
//    public void toParentIndex(Context context) {
//        startNextActivity(ParentMainActivity.class);
//    }

//    public void changeTeacherIdentity() {
//        for (int i = 0; i < activitys.size(); i++) {
//            if (i == 0) continue;
//            popActivity(activitys.get(i));
//        }
//        ActivityManager.getInstance().startNextActivity(TeacherMainActivity.class, true);
//    }
//
//    public void changeParentIdentity() {
//        for (int i = 0; i < activitys.size(); i++) {
//            if (i == 0) continue;
//            popActivity(activitys.get(i));
//        }
//        ActivityManager.getInstance().startNextActivity(ParentMainActivity.class, true);
//        UserManager.setSchoolId("");
//    }
//    public void createOtherIdentity() {
//        for (int i = 0; i < activitys.size(); i++) {
//            if (i == 0) continue;
//            popActivity(activitys.get(i));
//        }
//        ActivityManager.getInstance().startNextActivity(SelectIdentityActivity.class, true);
//        UserManager.setSchoolId("");
//    }

//    public void toPhotoBrowser(List<PhotoBean> photoBeanList, int position) {
//        ArrayList<PhotoBean> arrayList = new ArrayList<>();
//        arrayList.addAll(photoBeanList);
//        if (arrayList.get(arrayList.size() - 1).isAdd) {
//            arrayList.remove(arrayList.size() - 1);
//        }
//        Intent intent = new Intent(currentActivity(), PhotoBrowserActivity.class);
//        intent.putExtra("photoList", arrayList);
//        intent.putExtra("position", position);
//        startNextActivity(intent);
//    }
//
    public void toWebActivity(String url,String html) {
        Intent intent = new Intent(currentActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("html", html);
        startNextActivity(intent);
    }
//
//
//    //跳播放
//    public void toPlayVideo(String videoUrl) {
//        Intent intent = new Intent(currentActivity(), VideoPlayActivity.class);
//        intent.putExtra("videoUrl", getVideoUrl(videoUrl));
//        startNextActivity(intent);
//    }
//
//    //跳播放
//    public void toPlayVideo(File videoFile) {
//        Intent intent = new Intent(currentActivity(), VideoPlayActivity.class);
//        intent.putExtra("videoFile", videoFile);
//        startNextActivity(intent);
//    }
//
//    /**
//     * 跳转LOGO选择页面
//     *
//     * @param context
//     * @param type    1-老师、2-学生、3-班级、4-课内加分项、5-课内减分项、6-课内小组
//     */
//    public void toLogoSelectorPager(Context context, int type) {
//        Intent intent = new Intent(context, LogoSelectorActivity.class);
//        intent.putExtra("typeId", type);
//        startNextActivityForResult(intent, Constants.LOGO_SELECT_ACTIVITY_REQUEST_CODE);
//    }
//
//    /**
//     * 跳转LOGO选择页面
//     *
//     * @param context
//     * @param type    1-老师、2-学生、3-班级、4-课内加分项、5-课内减分项、6-课内小组
//     */
//    public void toLogoSelectorPager(Context context, int type, int requestCode) {
//        Intent intent = new Intent(context, LogoSelectorActivity.class);
//        intent.putExtra("typeId", type);
//        startNextActivityForResult(intent, requestCode);
//    }
}
