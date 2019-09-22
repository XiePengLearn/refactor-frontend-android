package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/21 13:23
 * @Description:
 */
public class UserChangeAuthenticationResponse {


    /**
     * code : 200200
     * msg : 说明
     * data : {"HOSPITAL_NAME":"医院名称","DEPARTMENT":"科室","RESIGN":"职务","WORK_TEL":"单位电话","PROVINCIAL_CODE":"省市代码","DOCUMENT_URI":{"TYPE":"1:身份证照正面,2:身份证照反面,3:证件照,4:胸牌照","URI":"xxxx"}}
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
         * HOSPITAL_NAME : 医院名称
         * DEPARTMENT : 科室
         * RESIGN : 职务
         * WORK_TEL : 单位电话
         * PROVINCIAL_CODE : 省市代码
         * DOCUMENT_URI : {"TYPE":"1:身份证照正面,2:身份证照反面,3:证件照,4:胸牌照","URI":"xxxx"}
         */

        private String HOSPITAL_NAME;
        private String          DEPARTMENT;
        private String          RESIGN;
        private String          WORK_TEL;
        private String          PROVINCIAL_CODE;
        private DOCUMENTURIBean DOCUMENT_URI;

        public String getHOSPITAL_NAME() {
            return HOSPITAL_NAME;
        }

        public void setHOSPITAL_NAME(String HOSPITAL_NAME) {
            this.HOSPITAL_NAME = HOSPITAL_NAME;
        }

        public String getDEPARTMENT() {
            return DEPARTMENT;
        }

        public void setDEPARTMENT(String DEPARTMENT) {
            this.DEPARTMENT = DEPARTMENT;
        }

        public String getRESIGN() {
            return RESIGN;
        }

        public void setRESIGN(String RESIGN) {
            this.RESIGN = RESIGN;
        }

        public String getWORK_TEL() {
            return WORK_TEL;
        }

        public void setWORK_TEL(String WORK_TEL) {
            this.WORK_TEL = WORK_TEL;
        }

        public String getPROVINCIAL_CODE() {
            return PROVINCIAL_CODE;
        }

        public void setPROVINCIAL_CODE(String PROVINCIAL_CODE) {
            this.PROVINCIAL_CODE = PROVINCIAL_CODE;
        }

        public DOCUMENTURIBean getDOCUMENT_URI() {
            return DOCUMENT_URI;
        }

        public void setDOCUMENT_URI(DOCUMENTURIBean DOCUMENT_URI) {
            this.DOCUMENT_URI = DOCUMENT_URI;
        }

        public static class DOCUMENTURIBean {
            /**
             * TYPE : 1:身份证照正面,2:身份证照反面,3:证件照,4:胸牌照
             * URI : xxxx
             */

            private String TYPE;
            private String URI;

            public String getTYPE() {
                return TYPE;
            }

            public void setTYPE(String TYPE) {
                this.TYPE = TYPE;
            }

            public String getURI() {
                return URI;
            }

            public void setURI(String URI) {
                this.URI = URI;
            }
        }
    }
}
