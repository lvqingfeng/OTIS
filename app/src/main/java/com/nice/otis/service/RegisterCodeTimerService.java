package com.nice.otis.service;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.nice.otis.R;


/**
 * 注册验证码计时服务
 * <p>
 * <p>
 * ****使用步骤*******
 * 第一、首先设置要倒计时的控件，调用setCountDownView（）;
 * 第二、在需要开启倒计时的地方，调用RegisterCodeTimerService中的startService方法
 * 第三、当页面消失的时候需要调用onActivityFinish();
 * <p>
 * *******一定要注意**********
 * 在使用之前一定要进行注册该服务：示例
 * <!-- 注册倒计时的服务 -->
 * <service
 * android:name=".service.RegisterCodeTimerService"
 * android:enabled="true" />
 */
public class RegisterCodeTimerService extends Service {

    private static int DEFAULT_TIME = 30000;
    private static Intent mIntent;

    private static Handler mHandler;
    private static RegisterCodeTimer mCodeTimer;

    private static View mSendCodeView;

    /**
     * 获取用来倒计时的具体实例
     * 就是一个线程
     *
     * @return
     */
    public static RegisterCodeTimer getRegisterCodeTimer() {
        return mCodeTimer;
    }

    public static void startService(Activity activity, int beginBackgroundRes) {
        getIntent(activity);
        mSendCodeView.setEnabled(false);
        mSendCodeView.setBackgroundResource(beginBackgroundRes);
        activity.startService(mIntent);
    }

    /**
     * 获取这个服务中的Intent
     *
     * @param context
     * @return
     */
    private static Intent getIntent(Context context) {
        if (mIntent == null) {
            mIntent = new Intent(context, RegisterCodeTimerService.class);
        }
        return mIntent;
    }

    /**
     * 设置停止倒计时
     */
    public static void onActivityFinish() {
        if (mCodeTimer != null) {
            mCodeTimer.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        mCodeTimer = new RegisterCodeTimer(DEFAULT_TIME, 1000, mHandler);
        mCodeTimer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        mCodeTimer.onFinish();
        super.onDestroy();
    }


    /**
     * 设置要进行倒计时的控件
     *
     * @param view                 具体倒计时的控件
     * @param endTimeBackgroundRes 结束倒计时后的背景资源
     */
    public static void setCountDownView(View view, final int endTimeBackgroundRes) {

        if (view instanceof TextView) {

            final TextView textView = (TextView) view;
            mSendCodeView = textView;
            /**
             * 倒计时Handler
             */
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
                        textView.setText(msg.obj.toString());
                        textView.setBackgroundResource(R.drawable.bg_blue_fillet);
                    } else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
                        textView.setEnabled(true);
                        textView.setText(msg.obj.toString());
                        textView.setBackgroundResource(endTimeBackgroundRes);
                    }
                }
            };
        }

    }

    /**
     * 设置要倒计时的时间
     *
     * @param time
     */
    public static void setCountDownTime(int time) {
        DEFAULT_TIME = time;
    }

}
