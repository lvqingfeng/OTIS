package com.nice.otis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.nice.otis.R;


/**
 * 自定义popupWindow
 *
 * @author lvp
 */
public class AddPopWindow extends PopupWindow {
    private View conentView;
    protected Context mContext;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     * @param resId   自定义的布局文件
     */
    public AddPopWindow(final Activity context, int resId) {
        mContext = context;
        /**
         * 	加载自定义布局
         */
        //初始化布局加载器
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(resId, null);


        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = context.getWindowManager();
        manager.getDefaultDisplay().getMetrics(metrics);

        //获取屏幕的宽和高
        int h = metrics.heightPixels - com.lzp.statusbar.utils.SystemUtils.getStatusHeight(context);
        int w = metrics.widthPixels;

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        //this.setHeight(h);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //软盘弹出时，显示的效果
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 刷新状态
        this.update();

        // 实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        setAnimationStyle(R.style.PopWindow_y_anim_style);
        //this.setAnimationStyle(android.R.style.Animation_Translucent);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimationPreview);
    }

    /**
     * 获取自定义到PopupWindow中的布局
     *
     * @return
     */
    public View getWindowRootView() {
        return conentView;
    }

    /**
     * 显示popupWindow
     * 必须调用这个方法才能显示出来PopupWindow
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    /**
     * 关闭PopupWindow
     */
    public void closePopupWindow() {
        if (isShowing())
            dismiss();
    }

}
