package com.nice.otis.base;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/18
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class ConsumptionBean {

    /**
     * message : 获取消费记录成功
     * statusCode : 1
     * list : [{"id":"1","consume_id":"1","user_id":"13","point":"12","create_time":"2079-07-13 14:47","consumer_title":"电梯打不开"},{"id":"2","consume_id":"1","user_id":"13","point":"10","create_time":"3945457-06-17 10:34","consumer_title":"电梯打不开"},{"id":"3","consume_id":"1","user_id":"13","point":"20","create_time":"1973-11-27 09:55","consumer_title":"电梯打不开"},{"id":"4","consume_id":"1","user_id":"13","point":"10","create_time":"3945457-06-17 10:34","consumer_title":"电梯打不开"},{"id":"5","consume_id":"1","user_id":"13","point":"20","create_time":"1973-11-27 09:55","consumer_title":"电梯打不开"}]
     * total : 5
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
         * id : 1
         * consume_id : 1
         * user_id : 13
         * point : 12
         * create_time : 2079-07-13 14:47
         * consumer_title : 电梯打不开
         */

        private String id;
        private String consume_id;
        private String user_id;
        private String point;
        private String create_time;
        private String consumer_title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConsume_id() {
            return consume_id;
        }

        public void setConsume_id(String consume_id) {
            this.consume_id = consume_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getConsumer_title() {
            return consumer_title;
        }

        public void setConsumer_title(String consumer_title) {
            this.consumer_title = consumer_title;
        }
    }
}
