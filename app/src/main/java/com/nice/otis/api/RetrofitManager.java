package com.nice.otis.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/***
 * Created by Administrator on 2017/7/5.
 */

public class RetrofitManager {
    private static final int DEFAULT_TIMEOUT = 500;

    private Retrofit mRetrofit2Object;
    private Retrofit mRetrofit2String;

    private volatile static RetrofitManager sRetrofitManager;
    private OkHttpClient.Builder mClientBuilder;

    private RetrofitManager() {

        //手动创建一个OkHttpClient并设置超时时间
        mClientBuilder = new OkHttpClient.Builder();
        mClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit2String = initRetrofit2String();
        mRetrofit2Object = initRetrofit2Object();
    }
    public  OkHttpClient.Builder getHttpClient(){
        return mClientBuilder;
    }

    public static RetrofitManager getInstance() {
        if (sRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (sRetrofitManager == null) {
                    sRetrofitManager = new RetrofitManager();
                }
            }
        }
        return sRetrofitManager;
    }

    public Retrofit getRetrofit(RetrofitType type) {
        switch (type) {
            case String:
                if (mRetrofit2String == null) {
                    mRetrofit2String = initRetrofit2String();
                }
                return mRetrofit2String;
            case Object:
                if (mRetrofit2Object == null) {
                    mRetrofit2Object = initRetrofit2Object();
                }
                return mRetrofit2Object;
        }
        return null;
    }

    private Retrofit initRetrofit2String() {

        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(mClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private Retrofit initRetrofit2Object() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(mClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public enum RetrofitType {
        String, Object
    }

    /**
     * 用于将Observable和Subscriber的绑定
     *  @param <T>
     * @param observable
     * @param subscriber
     */
    public static <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
