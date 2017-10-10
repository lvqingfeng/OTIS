package com.nice.otis.service;

import android.os.CountDownTimer;
import android.os.Handler;

/**
 * 注册验证码计时器
 */
public class RegisterCodeTimer extends CountDownTimer {

    private static Handler mHandler;
    public static final int IN_RUNNING = 1001;
    public static int END_RUNNING = 1002;
    public long mMillisUntilFinished = 0;

    /**
     * @param millisInFuture    // 倒计时的时长
     * @param countDownInterval // 间隔时间
     * @param handler           // 通知进度的Handler
     */
    public RegisterCodeTimer(long millisInFuture, long countDownInterval,
                             Handler handler) {
        super(millisInFuture, countDownInterval);
        mMillisUntilFinished = millisInFuture;
        mHandler = handler;
    }

    // 结束
    @Override
    public void onFinish() {
        mMillisUntilFinished = -1;
        // TODO Auto-generated method stub
        if (mHandler != null)
            mHandler.obtainMessage(END_RUNNING, "重新发送").sendToTarget();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // TODO Auto-generated method stub
        mMillisUntilFinished = millisUntilFinished;
        if (mHandler != null)
            mHandler.obtainMessage(IN_RUNNING,
                    (millisUntilFinished / 1000) + "s 后重新发送").sendToTarget();
    }

}
