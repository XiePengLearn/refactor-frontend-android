package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/18 09:15
 * @Description:
 */
public class AppUpdateResponse {


    /**
     * code : 200200
     * msg : 说明
     * data : {"updateTip":"更新提示","flag":"true","versionCode":"V1.0.0","url":"http://www.baidu.com"}
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
         * updateTip : 更新提示
         * flag : true
         * versionCode : V1.0.0
         * url : http://www.baidu.com
         */

        private String updateTip;
        private String flag;
        private String versionCode;
        private String url;

        public String getUpdateTip() {
            return updateTip;
        }

        public void setUpdateTip(String updateTip) {
            this.updateTip = updateTip;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
