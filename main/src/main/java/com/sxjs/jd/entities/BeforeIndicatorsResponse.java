package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/24 16:29
 * @Description:
 */
public class BeforeIndicatorsResponse {


    /**
     * code : 200200
     * data : {"ABNORMAL_INDICATORS":[{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.52","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.66","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.93","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.80","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.77","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.91","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.83","INDICATOR_CONTENT":"升降值"}],"FOLLOW_INDICATORS":[{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.38"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.76"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.81"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.92"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.79"}],"FOLLOW_INDICATORS_END_DATE":"2019-09-30"}
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
         * ABNORMAL_INDICATORS : [{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.52","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.66","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.93","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.80","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.77","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.91","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"1","INDICATOR_VALUE":"0.83","INDICATOR_CONTENT":"升降值"}]
         * FOLLOW_INDICATORS : [{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.38"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.76"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.81"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.92"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","INDICATOR_VALUE":"0.79"}]
         * FOLLOW_INDICATORS_END_DATE : 2019-09-30
         */

        private String FOLLOW_INDICATORS_END_DATE;
        private List<ABNORMALINDICATORSBean> ABNORMAL_INDICATORS;
        private List<FOLLOWINDICATORSBean>   FOLLOW_INDICATORS;

        public String getFOLLOW_INDICATORS_END_DATE() {
            return FOLLOW_INDICATORS_END_DATE;
        }

        public void setFOLLOW_INDICATORS_END_DATE(String FOLLOW_INDICATORS_END_DATE) {
            this.FOLLOW_INDICATORS_END_DATE = FOLLOW_INDICATORS_END_DATE;
        }

        public List<ABNORMALINDICATORSBean> getABNORMAL_INDICATORS() {
            return ABNORMAL_INDICATORS;
        }

        public void setABNORMAL_INDICATORS(List<ABNORMALINDICATORSBean> ABNORMAL_INDICATORS) {
            this.ABNORMAL_INDICATORS = ABNORMAL_INDICATORS;
        }

        public List<FOLLOWINDICATORSBean> getFOLLOW_INDICATORS() {
            return FOLLOW_INDICATORS;
        }

        public void setFOLLOW_INDICATORS(List<FOLLOWINDICATORSBean> FOLLOW_INDICATORS) {
            this.FOLLOW_INDICATORS = FOLLOW_INDICATORS;
        }

        public static class ABNORMALINDICATORSBean {
            /**
             * INDICATOR_NAME : (1).门诊人次数与出院人次数比
             * SIGN_TYPE : 1
             * INDICATOR_VALUE : 0.52
             * INDICATOR_CONTENT : 升降值
             */

            private String INDICATOR_NAME;
            private String SIGN_TYPE;
            private String INDICATOR_VALUE;
            private String INDICATOR_CONTENT;

            public String getINDICATOR_NAME() {
                return INDICATOR_NAME;
            }

            public void setINDICATOR_NAME(String INDICATOR_NAME) {
                this.INDICATOR_NAME = INDICATOR_NAME;
            }

            public String getSIGN_TYPE() {
                return SIGN_TYPE;
            }

            public void setSIGN_TYPE(String SIGN_TYPE) {
                this.SIGN_TYPE = SIGN_TYPE;
            }

            public String getINDICATOR_VALUE() {
                return INDICATOR_VALUE;
            }

            public void setINDICATOR_VALUE(String INDICATOR_VALUE) {
                this.INDICATOR_VALUE = INDICATOR_VALUE;
            }

            public String getINDICATOR_CONTENT() {
                return INDICATOR_CONTENT;
            }

            public void setINDICATOR_CONTENT(String INDICATOR_CONTENT) {
                this.INDICATOR_CONTENT = INDICATOR_CONTENT;
            }
        }

        public static class FOLLOWINDICATORSBean {
            /**
             * INDICATOR_NAME : (1).门诊人次数与出院人次数比
             * INDICATOR_VALUE : 0.38
             */

            private String INDICATOR_NAME;
            private String INDICATOR_VALUE;

            public String getINDICATOR_NAME() {
                return INDICATOR_NAME;
            }

            public void setINDICATOR_NAME(String INDICATOR_NAME) {
                this.INDICATOR_NAME = INDICATOR_NAME;
            }

            public String getINDICATOR_VALUE() {
                return INDICATOR_VALUE;
            }

            public void setINDICATOR_VALUE(String INDICATOR_VALUE) {
                this.INDICATOR_VALUE = INDICATOR_VALUE;
            }
        }
    }
}
