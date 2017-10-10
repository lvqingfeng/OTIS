package com.nice.otis.bean;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/4
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class BrandListBean {

    /**
     * message : 获取品牌信息成功
     * statusCode : 1
     * info : [{"id":"1","title":"三菱","orderby":"1","english_name":"sanling","thumb":null}]
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
         * title : 三菱
         * orderby : 1
         * english_name : sanling
         * thumb : null
         */

        private String id;
        private String title;
        private String orderby;
        private String english_name;
        private String thumb;

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

        public String getEnglish_name() {
            return english_name;
        }

        public void setEnglish_name(String english_name) {
            this.english_name = english_name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
