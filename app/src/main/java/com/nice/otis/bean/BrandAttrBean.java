package com.nice.otis.bean;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/5
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class BrandAttrBean {

    /**
     * message : 获取型号信息成功
     * statusCode : 1
     * info : [{"id":"1","title":"电池型号","orderby":"1","attr_value":"220","brand_id":"1"}]
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
         * id : 1
         * title : 电池型号
         * orderby : 1
         * attr_value : 220
         * brand_id : 1
         */

        private String id;
        private String title;
        private String orderby;
        private String attr_value;
        private String brand_id;

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

        public String getOrderby() {
            return orderby;
        }

        public void setOrderby(String orderby) {
            this.orderby = orderby;
        }

        public String getAttr_value() {
            return attr_value;
        }

        public void setAttr_value(String attr_value) {
            this.attr_value = attr_value;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }
    }
}
