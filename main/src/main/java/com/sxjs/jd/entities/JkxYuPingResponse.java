package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/24 19:29
 * @Description:
 */
public class JkxYuPingResponse {


    /**
     * code : 200200
     * data : [{"ID":"1","INDICATION_NAME":["医疗质量","(一)功能定位","1.门诊人次数与出院人次数比","(1).门诊人次数与出院人次数比"],"FILL_IN_ITEMS":[{"ITEM_NO":"1","ITEM_NAME":"门诊患者人次数","ITEM_VALUE":"418569.00"},{"ITEM_NO":"1","ITEM_NAME":"同期出院患者人次数","ITEM_VALUE":"34856.00"}],"REMIND":[{"ITEM_NO":"1","ITEM_CONTENT":"指标显示异常"}]},{"ID":"2","INDICATION_NAME":["医疗质量","(一)功能定位","2.下转患者人次数","(1).下转患者人次数"],"FILL_IN_ITEMS":[{"ITEM_NO":"1","ITEM_NAME":"门急诊下转患者人次数","ITEM_VALUE":"0.00"},{"ITEM_NO":"1","ITEM_NAME":"住院下转患者人次数","ITEM_VALUE":"72.00"}],"REMIND":[{"ITEM_NO":"1","ITEM_CONTENT":"指标显示异常"}]},{"ID":"3","INDICATION_NAME":["医疗质量","(一)功能定位","3.日间手术占择期手术比例","(1).日间手术占择期手术比例"],"FILL_IN_ITEMS":[{"ITEM_NO":"1","ITEM_NAME":"日间手术台次数","ITEM_VALUE":"265.00"},{"ITEM_NO":"1","ITEM_NAME":"同期出院患者择期手术总台次数","ITEM_VALUE":"9192.00"}],"REMIND":[{"ITEM_NO":"1","ITEM_CONTENT":"指标显示异常"}]},{"ID":"4","INDICATION_NAME":["医疗质量","(一)功能定位","4.出院患者手术占比","(1).出院患者手术占比"],"FILL_IN_ITEMS":[{"ITEM_NO":"1","ITEM_NAME":"出院患者手术台次数","ITEM_VALUE":"10865.00"},{"ITEM_NO":"1","ITEM_NAME":"同期出院患者总人次数","ITEM_VALUE":"34856.00"}],"REMIND":[{"ITEM_NO":"1","ITEM_CONTENT":"指标显示异常"}]},{"ID":"5","INDICATION_NAME":["医疗质量","(一)功能定位","5.出院患者微创手术占比","(1).出院患者微创手术占比"],"FILL_IN_ITEMS":[{"ITEM_NO":"1","ITEM_NAME":"同期出院患者手术台次数","ITEM_VALUE":"10865.00"},{"ITEM_NO":"1","ITEM_NAME":"出院患者微创手术台次数","ITEM_VALUE":"3194.00"}],"REMIND":[{"ITEM_NO":"1","ITEM_CONTENT":"指标显示异常"}]},{"ID":"6","INDICATION_NAME":["医疗质量","(一)功能定位","6.出院患者四级手术比例","(1).出院患者四级手术比例"],"FILL_IN_ITEMS":[{"ITEM_NO":"1","ITEM_NAME":"出院患者四级手术台次数","ITEM_VALUE":"1104.00"},{"ITEM_NO":"1","ITEM_NAME":"同期出院患者手术台次数","ITEM_VALUE":"10865.00"}],"REMIND":[{"ITEM_NO":"1","ITEM_CONTENT":"指标显示异常"}]}]
     * msg :
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
         * ID : 1
         * INDICATION_NAME : ["医疗质量","(一)功能定位","1.门诊人次数与出院人次数比","(1).门诊人次数与出院人次数比"]
         * FILL_IN_ITEMS : [{"ITEM_NO":"1","ITEM_NAME":"门诊患者人次数","ITEM_VALUE":"418569.00"},{"ITEM_NO":"1","ITEM_NAME":"同期出院患者人次数","ITEM_VALUE":"34856.00"}]
         * REMIND : [{"ITEM_NO":"1","ITEM_CONTENT":"指标显示异常"}]
         */

        private String ID;
        private List<String>          INDICATION_NAME;
        private List<FILLINITEMSBean> FILL_IN_ITEMS;
        private List<REMINDBean>      REMIND;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public List<String> getINDICATION_NAME() {
            return INDICATION_NAME;
        }

        public void setINDICATION_NAME(List<String> INDICATION_NAME) {
            this.INDICATION_NAME = INDICATION_NAME;
        }

        public List<FILLINITEMSBean> getFILL_IN_ITEMS() {
            return FILL_IN_ITEMS;
        }

        public void setFILL_IN_ITEMS(List<FILLINITEMSBean> FILL_IN_ITEMS) {
            this.FILL_IN_ITEMS = FILL_IN_ITEMS;
        }

        public List<REMINDBean> getREMIND() {
            return REMIND;
        }

        public void setREMIND(List<REMINDBean> REMIND) {
            this.REMIND = REMIND;
        }

        public static class FILLINITEMSBean {
            /**
             * ITEM_NO : 1
             * ITEM_NAME : 门诊患者人次数
             * ITEM_VALUE : 418569.00
             */

            private String ITEM_NO;
            private String ITEM_NAME;
            private String ITEM_VALUE;

            public String getITEM_NO() {
                return ITEM_NO;
            }

            public void setITEM_NO(String ITEM_NO) {
                this.ITEM_NO = ITEM_NO;
            }

            public String getITEM_NAME() {
                return ITEM_NAME;
            }

            public void setITEM_NAME(String ITEM_NAME) {
                this.ITEM_NAME = ITEM_NAME;
            }

            public String getITEM_VALUE() {
                return ITEM_VALUE;
            }

            public void setITEM_VALUE(String ITEM_VALUE) {
                this.ITEM_VALUE = ITEM_VALUE;
            }
        }

        public static class REMINDBean {
            /**
             * ITEM_NO : 1
             * ITEM_CONTENT : 指标显示异常
             */

            private String ITEM_NO;
            private String ITEM_CONTENT;

            public String getITEM_NO() {
                return ITEM_NO;
            }

            public void setITEM_NO(String ITEM_NO) {
                this.ITEM_NO = ITEM_NO;
            }

            public String getITEM_CONTENT() {
                return ITEM_CONTENT;
            }

            public void setITEM_CONTENT(String ITEM_CONTENT) {
                this.ITEM_CONTENT = ITEM_CONTENT;
            }
        }
    }
}
