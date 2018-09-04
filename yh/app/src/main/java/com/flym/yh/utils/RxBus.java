package com.flym.yh.utils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class RxBus {
    private static RxBus bus;
    private final Subject<Object> subject = PublishSubject.create().toSerialized();
    private Disposable disposable;

    private RxBus() {
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RxBus getInstance()

    {
        if (bus == null) {
            synchronized (RxBus.class) {
                if (bus == null) {
                    bus = new RxBus();
                }
            }
        }
        return bus;
    }

    /**
     * 发送事件
     *
     * @param obj
     */
    public void send(Object obj) {
        subject.onNext(obj);
    }

    /**
     * 订阅
     *
     * @param bean
     * @param consumer
     */
    public <T> void subscribe(Class<T> bean, Consumer<T> consumer) {
        disposable = toObservable(bean).subscribe(consumer);
    }

    /**
     * 取消订阅
     */
    public void unSubscribe(){
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    /**
     * 创建对应数据类型的Observable
     *
     * @param bean
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> bean) {
        return subject.ofType(bean);
    }


}
