package com.sxjs.jd.entities;

// FIXME generate failure  field _$Data281

/**
 * @Auther: xp
 * @Date: 2019/9/9 17:18
 * @Description:
 */
public class UserInfoResponse {


    /**
     * code : 200200
     * data : {"AUTHENTICATE_STATUS":"1","VIP_STATUS":"1","HEAD_PORTRAIT":"","IS_BUY_ITJ":"0","NAME":"谢鹏"}
     * msg : null
     */

    private String   code;
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
         * AUTHENTICATE_STATUS : 1
         * VIP_STATUS : 1
         * HEAD_PORTRAIT :
         * IS_BUY_ITJ : 0
         * NAME : 谢鹏
         */

        private String AUTHENTICATE_STATUS;
        private String VIP_STATUS;
        private String HEAD_PORTRAIT;
        private String IS_BUY_ITJ;
        private String NAME;

        public String getAUTHENTICATE_STATUS() {
            return AUTHENTICATE_STATUS;
        }

        public void setAUTHENTICATE_STATUS(String AUTHENTICATE_STATUS) {
            this.AUTHENTICATE_STATUS = AUTHENTICATE_STATUS;
        }

        public String getVIP_STATUS() {
            return VIP_STATUS;
        }

        public void setVIP_STATUS(String VIP_STATUS) {
            this.VIP_STATUS = VIP_STATUS;
        }

        public String getHEAD_PORTRAIT() {
            return HEAD_PORTRAIT;
        }

        public void setHEAD_PORTRAIT(String HEAD_PORTRAIT) {
            this.HEAD_PORTRAIT = HEAD_PORTRAIT;
        }

        public String getIS_BUY_ITJ() {
            return IS_BUY_ITJ;
        }

        public void setIS_BUY_ITJ(String IS_BUY_ITJ) {
            this.IS_BUY_ITJ = IS_BUY_ITJ;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }
    }
}
