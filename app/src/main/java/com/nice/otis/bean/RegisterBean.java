package com.nice.otis.bean;

/**
 * 作者:吕清锋
 * 时间:2017/9/4
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class RegisterBean {

    /**
     * message : 注册成功!
     * statusCode : 1
     * data : {"mobile":"17010207172","token":"0df06e0abf8ccb634fa80ebe3b7ada1a","uid":"13"}
     */

    private String message;
    private int statusCode;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * mobile : 17010207172
         * token : 0df06e0abf8ccb634fa80ebe3b7ada1a
         * uid : 13
         */

        private String mobile;
        private String token;
        private String uid;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
