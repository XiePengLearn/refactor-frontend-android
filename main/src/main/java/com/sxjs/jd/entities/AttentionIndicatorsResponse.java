package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/25 19:38
 * @Description:
 */
public class AttentionIndicatorsResponse {


    /**
     * code : 200200
     * data : [{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"0","INDICATOR_VALUE":"0.38","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"0","INDICATOR_VALUE":"0.76","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"0","INDICATOR_VALUE":"0.81","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"0","INDICATOR_VALUE":"0.92","INDICATOR_CONTENT":"升降值"},{"INDICATOR_NAME":"(1).门诊人次数与出院人次数比","SIGN_TYPE":"0","INDICATOR_VALUE":"0.79","INDICATOR_CONTENT":"升降值"}]
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
         * INDICATOR_NAME : (1).门诊人次数与出院人次数比
         * SIGN_TYPE : 0
         * INDICATOR_VALUE : 0.38
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
}
