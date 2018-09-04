package com.flym.yh.utils;

import android.annotation.SuppressLint;
import android.media.ThumbnailUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.flym.yh.MyApplication;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:mengl
 * Time:2018/4/13 ${TIEM}
 */
public class ALiYunUtils {
    private final static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private final static String bucketName = "wenzhenzaixian";
    public final static String url = "http://wenzhenzaixian.oss-cn-beijing.aliyuncs.com/";

    private static OSSAsyncTask task;

    public static void aLiYunUpLoad(final String objectKey,
                                    final String picturePath,
                                    final UploadAliayCall uploadAliayCall1) {
        Flowable.create( new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(final FlowableEmitter<String> e) throws Exception {
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(
                        "LTAIq30TbumkL0QN", "6caeQXNiZdNCbrB9WRNxN3ZqdkOSzO");
                OSS oss = new OSSClient(MyApplication.mContext, endpoint, credentialProvider, conf);
                // 构造上传请求  http://langduba.oss-cn-shenzhen.aliyuncs.com/Video/2017-09-21/1505966695.mp4
                PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, picturePath);
                // 异步上传时可以设置进度回调
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

                    }
                });
                task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        String uploadFilePath = request.getUploadFilePath();
                        e.onNext("1");
                        Log.d("tag", "onSuccess: "+uploadFilePath);

                    }

                    @Override
                    public void onFailure(PutObjectRequest request,
                                          ClientException clientExcepion,
                                          ServiceException serviceException) {
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();

                            e.onError(clientExcepion);
                        }
                        if (serviceException != null) {
                            e.onError(serviceException);

                            // 服务异常
                            Log.e("lsq  -->", serviceException.getErrorCode());
                            Log.e("lsq  -->", serviceException.getRequestId());
                            Log.e("lsq  -->", serviceException.getHostId());
                            Log.e("lsq  -->", serviceException.getRawMessage());
                        }
                    }
                });

            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("tag", "onNext: "+s);
                        if (uploadAliayCall1 != null) {
                            uploadAliayCall1.succ();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof ClientException) {
                            if (uploadAliayCall1 != null) {

                                uploadAliayCall1.error(t.getMessage());
                            }
                        } else if (t instanceof ServiceException) {

                            if (uploadAliayCall1 != null) {
                                uploadAliayCall1.error(((ServiceException) t).getRawMessage());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    public interface UploadAliayCall {
        void setView(int f);

        void succ();

        void error(String message);
    }
}

