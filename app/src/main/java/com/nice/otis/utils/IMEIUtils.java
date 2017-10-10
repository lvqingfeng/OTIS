package com.nice.otis.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 作者:吕清锋
 * 时间:2017/8/13
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class IMEIUtils {
    /**
     * 获取手机IMEI号
     * 需要动态权限: android.permission.READ_PHONE_STATE
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }
}
