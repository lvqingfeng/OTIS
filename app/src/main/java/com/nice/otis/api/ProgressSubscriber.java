package com.nice.otis.api;

import android.content.Context;
import android.widget.Toast;

import com.nice.otis.base.HttpResult;
import com.nice.otis.utils.DialogUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends Subscriber<T> {

    private SubscriberOnNextListener<T> mSubscriberOnNextListener;

    private Context context;

    public ProgressSubscriber(Context context, SubscriberOnNextListener<T> mSubscriberOnNextListener) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
    }

    private void showProgressDialog() {
        DialogUtils.showProgressDialog(context, "数据加载中...", "", "");
    }

    private void dismissProgressDialog(DialogUtils.LoadCompleteType type) {
        DialogUtils.hideDialog(type);
        onCancelProgress();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog(DialogUtils.LoadCompleteType.Success);
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        System.out.println("----错误信息 = " + e.getMessage());
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络请求超时，请重试", Toast.LENGTH_SHORT).show();
            dismissProgressDialog(DialogUtils.LoadCompleteType.Error);
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
            dismissProgressDialog(DialogUtils.LoadCompleteType.Error);
        } else if (e instanceof NullPointerException) {
            if (mSubscriberOnNextListener != null){
                mSubscriberOnNextListener.onNext(null);
                dismissProgressDialog(DialogUtils.LoadCompleteType.Error);
            }
        } else {
            Toast.makeText(context,"数据缺失", Toast.LENGTH_SHORT).show();
            dismissProgressDialog(DialogUtils.LoadCompleteType.Error);
        }

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            if (t instanceof HttpResult) {
                String status = ((HttpResult) t).getCode();
                switch (status) {
                    case ApiService.STATUS_SUC:
                        mSubscriberOnNextListener.onNext(t);
                        break;
                    case ApiService.TOKEN_EX:
                        Toast.makeText(context, "登录信息异常，请重新登陆", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, ((HttpResult) t).getMsg().toString(), Toast.LENGTH_SHORT).show();
                        break;

                }
            } else {
                mSubscriberOnNextListener.onNext(t);
            }

        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}