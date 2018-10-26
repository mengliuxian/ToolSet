package com.flym.fhzk.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();
            Log.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras : ");

            //SDK 向 JPush Server 注册所得到的注册ID
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//                StorageUtils.setProperty("registration_id",regId, MyApp.getMyApp());
                Log.d(TAG, "onReceive: 注册ID" + regId);
                //send the Registration Id to your server...

                //收到了自定义消息 Push 。
                //SDK 对自定义消息，只是传递，不会有任何界面上的展示
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                processCustomMessage(context, bundle);

                //收到了通知 Push。
                //如果通知的内容为空，则在通知栏上不会展示通知。
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.i(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                printBundle(bundle);

                //用户点击了通知。 一般情况下，用户不需要配置此 receiver action。
                //如果开发者在 AndroidManifest.xml 里未配置此 receiver action，那么，SDK 会默认打开应用程序的主 Activity，
                // 相当于用户点击桌面图标的效果。
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 用户点击打开了通知");
                //跳转到订单管理页面

//                context.startActivity(new Intent(context, MessageRemindActivity.class));

                //打开自定义的Activity
//                Intent i = new Intent(context, TestActivity.class);
//                i.putExtras(bundle);
//                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                context.startActivity(i);

                //用户点击了通知栏中自定义的按钮。(SDK 3.0.0 以上版本支持)
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

                //JPush 服务的连接状态发生变化
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.i(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            Log.e("ll", e.toString());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {

                    continue;
                }

                try {
                    JSONObject json = null;

                    json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));

                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {

                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        Log.i(TAG, "printBundle: "+sb.toString());
        return sb.toString();
    }

}
