package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/21 16:11
 * @Description:
 */
public class UploadImageResponse {

    /**
     * code : 200200
     * msg : 说明
     * data : {"IMAGE_URI":"图片URI地址"}
     */

    private String code;
    private String   msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * IMAGE_URI : 图片URI地址
         */

        private String IMAGE_URI;

        public String getIMAGE_URI() {
            return IMAGE_URI;
        }

        public void setIMAGE_URI(String IMAGE_URI) {
            this.IMAGE_URI = IMAGE_URI;
        }
    }
}
