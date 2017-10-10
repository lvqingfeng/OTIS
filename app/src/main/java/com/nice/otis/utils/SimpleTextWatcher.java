package com.nice.otis.utils;

import android.text.Editable;
import android.text.TextWatcher;


/**
 * 作者：lv
 * 创建时间：07月14日
 * 时间：19:37
 * 版本：v1.0.0
 * 类描述：监听文字输入的回掉
 * 修改时间：
 */
public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 3)
            return;
        isInputText(EditTextUtils.checkpointIsInput(start, after));
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public abstract void isInputText(boolean isInput);

}
