package com.flym.yh;


import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.flym.yh.base.CrashHandler;
import com.flym.yh.utils.LogUtils;
import com.flym.yh.utils.UserManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by mengl on 2018/3/19.
 */

public class MyApplication extends MultiDexApplication {
    public static int flag = -1;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        LogUtils.init(this);
        UserManager.init(this);
        mContext = getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        EMOptions emOptions = new EMOptions();
//        emOptions.setAppKey("1123180425228479#fs-yufan");
        emOptions.setAcceptInvitationAlways(false);
        emOptions.setAutoLogin(true);
        emOptions.setRequireAck(true);
        EaseUI.getInstance().init(getApplicationContext(),emOptions);


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //程序终止时执行
        mContext = null;
    }

}
