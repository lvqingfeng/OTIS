package com.nice.otis.bean;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class HeadlineListBean {

    /**
     * message : 获取头条信息成功
     * statusCode : 1
     * data : {"headInfo":[{"id":"1","title":"dsfgsdf","tag_id":"1","img_url":"","tag_str":null}],"total":"1"}
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
         * headInfo : [{"id":"1","title":"dsfgsdf","tag_id":"1","img_url":"","tag_str":null}]
         * total : 1
         */

        private String total;
        private List<HeadInfoBean> headInfo;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<HeadInfoBean> getHeadInfo() {
            return headInfo;
        }

        public void setHeadInfo(List<HeadInfoBean> headInfo) {
            this.headInfo = headInfo;
        }

        public static class HeadInfoBean {
            /**
             * id : 1
             * title : dsfgsdf
             * tag_id : 1
             * img_url :
             * tag_str : null
             */

            private String id;
            private String title;
            private String tag_id;
            private String img_url;
            private String tag_str;

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

            public String getTag_id() {
                return tag_id;
            }

            public void setTag_id(String tag_id) {
                this.tag_id = tag_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getTag_str() {
                return tag_str;
            }

            public void setTag_str(String tag_str) {
                this.tag_str = tag_str;
            }
        }
    }
}
