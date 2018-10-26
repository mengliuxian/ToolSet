package com.flym.fhzk.utils;

/**
 * Created by Morphine on 2017/9/2.
 */

public class ShareUtil {


//    public static void shareText(Activity activity, String text) {
//        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN).withText(text).open();
//    }

//
//    public static void shareUrl(Activity activity, String title, String description, String url, String thumb) {
//        ShareUtil.shareUrl(activity, title, description, url, thumb, null);
//    }


//    public static void shareUrl(Activity activity, String title, String description, String url, String thumb, final OnShareSuccessListener listener) {
//        UMWeb umWeb = new UMWeb(url);
//        umWeb.setTitle(title);
//        if (TextUtils.isEmpty(thumb)) {
//            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.simple);
//            umWeb.setThumb(new UMImage(context, bitmap));
//        } else {
//            umWeb.setThumb(new UMImage(context, thumb));
//        }
//
//        umWeb.setDescription(description);
//        new ShareAction(activity)
//                .withMedia(umWeb)
//                .setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//                        if (listener != null) listener.onShareSuccess();
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//
//                    }
//                }).setDisplayList(SHARE_MEDIA.QQ,
//                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE).open();
//    }


    public interface OnShareSuccessListener {
        void onShareSuccess();
    }

}
