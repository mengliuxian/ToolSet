package com.flym.fhzk.http;

import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.GsonUtil;
import com.flym.fhzk.utils.PropertiesUtil;
import com.flym.fhzk.utils.SignUtil;
import com.flym.fhzk.utils.UserManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 拦截器
 * Created by Morphine on 2017/6/12.
 */

public class SimpleInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        if (chain.request().url().toString().contains("/dyb-interface/scanQRCode")) {
//            return chain.proceed(chain.request());
//        }
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Request.Builder newBuilder = request.newBuilder();
        Map<String, Object> map = new HashMap<>();
        if (UserManager.isLogin()) {
            map.put("token", UserManager.getToken(ActivityManager.getInstance().currentActivity()));
        }
        map.put("appKey", PropertiesUtil.getAppKey());
        map.put("time", String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign", SignUtil.getSign(map));
//        Random random = new Random();
//        map.put("noncestr", random.nextInt(999999 - 100000 + 1) + 100000 + "");
        if (requestBody instanceof FormBody) {
            //FormBody:指定Content-Type为application/x-www-form-urlencoded
            FormBody.Builder builder = new FormBody.Builder();
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                if (formBody.value(i) != null) {
                    map.put(formBody.name(i), formBody.value(i));
                }
//                    builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
//            if (UserManager.isLogin()) {
//                formBody = builder.addEncoded("onlineSign", UserManager.getUserOnlineSign(ActivityManager.getInstance().currentActivity())).build();
//            }



            String json = GsonUtil.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

            newBuilder.method(request.method(), body);

//            newBuilder.post(formBody);

        } else if (requestBody instanceof MultipartBody) {
            MultipartBody body = (MultipartBody) requestBody;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            List<MultipartBody.Part> parts = body.parts();
            for (int i = 0; i < parts.size(); i++) {
                builder.addPart(parts.get(i));
            }
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
            builder.setType(MultipartBody.FORM);
            MultipartBody build = builder.build();
            newBuilder.method(request.method(), build);
        }
        request = newBuilder.build();
        return chain.proceed(request);
    }

}
