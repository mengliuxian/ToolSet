package com.flym.yh.utils;

import com.flym.yh.MyApplication;
import com.flym.yh.data.model.GetOpenIdBean;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.opensdk.modelmsg.WXTextObject;
//import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by Morphine on 2017/9/2.
 */

public class WxLoginAndShareUtil {
    public static final String ACTION_SHARE_RESPONSE = "action_wx_share_response";
    public static final String EXTRA_RESULT = "result";

    public static OnWxCallBackListener listeners;

    public static void wxLogin(OnWxCallBackListener listener) {
        listeners = listener;
        // 将该app注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(MyApplication.mContext, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        if (!api.isWXAppInstalled()) {
            if (listeners != null) {
                listeners.onFail("微信客户端未安装");
            }
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        api.sendReq(req);

    }


    //    private final Context context;
//    private final IWXAPI api;
//    private OnResponseListener listener;
//    private ResponseReceiver receiver;
//
//    public WxLoginAndShareUtil(Context context) {
//        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
//        this.context = context;
//    }
//
//
//    public void share(String text) {
//        if (!api.isWXAppInstalled()) {
//            ToastUtil.showMessage(context, context.getString(R.string.not_install_weixin));
//            return;
//        }
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = text;
//
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObj;
//        //        msg.title = "Will be ignored";
//        msg.description = text;
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("text");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
//
//        boolean result = api.sendReq(req);
//
//    }
//
//    public void shareUrl(String url, String title, String description, String tag, boolean isLine) {
//        WXWebpageObject wxWebpage = new WXWebpageObject();
//        wxWebpage.webpageUrl = url;
//        WXMediaMessage msg = new WXMediaMessage(wxWebpage);
//        //网页标题
//        msg.title = title;
//        //网页描述
//        msg.description = description;
//        //缩略图
////        msg.setThumbImage();
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.message = msg;
//        //发送朋友圈
//        req.scene = isLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
//        //发送朋友
////        req.scene = SendMessageToWX.Req.WXSceneSession;
//        //这个tag要唯一,用于在回调中分辨是哪个分享请求
//        req.transaction = tag;
//        api.sendReq(req);
//    }
//
//
//    public WxLoginAndShareUtil register() {
//        // 微信分享
//        api.registerApp(Constants.APP_ID);
//        receiver = new ResponseReceiver();
//        IntentFilter filter = new IntentFilter(ACTION_SHARE_RESPONSE);
//        context.registerReceiver(receiver, filter);
//        return this;
//    }
//
//    public void unregister() {
//        try {
//            api.unregisterApp();
//            context.unregisterReceiver(receiver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setListener(OnResponseListener listener) {
//        this.listener = listener;
//    }
//
//    public IWXAPI getApi() {
//        return api;
//    }
//
//    private String buildTransaction(final String type) {
//        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//    }
//
//
////
////    public static void shareText(Activity activity, String text) {
////        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN).withText(text).open();
////    }
////
////
////    public static void shareUrl(Activity activity, String title, String description, String url, String thumb) {
////        WxLoginAndShareUtil.shareUrl(activity, title, description, url, thumb, null);
////    }
////
////
////    public static void shareUrl(Activity activity, String title, String description, String url, String thumb, final OnShareSuccessListener listener) {
////        UMWeb umWeb = new UMWeb(url);
////        umWeb.setTitle(title);
////        if (TextUtils.isEmpty(thumb)) {
////            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.simple);
////            umWeb.setThumb(new UMImage(context, bitmap));
////        } else {
////            umWeb.setThumb(new UMImage(context, thumb));
////        }
////
////        umWeb.setDescription(description);
////        new ShareAction(activity)
////                .withMedia(umWeb)
////                .setCallback(new UMShareListener() {
////                    @Override
////                    public void onStart(SHARE_MEDIA share_media) {
////
////                    }
////
////                    @Override
////                    public void onResult(SHARE_MEDIA share_media) {
////                        if (listener != null) listener.onShareSuccess();
////                    }
////
////                    @Override
////                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
////
////                    }
////
////                    @Override
////                    public void onCancel(SHARE_MEDIA share_media) {
////
////                    }
////                }).setDisplayList(SHARE_MEDIA.QQ,
////                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE).open();
////    }
//
//
//    public interface OnShareSuccessListener {
//        void onShareSuccess();
//    }
//
//
//    // 判断微信是否有安装
//    public static boolean judgeIs() {
//        try {
//            PackageManager manager = MyApplication.context.getPackageManager();
//            PackageInfo info = manager.getPackageInfo("com.tencent.mm",
//                    PackageManager.GET_ACTIVITIES);
//            if (info != null) {
//                return true;
//            } else {
//
//            }
//        } catch (Exception e) {
//        }
//        return false;
//    }
//
//    // 将图片转换成字节数组--微信分享图片大小不超过32k
//    public static byte[] bmpToByteArray(final Bitmap bmp) {
//
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        int position = 100;
//        bmp.compress(Bitmap.CompressFormat.JPEG, position, output);
//        while (output.size() / 1024 >= 32) {
//            output.reset();
//            position -= 5;
//            bmp.compress(Bitmap.CompressFormat.JPEG, position, output);
//        }
//        byte[] result = output.toByteArray();
//        try {
//            output.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//    public static class Response extends BaseResp implements Parcelable {
//
//        public int errCode;
//        public String errStr;
//        public String transaction;
//        public String openId;
//
//        private int type;
//        private boolean checkResult;
//
//        public Response(BaseResp baseResp) {
//            errCode = baseResp.errCode;
//            errStr = baseResp.errStr;
//            transaction = baseResp.transaction;
//            openId = baseResp.openId;
//            type = baseResp.getType();
//            checkResult = baseResp.checkArgs();
//        }
//
//        @Override
//        public int getType() {
//            return type;
//        }
//
//        @Override
//        public boolean checkArgs() {
//            return checkResult;
//        }
//
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeInt(this.errCode);
//            dest.writeString(this.errStr);
//            dest.writeString(this.transaction);
//            dest.writeString(this.openId);
//            dest.writeInt(this.type);
//            dest.writeByte(this.checkResult ? (byte) 1 : (byte) 0);
//        }
//
//        protected Response(Parcel in) {
//            this.errCode = in.readInt();
//            this.errStr = in.readString();
//            this.transaction = in.readString();
//            this.openId = in.readString();
//            this.type = in.readInt();
//            this.checkResult = in.readByte() != 0;
//        }
//
//        public static final Creator<Response> CREATOR = new Creator<Response>() {
//            @Override
//            public Response createFromParcel(Parcel source) {
//                return new Response(source);
//            }
//
//            @Override
//            public Response[] newArray(int size) {
//                return new Response[size];
//            }
//        };
//    }
//
//    private class ResponseReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Response response = intent.getParcelableExtra(EXTRA_RESULT);
//            String result;
//            if (listener != null) {
//                if (response.errCode == BaseResp.ErrCode.ERR_OK) {
//                    listener.onSuccess();
//                } else if (response.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
//                    listener.onCancel();
//                } else {
//                    switch (response.errCode) {
//                        case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                            result = "发送被拒绝";
//                            break;
//                        case BaseResp.ErrCode.ERR_UNSUPPORT:
//                            result = "不支持错误";
//                            break;
//                        default:
//                            result = "发送返回";
//                            break;
//                    }
//                    listener.onFail(result);
//                }
//            }
//        }
//    }
//
    public interface OnWxCallBackListener {
        void onSuccess(GetOpenIdBean o);

        void onCancel();

        void onFail(String message);
    }


}
