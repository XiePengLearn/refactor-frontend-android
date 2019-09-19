package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/19 19:38
 * @Description:
 */
public class ExamMiddleResponse {

    /**
     * code : 200200
     * data : [{"NAME":"医疗质量","URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/pass-examination?type=X001&year=2019&indicateDefinitionId=A1&indicateDefinitionSetId=1"},{"NAME":"运营效率","URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/pass-examination?type=X001&year=2019&indicateDefinitionId=A2&indicateDefinitionSetId=1"},{"NAME":"持续发展","URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/pass-examination?type=X001&year=2019&indicateDefinitionId=A3&indicateDefinitionSetId=1"},{"NAME":"满意度评价","URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/pass-examination?type=X001&year=2019&indicateDefinitionId=A4&indicateDefinitionSetId=1"}]
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
         * NAME : 医疗质量
         * URI : http://114.247.234.146:8087/hospital-pa-ap-h5/#/pass-examination?type=X001&year=2019&indicateDefinitionId=A1&indicateDefinitionSetId=1
         */

        private String NAME;
        private String URI;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getURI() {
            return URI;
        }

        public void setURI(String URI) {
            this.URI = URI;
        }
    }
}
