package com.nice.otis.bean;

/**
 * 作者:吕清锋
 * 时间:2017/9/4
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class LoginBean {

    /**
     * message : 登录成功!
     * statusCode : 1
     * data : {"uid":"14","nickname":null,"truename":null,"gender":null,"email":null,"mobile":"13709592201","birthday":null,"status":"1","token":"c4d1b5e8605c75b7b96e99ff286f581a"}
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
         * uid : 14
         * nickname : null
         * truename : null
         * gender : null
         * email : null
         * mobile : 13709592201
         * birthday : null
         * status : 1
         * token : c4d1b5e8605c75b7b96e99ff286f581a
         */

        private String uid;
        private String nickname;
        private String truename;
        private String gender;
        private String email;
        private String mobile;
        private String birthday;
        private String status;
        private String token;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
