package com.nice.otis.base;

/**
 * 作者：lv
 * 创建时间：11月07日
 * 时间：16:07
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HttpResult<T> {

    protected String code;
    protected T msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
