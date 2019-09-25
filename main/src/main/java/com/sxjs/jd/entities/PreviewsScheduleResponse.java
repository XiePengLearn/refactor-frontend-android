package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/25 14:41
 * @Description:
 */
public class PreviewsScheduleResponse {


    /**
     * code : 200200
     * msg : 说明
     * data : [{"PREVIEW_STAGE_NAME":"预评阶段名称","PREVIEW_STAGE_STATUS":"预评阶段进行状态","PREVIEW_STAGE_END_DATE":"预评阶段截至日期"}]
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
         * PREVIEW_STAGE_NAME : 预评阶段名称
         * PREVIEW_STAGE_STATUS : 预评阶段进行状态
         * PREVIEW_STAGE_END_DATE : 预评阶段截至日期
         */

        private String PREVIEW_STAGE_NAME;
        private String PREVIEW_STAGE_STATUS;
        private String PREVIEW_STAGE_END_DATE;

        public String getPREVIEW_STAGE_NAME() {
            return PREVIEW_STAGE_NAME;
        }

        public void setPREVIEW_STAGE_NAME(String PREVIEW_STAGE_NAME) {
            this.PREVIEW_STAGE_NAME = PREVIEW_STAGE_NAME;
        }

        public String getPREVIEW_STAGE_STATUS() {
            return PREVIEW_STAGE_STATUS;
        }

        public void setPREVIEW_STAGE_STATUS(String PREVIEW_STAGE_STATUS) {
            this.PREVIEW_STAGE_STATUS = PREVIEW_STAGE_STATUS;
        }

        public String getPREVIEW_STAGE_END_DATE() {
            return PREVIEW_STAGE_END_DATE;
        }

        public void setPREVIEW_STAGE_END_DATE(String PREVIEW_STAGE_END_DATE) {
            this.PREVIEW_STAGE_END_DATE = PREVIEW_STAGE_END_DATE;
        }
    }
}
