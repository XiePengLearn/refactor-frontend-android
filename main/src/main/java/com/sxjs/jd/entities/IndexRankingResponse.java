package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/26 09:43
 * @Description:
 */
public class IndexRankingResponse {


    /**
     * code : 200200
     * data : {"RANK_DECLINE":"3","INDICATION":[{"INDICATION_NAME":["医疗质量","(一)功能定位"],"CHILDREN":[{"INDICATION_NAME":["1.门诊人次数与出院人次数比"],"CHILDREN":[],"INDICATION":[{"INDICATION_NAME":["(1).门诊人次数与出院人次数比"],"INDICATION_VALUE":"11.4","SAMETYPE_HOSPITAL_RANK":"2","SAMETYPE_HOSPITAL_OLD_RANK":"1","PREFECTURE_HOSPITAL_RANK":"2","PREFECTURE_HOSPITAL_OLD_RANK":"1"}]},{"INDICATION_NAME":["2.下转患者人次数"],"CHILDREN":[],"INDICATION":[{"INDICATION_NAME":["(1).下转患者人次数"],"INDICATION_VALUE":"4.6","SAMETYPE_HOSPITAL_RANK":"2","SAMETYPE_HOSPITAL_OLD_RANK":"1","PREFECTURE_HOSPITAL_RANK":"2","PREFECTURE_HOSPITAL_OLD_RANK":"1"}]},{"INDICATION_NAME":["3.日间手术占择期手术比例"],"CHILDREN":[],"INDICATION":[{"INDICATION_NAME":["(1).日间手术占择期手术比例"],"INDICATION_VALUE":"9.8","SAMETYPE_HOSPITAL_RANK":"2","SAMETYPE_HOSPITAL_OLD_RANK":"1","PREFECTURE_HOSPITAL_RANK":"2","PREFECTURE_HOSPITAL_OLD_RANK":"1"}]}],"INDICATION":[]}]}
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
         * RANK_DECLINE : 3
         * INDICATION : [{"INDICATION_NAME":["医疗质量","(一)功能定位"],"CHILDREN":[{"INDICATION_NAME":["1.门诊人次数与出院人次数比"],"CHILDREN":[],"INDICATION":[{"INDICATION_NAME":["(1).门诊人次数与出院人次数比"],"INDICATION_VALUE":"11.4","SAMETYPE_HOSPITAL_RANK":"2","SAMETYPE_HOSPITAL_OLD_RANK":"1","PREFECTURE_HOSPITAL_RANK":"2","PREFECTURE_HOSPITAL_OLD_RANK":"1"}]},{"INDICATION_NAME":["2.下转患者人次数"],"CHILDREN":[],"INDICATION":[{"INDICATION_NAME":["(1).下转患者人次数"],"INDICATION_VALUE":"4.6","SAMETYPE_HOSPITAL_RANK":"2","SAMETYPE_HOSPITAL_OLD_RANK":"1","PREFECTURE_HOSPITAL_RANK":"2","PREFECTURE_HOSPITAL_OLD_RANK":"1"}]},{"INDICATION_NAME":["3.日间手术占择期手术比例"],"CHILDREN":[],"INDICATION":[{"INDICATION_NAME":["(1).日间手术占择期手术比例"],"INDICATION_VALUE":"9.8","SAMETYPE_HOSPITAL_RANK":"2","SAMETYPE_HOSPITAL_OLD_RANK":"1","PREFECTURE_HOSPITAL_RANK":"2","PREFECTURE_HOSPITAL_OLD_RANK":"1"}]}],"INDICATION":[]}]
         */

        private String RANK_DECLINE;
        private List<INDICATIONBeanX> INDICATION;

        public String getRANK_DECLINE() {
            return RANK_DECLINE;
        }

        public void setRANK_DECLINE(String RANK_DECLINE) {
            this.RANK_DECLINE = RANK_DECLINE;
        }

        public List<INDICATIONBeanX> getINDICATION() {
            return INDICATION;
        }

        public void setINDICATION(List<INDICATIONBeanX> INDICATION) {
            this.INDICATION = INDICATION;
        }

        public static class INDICATIONBeanX {
            private List<String>       INDICATION_NAME;
            private List<CHILDRENBean> CHILDREN;
            private List<?>            INDICATION;

            public List<String> getINDICATION_NAME() {
                return INDICATION_NAME;
            }

