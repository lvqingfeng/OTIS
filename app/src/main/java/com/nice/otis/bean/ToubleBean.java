package com.nice.otis.bean;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/9
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class ToubleBean {

    /**
     * message : 获取查询信息成功
     * statusCode : 1
     * info : [{"brand_id":"1","id":"2","title":"拉德芳斯","code":"11","is_pay":"0","attr_id":"1","search_count":"17","answer":"水电费感受到","reason":"12123"},{"brand_id":"1","id":"1","title":"电梯打不开","code":"11","is_pay":"1","attr_id":"1","search_count":"19","answer":"阿斯顿发撒旦法速度发斯蒂芬按时地方","reason":"阿斯顿发放"}]
     */

    private String message;
    private int statusCode;
    private List<InfoBean> info;

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

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * brand_id : 1
         * id : 2
         * title : 拉德芳斯
         * code : 11
         * is_pay : 0
         * attr_id : 1
         * search_count : 17
         * answer : 水电费感受到
         * reason : 12123
         */

        private String brand_id;
        private String id;
        private String title;
        private String code;
        private String is_pay;
        private String attr_id;
        private String search_count;
        private String answer;
        private String reason;
        private String point;

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public String getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(String attr_id) {
            this.attr_id = attr_id;
        }

        public String getSearch_count() {
            return search_count;
        }

        public void setSearch_count(String search_count) {
            this.search_count = search_count;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
