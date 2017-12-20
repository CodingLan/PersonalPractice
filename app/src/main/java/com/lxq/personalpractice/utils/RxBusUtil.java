package com.lxq.personalpractice.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by lxq_workspace on 2017/12/20.
 */

public class RxBusUtil {
    private static volatile RxBusUtil INSTANCE;

    public static RxBusUtil getDefault() {
        if (INSTANCE == null) {
            synchronized (RxBusUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RxBusUtil();
                }
            }
        }

        return INSTANCE;
    }

    private final Subject<Object> bus = PublishSubject.create()
                                                      .toSerialized();

    public void send(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    /**
     * 是否有订阅者
     * @return
     */
    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
