package com.nice.otis.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * 作者：吕振鹏
 * 创建时间：06月30日
 * 时间：11:42
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class Sdk16 {


    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }

    }

}
