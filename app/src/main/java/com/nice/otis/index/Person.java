package com.nice.otis.index;

import com.nice.otis.utils.PinYinUtil;

/**
 * 作者:吕清锋
 * 时间:2017/9/5
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class Person {
    //姓名
    private String name;
    //拼音
    private String pinyin;
    //拼音首字母
    private String headerWord;

    public Person(String name) {
        this.name = name;
        this.pinyin = PinYinUtil.toPinyin(name);
        headerWord = pinyin.substring(0, 1);
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderWord() {
        return headerWord;
    }
}
