package com.flym.fhzk.http;

import android.util.Log;

import com.flym.fhzk.data.service.Api;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.ApiConstants;
import com.flym.fhzk.utils.Constants;
import com.flym.fhzk.utils.LogUtils;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Morphine on 2017/6/12.
 */

public class RetrofitUtil {

    private static OkHttpClient okHttpClient;
    private static Retrofit.Builder mBuilder;

    private RetrofitUtil() {

    }

    public static <T> T getService(Class<T> t) {
        if (mBuilder == null) {
            initBuilder(null);
        }
        return mBuilder.baseUrl(ApiConstants.BASE_URL)
                .client(okHttpClient)
                .build().create(t);
    }


    public static Api getApiService() {
        return getService(Api.class);
    }


    private static void initBuilder(Retrofit.Builder builder) {
        if (builder == null) {
            if (okHttpClient == null) {
                initOkHttpClient(null);
            }
            mBuilder = new Retrofit.Builder();
            mBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
        } else {
            mBuilder = builder;
        }
    }

    private static void initOkHttpClient(OkHttpClient client) {
        if (client == null) {
            CookieStore store = new PersistentCookieStore(ActivityManager.getInstance().currentActivity().getApplicationContext());
            CookieHandler cookieHandler = new CookieManager(store, CookiePolicy.ACCEPT_ALL);
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(Constants.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(Constants.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                    .connectionPool(new ConnectionPool(Constants.HTTP_MAX_CONNECT_COUNT,
                            Constants.HTTP_KEEP_ALIVE_CONNECT_COUNT, TimeUnit.MINUTES))
                    .addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            if (LogUtils.APP_DBG) {
                                Log.e("okHttp", message);
                            }
                        }
                    }).setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(new TokenInterceptor())
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .build();
        } else {
            okHttpClient = client;
        }
    }
}
