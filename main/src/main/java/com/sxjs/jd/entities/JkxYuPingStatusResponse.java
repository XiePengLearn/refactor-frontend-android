package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/24 19:28
 * @Description:
 */
public class JkxYuPingStatusResponse {


    /**
     * code : 200200
     * data : {"PREVIEW_NAME":"XXXX2医院绩效考核","PREVIEW_STAGE_NAME":"核实","PREVIEW_STAGE_END_DATE":"2019-10-31"}
     * msg :
     */

    private String code;
    private DataBean data;
    private String   msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * PREVIEW_NAME : XXXX2医院绩效考核
         * PREVIEW_STAGE_NAME : 核实
         * PREVIEW_STAGE_END_DATE : 2019-10-31
         */

        private String PREVIEW_NAME;
        private String PREVIEW_STAGE_NAME;
        private String PREVIEW_STAGE_END_DATE;

        public String getPREVIEW_NAME() {
            return PREVIEW_NAME;
        }

        public void setPREVIEW_NAME(String PREVIEW_NAME) {
            this.PREVIEW_NAME = PREVIEW_NAME;
        }

        public String getPREVIEW_STAGE_NAME() {
            return PREVIEW_STAGE_NAME;
        }

        public void setPREVIEW_STAGE_NAME(String PREVIEW_STAGE_NAME) {
            this.PREVIEW_STAGE_NAME = PREVIEW_STAGE_NAME;
        }

        public String getPREVIEW_STAGE_END_DATE() {
            return PREVIEW_STAGE_END_DATE;
        }

        public void setPREVIEW_STAGE_END_DATE(String PREVIEW_STAGE_END_DATE) {
            this.PREVIEW_STAGE_END_DATE = PREVIEW_STAGE_END_DATE;
        }
    }
}