            public void setINDICATION_NAME(List<String> INDICATION_NAME) {
                this.INDICATION_NAME = INDICATION_NAME;
            }

            public List<CHILDRENBean> getCHILDREN() {
                return CHILDREN;
            }

            public void setCHILDREN(List<CHILDRENBean> CHILDREN) {
                this.CHILDREN = CHILDREN;
            }

            public List<?> getINDICATION() {
                return INDICATION;
            }

            public void setINDICATION(List<?> INDICATION) {
                this.INDICATION = INDICATION;
            }

            public static class CHILDRENBean {
                private List<String>         INDICATION_NAME;
                private List<?>              CHILDREN;
                private List<INDICATIONBean> INDICATION;

                public List<String> getINDICATION_NAME() {
                    return INDICATION_NAME;
                }

                public void setINDICATION_NAME(List<String> INDICATION_NAME) {
                    this.INDICATION_NAME = INDICATION_NAME;
                }

                public List<?> getCHILDREN() {
                    return CHILDREN;
                }

                public void setCHILDREN(List<?> CHILDREN) {
                    this.CHILDREN = CHILDREN;
                }

                public List<INDICATIONBean> getINDICATION() {
                    return INDICATION;
                }

                public void setINDICATION(List<INDICATIONBean> INDICATION) {
                    this.INDICATION = INDICATION;
                }

                public static class INDICATIONBean {
                    /**
                     * INDICATION_NAME : ["(1).门诊人次数与出院人次数比"]
                     * INDICATION_VALUE : 11.4
                     * SAMETYPE_HOSPITAL_RANK : 2
                     * SAMETYPE_HOSPITAL_OLD_RANK : 1
                     * PREFECTURE_HOSPITAL_RANK : 2
                     * PREFECTURE_HOSPITAL_OLD_RANK : 1
                     */

                    private String INDICATION_VALUE;
                    private String       SAMETYPE_HOSPITAL_RANK;
                    private String       SAMETYPE_HOSPITAL_OLD_RANK;
                    private String       PREFECTURE_HOSPITAL_RANK;
                    private String       PREFECTURE_HOSPITAL_OLD_RANK;
                    private List<String> INDICATION_NAME;

                    public String getINDICATION_VALUE() {
                        return INDICATION_VALUE;
                    }

                    public void setINDICATION_VALUE(String INDICATION_VALUE) {
                        this.INDICATION_VALUE = INDICATION_VALUE;
                    }

                    public String getSAMETYPE_HOSPITAL_RANK() {
                        return SAMETYPE_HOSPITAL_RANK;
                    }

                    public void setSAMETYPE_HOSPITAL_RANK(String SAMETYPE_HOSPITAL_RANK) {
                        this.SAMETYPE_HOSPITAL_RANK = SAMETYPE_HOSPITAL_RANK;
                    }

                    public String getSAMETYPE_HOSPITAL_OLD_RANK() {
                        return SAMETYPE_HOSPITAL_OLD_RANK;
                    }

                    public void setSAMETYPE_HOSPITAL_OLD_RANK(String SAMETYPE_HOSPITAL_OLD_RANK) {
                        this.SAMETYPE_HOSPITAL_OLD_RANK = SAMETYPE_HOSPITAL_OLD_RANK;
                    }

                    public String getPREFECTURE_HOSPITAL_RANK() {
                        return PREFECTURE_HOSPITAL_RANK;
                    }

                    public void setPREFECTURE_HOSPITAL_RANK(String PREFECTURE_HOSPITAL_RANK) {
                        this.PREFECTURE_HOSPITAL_RANK = PREFECTURE_HOSPITAL_RANK;
                    }

                    public String getPREFECTURE_HOSPITAL_OLD_RANK() {
                        return PREFECTURE_HOSPITAL_OLD_RANK;
                    }

                    public void setPREFECTURE_HOSPITAL_OLD_RANK(String PREFECTURE_HOSPITAL_OLD_RANK) {
                        this.PREFECTURE_HOSPITAL_OLD_RANK = PREFECTURE_HOSPITAL_OLD_RANK;
                    }

                    public List<String> getINDICATION_NAME() {
                        return INDICATION_NAME;
                    }

                    public void setINDICATION_NAME(List<String> INDICATION_NAME) {
                        this.INDICATION_NAME = INDICATION_NAME;
                    }
                }
            }
        }
    }
}
