package com.flym.fhzk.http;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.ToastUtil;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Morphine on 2017/6/12.
 */

public class SimpleTransFormer<T> implements FlowableTransformer<SimpleResponse<T>, T> {

    Class clazz;
    T list;

    public SimpleTransFormer(Class clazz) {
        this.clazz = clazz;

    }

    public SimpleTransFormer(T list) {
        this.list = list;
    }

    @Override
    public Publisher<T> apply(Flowable<SimpleResponse<T>> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .flatMap(new Function<SimpleResponse<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(@NonNull final SimpleResponse<T> tSimpleResponse) throws Exception {

                        T data = tSimpleResponse.datas;
                        if (data == null) {
                            if (list != null) {
                                data = list;
                            } else {
                                {
                                    //创建类的新的实例
                                    data = (T) clazz.newInstance();
                                }
                            }
                        }
                        return Flowable.just(data).lift(new FlowableOperator<T, T>() {
                            @Override
                            public Subscriber<? super T> apply(Subscriber<? super T> observer) throws Exception {
                                if (TextUtils.isEmpty(tSimpleResponse.result)) {

                                    tSimpleResponse.result = "3";
                                }
                                if ("1".equals(tSimpleResponse.result)) {
                                    final Activity activity = ActivityManager.getInstance().currentActivity();
                                    if (activity != null) {
                                        {
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showMessage(activity, tSimpleResponse.message);
                                                }
                                            });
                                        }
                                    }
                                }
                                if (Integer.parseInt(tSimpleResponse.result) > 2) {
                                    observer.onError(new ResponseError(tSimpleResponse.result, tSimpleResponse.message));
                                }
                                return observer;
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
