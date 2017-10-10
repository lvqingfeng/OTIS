package com.nice.otis.bean;

/**
 * 作者:吕清锋
 * 时间:2017/9/4
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class BaseData {

    /**
     * message : 验证码发送成功
     * statusCode : 1
     */

    private String message;
    private int statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
