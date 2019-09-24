package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/24 17:05
 * @Description:
 */
public class MedicalQualityResponse {

    /**
     * code : 200200
     * data : {"DATA_END_DATE":"2019-09-23","CLASSIFY":[{"CLASSIFY":"医疗质量","PROJECT":[{"NAME":"门诊人次数与出院人次数比","VALUE":"1"},{"NAME":"下转患者人次数","VALUE":"2"}]},{"CLASSIFY":"合理用药","PROJECT":[{"NAME":"点评处方占处方总数的比例","VALUE":"1"},{"NAME":"病房（区）医嘱单（处方）点评率","VALUE":"4"}]}]}
     * msg : “”
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
         * DATA_END_DATE : 2019-09-23
         * CLASSIFY : [{"CLASSIFY":"医疗质量","PROJECT":[{"NAME":"门诊人次数与出院人次数比","VALUE":"1"},{"NAME":"下转患者人次数","VALUE":"2"}]},{"CLASSIFY":"合理用药","PROJECT":[{"NAME":"点评处方占处方总数的比例","VALUE":"1"},{"NAME":"病房（区）医嘱单（处方）点评率","VALUE":"4"}]}]
         */

        private String DATA_END_DATE;
        private List<CLASSIFYBean> CLASSIFY;

        public String getDATA_END_DATE() {
            return DATA_END_DATE;
        }

        public void setDATA_END_DATE(String DATA_END_DATE) {
            this.DATA_END_DATE = DATA_END_DATE;
        }

        public List<CLASSIFYBean> getCLASSIFY() {
            return CLASSIFY;
        }

        public void setCLASSIFY(List<CLASSIFYBean> CLASSIFY) {
            this.CLASSIFY = CLASSIFY;
        }

        public static class CLASSIFYBean {
            /**
             * CLASSIFY : 医疗质量
             * PROJECT : [{"NAME":"门诊人次数与出院人次数比","VALUE":"1"},{"NAME":"下转患者人次数","VALUE":"2"}]
             */

            private String CLASSIFY;
            private List<PROJECTBean> PROJECT;

            public String getCLASSIFY() {
                return CLASSIFY;
            }

            public void setCLASSIFY(String CLASSIFY) {
                this.CLASSIFY = CLASSIFY;
            }

            public List<PROJECTBean> getPROJECT() {
                return PROJECT;
            }

            public void setPROJECT(List<PROJECTBean> PROJECT) {
                this.PROJECT = PROJECT;
            }

            public static class PROJECTBean {
                /**
                 * NAME : 门诊人次数与出院人次数比
                 * VALUE : 1
                 */

                private String NAME;
                private String VALUE;

                public String getNAME() {
                    return NAME;
                }

                public void setNAME(String NAME) {
                    this.NAME = NAME;
                }

                public String getVALUE() {
                    return VALUE;
                }

                public void setVALUE(String VALUE) {
                    this.VALUE = VALUE;
                }
            }
        }
    }
}
