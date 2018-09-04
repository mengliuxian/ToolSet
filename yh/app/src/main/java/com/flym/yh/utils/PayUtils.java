package com.flym.yh.utils;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class PayUtils {

//    public static PayResultListence listences;
//    private static Dialog dialog;
//
//    public static void weixinPay(Context context, PayResultListence listence) {
//        listences = listence;
//        //微信调起支付
//        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, "wx8b61e20ececca1ae");
//        iwxapi.registerApp("wx8b61e20ececca1ae");
//        PayReq request = new PayReq();
//        request.appId = "wxd930ea5d5a258f4f";
//        request.partnerId = "1900000109";
//        request.prepayId = "1101000000140415649af9fc314aa427";
//        request.packageValue = "Sign=WXPay";
//        request.nonceStr = "1101000000140429eb40476f8896f4c9";
//        request.timeStamp = "1398746574";
//        request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//        iwxapi.sendReq(request);
//    }
//
//    public static void aliPay(final Activity activity, String info, final PayResultListence listence) {
//
//
//        Flowable.just(info)
//                .flatMap(new Function<String, Publisher<Map<String, String>>>() {
//                    @Override
//                    public Publisher<Map<String, String>> apply(@NonNull String s) throws Exception {
//                        PayTask alipay = new PayTask(activity);
//                        Map<String, String> result = alipay.payV2(s, true);
//                        return Flowable.just(result);
//
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Map<String, String>>() {
//                    @Override
//                    public void accept(@NonNull Map<String, String> stringStringMap) throws Exception {
//                        PayResult payResult = new PayResult(stringStringMap);
//                        //同步获取结果
//                        String resultInfo = payResult.getResult();
//                        final String resultStatus = payResult.getResultStatus();
//                        // 判断resultStatus 为9000则代表支付成功
//                        if (TextUtils.equals(resultStatus, "9000")) {
//                            Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
//                            dialog = CustomDialog.HintNoTitleLongDialog(activity, "你已支付成功，正在联系商家发货", null, new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                    listence.patResukt(resultStatus);
//                                }
//                            });
//
//                        } else {
//                            Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
//                            listence.patResukt(resultStatus);
//                        }
//                    }
//                });
//    }
//
//
//    public interface PayResultListence {
//        void patResukt(String code);
//    }

    @SuppressLint("CheckResult")
    public static void AlipayLogin(final Activity activity, final String str, final LoginCallback callback) {
        Observable.just(1)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<Integer, Map<String, String>>() {
                    @Override
                    public Map<String, String> apply(Integer integer) throws Exception {
                        Map<String, String> map = new HashMap<>();
//                        AuthTask authTask = new AuthTask(activity);
//                        Map<String, String> map = authTask.authV2(str, true);
                        return map;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> stringStringMap) throws Exception {
                        if (stringStringMap.get("resultStatus").equals("9000")) {
                            callback.onLoginSuccess(stringStringMap.get("result"));
                        } else {
                            callback.onLoginFail();
                        }
                    }
                });
    }

    public interface LoginCallback {
        void onLoginSuccess(String s);

        void onLoginFail();
    }
}
