package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/25 14:41
 * @Description:
 */
public class ExamScheduleResponse {


    /**
     * code : 200200
     * data : [{"EXAM_STAGE_NAME":"自查填报数据","EXAM_STAGE_STATUS":"2","EXAM_STAGE_END_DATE":"2019-08-12"},{"EXAM_STAGE_NAME":"第一阶段质控","EXAM_STAGE_STATUS":"2","EXAM_STAGE_END_DATE":"2019-08-20"},{"EXAM_STAGE_NAME":"第二阶段质控","EXAM_STAGE_STATUS":"1","EXAM_STAGE_END_DATE":"2019-09-30"},{"EXAM_STAGE_NAME":"病案首页数据质控","EXAM_STAGE_STATUS":"0","EXAM_STAGE_END_DATE":"2019-10-31"}]
     * msg :
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
         * EXAM_STAGE_NAME : 自查填报数据
         * EXAM_STAGE_STATUS : 2
         * EXAM_STAGE_END_DATE : 2019-08-12
         */

        private String EXAM_STAGE_NAME;
        private String EXAM_STAGE_STATUS;
        private String EXAM_STAGE_END_DATE;

        public String getEXAM_STAGE_NAME() {
            return EXAM_STAGE_NAME;
        }

        public void setEXAM_STAGE_NAME(String EXAM_STAGE_NAME) {
            this.EXAM_STAGE_NAME = EXAM_STAGE_NAME;
        }

        public String getEXAM_STAGE_STATUS() {
            return EXAM_STAGE_STATUS;
        }

        public void setEXAM_STAGE_STATUS(String EXAM_STAGE_STATUS) {
            this.EXAM_STAGE_STATUS = EXAM_STAGE_STATUS;
        }

        public String getEXAM_STAGE_END_DATE() {
            return EXAM_STAGE_END_DATE;
        }

        public void setEXAM_STAGE_END_DATE(String EXAM_STAGE_END_DATE) {
            this.EXAM_STAGE_END_DATE = EXAM_STAGE_END_DATE;
        }
    }
}
