package com.nice.otis.bean;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/5
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class BannerBean {

    /**
     * message : 操作成功
     * statusCode : 1
     * advInfo : [{"id":"19","token":"-1","title":"职位广告1","jump_url":"http://baike.baidu.com","img_url":"http://lift.lzy95.cn"},{"id":"20","token":"-1","title":"职位广告2","jump_url":"http://baike.baidu.com","img_url":"http://lift.lzy95.cn"}]
     */

    private String message;
    private int statusCode;
    private List<AdvInfoBean> advInfo;

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

    public List<AdvInfoBean> getAdvInfo() {
        return advInfo;
    }

    public void setAdvInfo(List<AdvInfoBean> advInfo) {
        this.advInfo = advInfo;
    }

    public static class AdvInfoBean {
        /**
         * id : 19
         * token : -1
         * title : 职位广告1
         * jump_url : http://baike.baidu.com
         * img_url : http://lift.lzy95.cn
         */

        private String id;
        private String token;
        private String title;
        private String jump_url;
        private String img_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
