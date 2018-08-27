package com.flym.yh.net;


import com.flym.yh.MyApplication;
import com.flym.yh.data.service.Api;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.Constants;
import com.flym.yh.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 */

public class RetrofitUtil {

    private static OkHttpClient okHttpClient;
    private static Retrofit.Builder mBuilder;
    private static HttpLogger httpLogger = new HttpLogger();
    private static String CACHE_NAME = "Cache";


    private RetrofitUtil() {

    }

    public static <T> T getService(Class<T> t) {
        if (mBuilder == null) {
            initBuilder(null);
        }
        return mBuilder.baseUrl(ApiConstants.BASE_URL).client(okHttpClient).build().create(t);
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
            mBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()).addConverterFactory(GsonConverterFactory.create());
        } else {
            mBuilder = builder;
        }
    }

    private static void initOkHttpClient(OkHttpClient client) {
        if (client == null) {
            /**
             * 设置缓存
             */
            File cacheFile = new File(MyApplication.mContext.getExternalCacheDir(), CACHE_NAME);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetWorkUtils.isNetworkConnected()) {
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).url(chain.request().url()).build();
                    }
                    Response response = chain.proceed(request);
                    if (NetWorkUtils.isNetworkConnected()) {
                        int maxAge = 0;
                        // 有网络时 设置缓存超时时间0个小时
                        response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).removeHeader(CACHE_NAME)// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .build();
                    } else {
                        // 无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).removeHeader(CACHE_NAME).build();
                    }
                    return response;
                }
            };

            CookieStore store = new PersistentCookieStore(ActivityManager.getInstance().currentActivity().getApplicationContext());
            CookieHandler cookieHandler = new CookieManager(store, CookiePolicy.ACCEPT_ALL);
            okHttpClient = new OkHttpClient.Builder().
                    cache(cache).addInterceptor(cacheInterceptor) //缓存
                    .connectTimeout(Constants.HTTP_TIME_OUT, TimeUnit.MILLISECONDS).readTimeout(Constants.HTTP_TIME_OUT, TimeUnit.MILLISECONDS).connectionPool(new ConnectionPool(Constants.HTTP_MAX_CONNECT_COUNT, Constants.HTTP_KEEP_ALIVE_CONNECT_COUNT, TimeUnit.MINUTES)).retryOnConnectionFailure(true).addNetworkInterceptor(new HttpLoggingInterceptor(httpLogger).setLevel(HttpLoggingInterceptor.Level.BODY)).addInterceptor(new TokenInterceptor()).cookieJar(new JavaNetCookieJar(cookieHandler)).build();
            setCertificates();
        } else {
            okHttpClient = client;
        }


    }

    private static void setCertificates() {
        try {
            //自签名证书路径
            InputStream open = MyApplication.mContext.getAssets().open("srca.cer");

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(open);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (Certificate certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificate);
            }

            try {
                if (open != null) open.close();
            } catch (IOException e) {
            }


            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            //初始化keystore(双向验证)
//                KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//                clientKeyStore.load(mContext.getAssets().open("zhy_client.jks"), "123456".toCharArray());
//
//                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//                keyManagerFactory.init(clientKeyStore, "123456".toCharArray());
//
//                sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            okHttpClient = okHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0]).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    //判断网络地址是否相同
                    //例如：“https//:aaaa,com”, “https//:bbb.com”

                    return false;
                }
            }).build();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
