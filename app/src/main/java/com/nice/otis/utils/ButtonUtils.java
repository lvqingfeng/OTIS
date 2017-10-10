package com.nice.otis.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.nice.otis.R;


/**
 * 作者：lv
 * 创建时间：07月01日
 * 时间：16:04
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class ButtonUtils {
    /**
     * 设置按钮的背景和点击状态
     *
     * @param context 环境变量
     * @param button  点击的按钮
     * @param flag    设置的状态
     */
    public static void setButtonEnabledState(Context context, TextView button, boolean flag) {
        button.setTextColor(Color.WHITE);
        setButtonEnabledState(context, button, flag, R.drawable.bg_blue_btn, R.drawable.bg_gray_btn);

    }

    public static void setButtonEnabledState(Context context, View button, boolean isEnabled, int enabledOkRes, int enabledNoRes) {

        button.setEnabled(isEnabled);
        if (isEnabled) {
            Sdk16.setBackground(button, ContextCompat.getDrawable(context, enabledOkRes));
        } else {
            Sdk16.setBackground(button, ContextCompat.getDrawable(context, enabledNoRes));
        }

    }

}
