package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/20 14:29
 * @Description:
 */
public class NewsItemListResponse {


    /**
     * code : 200200
     * data : [{"ID":"1","NAME":"国家政策"},{"ID":"2","NAME":"业内新闻"},{"ID":"3","NAME":"健康资讯"},{"ID":"4","NAME":"测试"}]
     * msg :
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
         * ID : 1
         * NAME : 国家政策
         */

        private String ID;
        private String NAME;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }
    }
}
