package com.nice.otis.utils;

import android.content.Context;
import android.os.CountDownTimer;

import com.nice.otis.view.LoadingDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Administrator on 2017/7/5.
 */

public class DialogUtils {
    private static LoadingDialog sSweetAlertDialog;//计时器
    private static CountDownTimer sTimer;//计时器
    private static LoadCompleteType sLoadCompleteType;//加载类型

    public static SweetAlertDialog alertDialog(Context context, String titleText, String messageText,
                                               SweetAlertDialog.OnSweetClickListener cancelListener, SweetAlertDialog.OnSweetClickListener confirmListener) {

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titleText)
                .setContentText(messageText)
                .setCancelText("取消")
                .setConfirmText("确认")
                .showCancelButton(true)
                .setCancelClickListener(cancelListener)
                .setConfirmClickListener(confirmListener);
        dialog.show();
        dialog.setCancelable(true);
        return dialog;
    }

    public static SweetAlertDialog alertDialogNormal(Context context, String titleText, String messageText,
                                               SweetAlertDialog.OnSweetClickListener cancelListener, SweetAlertDialog.OnSweetClickListener confirmListener) {

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(titleText)
                .setContentText(messageText)
                .setCancelText("取消")
                .setConfirmText("确认")
                .showCancelButton(true)
                .setCancelClickListener(cancelListener)
                .setConfirmClickListener(confirmListener);
        dialog.show();
        dialog.setCancelable(true);
        return dialog;
    }

    public static SweetAlertDialog alertDialogNoCancel(Context context, String titleText, String messageText, SweetAlertDialog.OnSweetClickListener confirmListener) {

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titleText)
                .setContentText(messageText)
                .setConfirmText("确认")
                .showCancelButton(false)
                .setConfirmClickListener(confirmListener);
        dialog.show();
        return dialog;
    }

    public static void showProgressDialog(Context context, final String titleText, final String successText, final String errorText) {
        sSweetAlertDialog = new LoadingDialog(context);
        sSweetAlertDialog.setMessage(titleText);
        sSweetAlertDialog.show();
        sSweetAlertDialog.setCancelable(true);
    }


    public static void hideDialog(LoadCompleteType type) {
        if (sSweetAlertDialog != null) {
            sSweetAlertDialog.hide();
        }
    }

    /**
     * 加载完成以后的类型
     */
    public enum LoadCompleteType {
        Success, Error
    }

}
