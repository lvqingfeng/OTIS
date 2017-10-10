package com.nice.otis.base;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/18
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class RechargeBean {

    /**
     * message : 获取充值记录成功
     * statusCode : 1
     * list : [{"id":"2","point_category_id":"1","user_id":"13","price":"100","create_time":"1970-01-02 18:12","point_category_title":"开门大礼包"},{"id":"3","point_category_id":"2","user_id":"13","price":"200","create_time":"1970-09-29 10:28","point_category_title":"包月VIP"},{"id":"4","point_category_id":"1","user_id":"13","price":"100","create_time":"1970-01-02 18:12","point_category_title":"开门大礼包"},{"id":"5","point_category_id":"2","user_id":"13","price":"200","create_time":"1970-09-29 10:28","point_category_title":"包月VIP"}]
     * total : 4
     */

    private String message;
    private int statusCode;
    private String total;
    private List<ListBean> list;

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 2
         * point_category_id : 1
         * user_id : 13
         * price : 100
         * create_time : 1970-01-02 18:12
         * point_category_title : 开门大礼包
         */

        private String id;
        private String point_category_id;
        private String user_id;
        private String price;
        private String create_time;
        private String point_category_title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPoint_category_id() {
            return point_category_id;
        }

        public void setPoint_category_id(String point_category_id) {
            this.point_category_id = point_category_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPoint_category_title() {
            return point_category_title;
        }

        public void setPoint_category_title(String point_category_title) {
            this.point_category_title = point_category_title;
        }
    }
}
