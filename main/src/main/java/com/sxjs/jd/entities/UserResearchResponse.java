package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/20 13:25
 * @Description:
 */
public class UserResearchResponse {


    /**
     * code : 200200
     * data : {"SURVEY_ID":"2","SURVEY_STATUS":"2","SURVEY_ADDRESS":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/hospital-survey?type=S015&id=2"}
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
         * SURVEY_ID : 2
         * SURVEY_STATUS : 2
         * SURVEY_ADDRESS : http://114.247.234.146:8087/hospital-pa-ap-h5/#/hospital-survey?type=S015&id=2
         */

        private String SURVEY_ID;
        private String SURVEY_STATUS;
        private String SURVEY_ADDRESS;

        public String getSURVEY_ID() {
            return SURVEY_ID;
        }

        public void setSURVEY_ID(String SURVEY_ID) {
            this.SURVEY_ID = SURVEY_ID;
        }

        public String getSURVEY_STATUS() {
            return SURVEY_STATUS;
        }

        public void setSURVEY_STATUS(String SURVEY_STATUS) {
            this.SURVEY_STATUS = SURVEY_STATUS;
        }

        public String getSURVEY_ADDRESS() {
            return SURVEY_ADDRESS;
        }

        public void setSURVEY_ADDRESS(String SURVEY_ADDRESS) {
            this.SURVEY_ADDRESS = SURVEY_ADDRESS;
        }
    }
}
