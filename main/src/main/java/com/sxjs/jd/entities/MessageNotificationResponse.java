package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/18 14:13
 * @Description:
 */
public class MessageNotificationResponse {


    /**
     * code : 200200
     * msg : 说明
     * data : [{"TITLE":"标题","IMAGE_URI":"图像地址","CONTENT":"消息简述","NOTIFY_URI":"通知地址","DATE":"日期"}]
     */

    private String code;
    private String         msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * TITLE : 标题
         * IMAGE_URI : 图像地址
         * CONTENT : 消息简述
         * NOTIFY_URI : 通知地址
         * DATE : 日期
         */

        private String TITLE;
        private String IMAGE_URI;
        private String CONTENT;
        private String NOTIFY_URI;
        private String DATE;

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getIMAGE_URI() {
            return IMAGE_URI;
        }

        public void setIMAGE_URI(String IMAGE_URI) {
            this.IMAGE_URI = IMAGE_URI;
        }

        public String getCONTENT() {
            return CONTENT;
        }

        public void setCONTENT(String CONTENT) {
            this.CONTENT = CONTENT;
        }

        public String getNOTIFY_URI() {
            return NOTIFY_URI;
        }

        public void setNOTIFY_URI(String NOTIFY_URI) {
            this.NOTIFY_URI = NOTIFY_URI;
        }

        public String getDATE() {
            return DATE;
        }

        public void setDATE(String DATE) {
            this.DATE = DATE;
        }
    }
}
