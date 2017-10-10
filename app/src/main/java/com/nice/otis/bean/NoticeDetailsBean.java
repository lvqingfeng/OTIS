package com.nice.otis.bean;

/**
 * 作者:吕清锋
 * 时间:2017/9/9
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class NoticeDetailsBean {

    /**
     * message : 获取数据成功
     * statusCode : 1
     * data : {"id":"1","title":"欢迎使用weiphp微信小程序版","content":"<p>这里是系统公告的内容，只有系统超级管理员可以发布和管理。<\/p><p><br/><\/p><p>超级管理员可以登录系统后台，进入公告管理里进入编辑，如下图<\/p><p><img src=\"/weicms/Uploads/Editor/-1/2016-09-24/57e65bfb24d27.png\" title=\"QQ图片20160924185634.png\"/><\/p>","create_time":"01-01"}
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
         * id : 1
         * title : 欢迎使用weiphp微信小程序版
         * content : <p>这里是系统公告的内容，只有系统超级管理员可以发布和管理。</p><p><br/></p><p>超级管理员可以登录系统后台，进入公告管理里进入编辑，如下图</p><p><img src="/weicms/Uploads/Editor/-1/2016-09-24/57e65bfb24d27.png" title="QQ图片20160924185634.png"/></p>
         * create_time : 01-01
         */

        private String id;
        private String title;
        private String content;
        private String create_time;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
