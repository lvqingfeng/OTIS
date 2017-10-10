package com.nice.otis.bean;

import android.support.annotation.NonNull;

import com.nice.otis.utils.PinYinUtil;

@SuppressWarnings("unused")
public class Girl implements Comparable<Girl> {

    private String mName;
    private String mPinyin;
    
    public Girl(String name) {
        mName = name;
        mPinyin = PinYinUtil.toPinyin(mName);
    }
    
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getPinyin() {
        return mPinyin;
    }
    public void setPinyin(String pinyin) {
        mPinyin = pinyin;
    }

    @Override
    public int compareTo(@NonNull Girl another) {
        return mPinyin.compareTo(another.getPinyin());
    }
}
