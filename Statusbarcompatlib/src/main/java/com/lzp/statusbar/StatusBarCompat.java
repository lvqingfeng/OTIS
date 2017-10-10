package com.lzp.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzp.statusbar.utils.AndroidBug5497Workaround;
import com.lzp.statusbar.utils.ChangeStatusBarStatus;
import com.lzp.statusbar.utils.SystemUtils;
import com.lzp.statusbar.view.TitleCompatLayout;

/**
 * 作者：吕振鹏
 * 创建时间：08月19日
 * 时间：1:18
 * 版本：v1.0.0
 * 类描述：状态栏的兼容类，可以兼容到4.4以上的版本
 * 修改时间：
 * <p>
 * ************使用方式***********
 * 》》》》》》特别注意，主题要设置为NoActionBar类型》》》》》
 * <p>
 * 1.在你原有的标题栏布局中包裹上TitleCompatLayout布局（就把它看作是一个包裹的布局就行，你原有的标题栏根本不用变）
 * 2.为它设置上两个属性，必须设置这个两个属性（1.宽高分别设置为MATCH_PARENT和WRAP_CONTENT,2.设置背景颜色，一定要给它设置上背景颜色,当然如果不设置的话，会显示默认的）
 * 3.在java代码中直接调用静态方法 StatusBarCompat.setStatusBarColor(this, titleCompatLayout);
 * <p>
 * ************结束方式***********
 */
public class StatusBarCompat {

    private static final String TAG = StatusBarCompat.class.toString();


    public static void setStatusBarColor(Activity activity, TitleCompatLayout titleLayout, boolean isAddStatusBar) {

        if (activity == null) {
            Log.e(TAG, "activity or titleLayout null");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);
            AndroidBug5497Workaround.assistActivity(activity);


            if (isAddStatusBar && titleLayout != null) {
                View statusBar = titleLayout.getChildAt(0);
                setStatusBarColor(activity, titleLayout, statusBar);
            }


        }

    }


    private static void setStatusBarColor(Activity activity, TitleCompatLayout titleLayout, View statusBar) {

        ColorDrawable drawable = (ColorDrawable) titleLayout.getBackground();
        if (drawable == null) {
            drawable = new ColorDrawable();
        }

        statusBar.getLayoutParams().height = SystemUtils.getStatusHeight(activity);
        statusBar.setLayoutParams(statusBar.getLayoutParams());
        statusBar.setBackgroundColor(drawable.getColor());

        if (drawable.getColor() >=  Color.argb(255,222,222,222) && drawable.getColor() <= 0xffffffff) {
            ChangeStatusBarStatus.setStatusBarDarkMode(activity, true);
        }

    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
