package com.flym.fhzk.http;

import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.data.model.User;
import com.flym.fhzk.data.service.Api;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.ApiConstants;
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
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Administrator
 * @date 2017/12/14 0014
 */

public class TokenInterceptor implements Interceptor {

    public final String TOKEN_EXPIRED = "100003";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();
        Request.Builder builder = request.newBuilder();
        HashMap<String, Object> map = new HashMap<>();
        if (UserManager.isLogin()) {
            map.put("token", UserManager.getToken(ActivityManager.getInstance().currentActivity()));
        }
        map.put("sign", SignUtil.getSign(map));
        map.put("appKey", PropertiesUtil.getAppKey());
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            for (int i = 0; i < formBody.size(); i++) {
                if (formBody.value(i) != null) {
                    map.put(formBody.name(i), formBody.value(i));
                }
            }

            String json = GsonUtil.toJson(map);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            builder.method(request.method(), requestBody);
        } else {
            if (body instanceof MultipartBody) {
                MultipartBody multipartBody = (MultipartBody) body;
                MultipartBody.Builder mBuilder = new MultipartBody.Builder();
                List<MultipartBody.Part> parts = multipartBody.parts();
                for (int i = 0; i < parts.size(); i++) {
                    mBuilder.addPart(parts.get(i));
                }
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    mBuilder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
                mBuilder.setType(MultipartBody.FORM);
                MultipartBody multipartBody1 = mBuilder.build();
                builder.method(request.method(), multipartBody1);

            }
        }

        request = builder.build();

//        Response proceed = chain.proceed(request);
//        String string = proceed.body().string();


//        Log.d("TAG", "TokenInterceptor: " );
//        try {
//            JSONObject jsonObject = new JSONObject(string);
//            String code = jsonObject.getString("result");
//            if (TOKEN_EXPIRED.equals(code)) {
//                String newToken = getNewToken();
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return chain.proceed(request);
    }


    public String getNewToken() {
        User user = UserManager.getUser(ActivityManager.getInstance().currentActivity());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .build();
        Api api = retrofit.create(Api.class);
        Call<SimpleResponse<SimpleString>> call = api.token(user.username, user.userpass, "android");
        try {
            SimpleResponse<SimpleString> body = call.execute().body();
            return body.datas.string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
