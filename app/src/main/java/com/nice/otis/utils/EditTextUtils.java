package com.nice.otis.utils;

import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 作者：lv
 * 创建时间：07月01日
 * 时间：14:33
 * 版本：v1.0.0
 * 类描述：EditText的工具类
 * 修改时间：
 */
public class EditTextUtils {

    /**
     * 改变EditText游标为显示状态
     *
     * @param editText
     */
    public static void changeCursorVisible(EditText editText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (!editText.isCursorVisible()) {
                String inputStr = editText.getText().toString();
                if (TextUtils.isEmpty(inputStr))
                    editText.setText("");
                editText.setCursorVisible(true);
            }
        }
    }

    /**
     * 设置EditText是否显示为密码模式，或者可见模式
     *
     * @param editText
     * @param flag
     */
    public static void checkedPasswordIsShow(EditText editText, boolean flag) {
        if (flag) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//迷茫为可见状态
            editText.setSelection(editText.length());//设置光标显示在文字的最后面
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);//密码为不可见状态
            editText.setSelection(editText.length());
        }
    }

    /**
     * 设置EditText的长度
     *
     * @param length
     * @return
     */
    public static void setInputLength(EditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * 用来检测判断用户是否符合输入成功的标准
     *
     * @param start
     * @param after
     * @return
     */
    public static boolean checkpointIsInput(int start, int after) {
        //说明正在输入
        if (after > 0) {
            return start + after >= 3;
        } else {
            //如果走else说明正在减少
            return start >= 3;
        }
    }

    /**
     * 判断输入框中的内容是否输入
     *
     * @param views
     * @return
     */
    public static boolean isInputOk(TextView... views) {

        for (TextView view : views) {
            if (view.length() < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 监听当EditText中的文字发生变化的时候，对应的Button是否为可点击状态
     *
     * @param context 环境变量
     * @param button  对应的button
     * @param texts   需要监听的EditText
     */
    public static void checkOnEditInputForButtonState(final Context context, final Button button, EditText... texts) {

        final boolean[] isInputs = new boolean[texts.length];

        for (int i = 0; i < texts.length; i++) {
            final int finalI = i;
            texts[i].addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void isInputText(boolean isInput) {
                    isInputs[finalI] = isInput;
                    if (checkButtonIsEnabled(isInputs) && !button.isEnabled())
                        ButtonUtils.setButtonEnabledState(context, button, true);
                    else if (!checkButtonIsEnabled(isInputs) && button.isEnabled()) {
                        ButtonUtils.setButtonEnabledState(context, button, false);
                    }
                }
            });
        }

    }

    /**
     * 检测按钮是否能被点击
     *
     * @param isInputs
     * @return
     */
    private static boolean checkButtonIsEnabled(boolean[] isInputs) {

        for (boolean isInput : isInputs) {
            if (!isInput) {
                return false;
            }
        }
        return true;
    }


}
