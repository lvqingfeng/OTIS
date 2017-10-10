package com.nice.otis.api;

/**
 * Created by liukun on 16/3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T result);
}
