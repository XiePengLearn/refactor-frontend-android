package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/18 09:15
 * @Description:
 */
public class PolicyElucidationResponse {


    /**
     * code : 200200
     * msg : 说明
     * data : [{"TYPE_NAME":"类别名称","URI":"地址"}]
     */

    private String         code;
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
         * TYPE_NAME : 类别名称
         * URI : 地址
         */

        private String TYPE_NAME;
        private String URI;

        public String getTYPE_NAME() {
            return TYPE_NAME;
        }

        public void setTYPE_NAME(String TYPE_NAME) {
            this.TYPE_NAME = TYPE_NAME;
        }

        public String getURI() {
            return URI;
        }

        public void setURI(String URI) {
            this.URI = URI;
        }
    }
}
