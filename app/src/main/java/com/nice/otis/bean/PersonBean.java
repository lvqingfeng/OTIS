package com.nice.otis.bean;

/**
 * 作者:吕清锋
 * 时间:2017/9/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class PersonBean {

    /**
     * message : 获取数据成功
     * statusCode : 1
     * data : {"truename":"李四","gender":null,"point":"0","mobile":"13526485613","nickname":"王五","birthday":"2017-9-8","uid":"13","service_record":"0","headimgurl":null}
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
         * truename : 李四
         * gender : null
         * point : 0
         * mobile : 13526485613
         * nickname : 王五
         * birthday : 2017-9-8
         * uid : 13
         * service_record : 0
         * headimgurl : null
         */

        private String truename;
        private String gender;
        private String point;
        private String mobile;
        private String nickname;
        private String birthday;
        private String uid;
        private String service_record;
        private String headimgurl;

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

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getService_record() {
            return service_record;
        }

        public void setService_record(String service_record) {
            this.service_record = service_record;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }
    }
}
