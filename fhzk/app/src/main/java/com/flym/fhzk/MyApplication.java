package com.flym.fhzk;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.flym.fhzk.base.CrashHandler;
import com.flym.fhzk.utils.LogUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

//import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
//import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;


/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class MyApplication extends MultiDexApplication {

    private static final String TAG = "MyApplication";

    public static int flag = -1;
    private static UMShareAPI umShareAPI;

    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        LogUtils.init(this);
        CrashHandler.getInstance().init(this);
        //初始化友盟

        umShareAPI = UMShareAPI.get(this);
        Config.DEBUG = true;
        //微信和QQ开放平台对应的AppId和Appkey
        //微信
        PlatformConfig.setWeixin("wxd38018323f9d0403", "e72bb69b9f219e8da2436c83bb35af6c");
        //QQ
        PlatformConfig.setQQZone("1106581025", "gQYEV1s90La4wMDd");
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                // 是否使用支付宝
                AlibcTradeSDK.setShouldUseAlipay(true);

                // 设置是否使用同步淘客打点
                AlibcTradeSDK.setSyncForTaoke(false);

                // 是否走强制H5的逻辑，为true时全部页面均为H5打开
                AlibcTradeSDK.setForceH5(true);
                Log.e(TAG, "onSuccess: AlibcTradeSDK" );
                // 设置全局淘客参数，方便开发者用同一个淘客参数，不需要在show接口重复传入
//                Alibct alibcTaokeParams = new AlibcTaokeParams("mm_118409394_42164435_211718364", "", "");
//                AlibcTradeSDK.setTaokeParams(alibcTaokeParams);

            }

            @Override
            public void onFailure(int i, String s) {
                Log.e(TAG, "onFailure: "+s );
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static UMShareAPI getUmShareAPI() {
        return umShareAPI;
    }
}
